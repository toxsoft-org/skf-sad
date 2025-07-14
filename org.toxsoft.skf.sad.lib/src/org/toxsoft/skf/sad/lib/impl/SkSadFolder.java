package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.utils.TsLibUtils.*;
import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;
import static org.toxsoft.skf.sad.lib.impl.ISkSadInternalConstants.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import java.io.*;
import java.time.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.events.msg.*;
import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.impl.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * {@link ISkSadFolder} implementation.
 *
 * @author hazard157
 */
public class SkSadFolder
    extends SkObject
    implements ISkSadFolder {

  static final ISkObjectCreator<SkSadFolder> CREATOR = SkSadFolder::new;

  private final String docClassId;

  private SkExtServiceSad sadService;
  private IOpsBatchEdit   paramsBatchEditor;

  SkSadFolder( Skid aSkid ) {
    super( aSkid );
    docClassId = makeDocumentClassId( aSkid.strid() );
  }

  // ------------------------------------------------------------------------------------
  // SkObject
  //

  @Override
  protected void doPostConstruct() {
    sadService = (SkExtServiceSad)coreApi().getService( ISkSadService.SERVICE_ID );
    paramsBatchEditor = new ObjectParamsEditor<>( this, sadService ) {

      @Override
      protected void generateSiblingMessage() {
        GtMessage msg = sadService.makeSiblingMessage2( MSGID_FOLDER_CRUD, ///
            MSGARGID_CRUD_OP, avValobj( ECrudOp.EDIT ), ///
            MSGARGID_FOLDER_ID, strid() ///
        );
        sadService.sendMessageToSiblings( msg );
      }
    };
  }

  // ------------------------------------------------------------------------------------
  // IParameterizedBatchEdit
  //

  @Override
  public IOptionSet params() {
    return attrs().getValobj( ATRID_PARAMS );
  }

  // ------------------------------------------------------------------------------------
  // IParameterizedBatchEdit
  //

  @Override
  public IOpsBatchEdit paramsBatchEditor() {
    return paramsBatchEditor;
  }

  // ------------------------------------------------------------------------------------
  // ISadFolder
  //

  @Override
  public IStridablesListEdit<ISkSadDocument> listDocuments() {
    IList<ISkSadDocument> ll = coreApi().objService().listObjs( docClassId, false );
    return new StridablesList<>( ll );
  }

  @Override
  public ISkSadDocument findDocument( String aDocumentId ) {
    Skid skid = new Skid( docClassId, aDocumentId );
    return coreApi().objService().find( skid );
  }

  @Override
  public ISkSadDocument createDocument( String aDocId, String aTemplateDocId, IOptionSet aParams ) {
    TsValidationFailedRtException
        .checkError( sadService.svs().validator().canCreateDocument( this, aDocId, aTemplateDocId, aParams ) );
    // prepare attributes and remove system attributes if any found in aParameters
    String name = aParams.getStr( AID_NAME, EMPTY_STRING );
    String description = aParams.getStr( AID_DESCRIPTION, EMPTY_STRING );
    IOptionSetEdit params = new OptionSet();
    for( String pid : aParams.keys() ) {
      if( !isSkSysAttrId( pid ) ) {
        params.put( pid, aParams.getValue( pid ) );
      }
    }
    sadService.pauseCoreValidationAndEvents();
    ISkSadDocument document;
    File tmpFile = null;
    try {
      // create document object
      Skid skid = new Skid( docClassId, aDocId );
      DtoObject dto = new DtoObject( skid );
      dto.attrs().setStr( AID_NAME, name );
      dto.attrs().setStr( AID_DESCRIPTION, description );
      LocalDateTime ldtNow = LocalDateTime.now( ZoneId.of( "UTC" ) ); //$NON-NLS-1$
      dto.attrs().setValobj( ATRID_CREATION_TIME, ldtNow );
      dto.attrs().setValobj( ATRID_CONTENT_MODIFICATION_TIME, ldtNow );
      dto.attrs().setValobj( ATRID_ATTRS_MODIFICATION_TIME, ldtNow );
      dto.attrs().setValobj( ATRID_STORAGE_KIND, ESadContentStorageKind.CLOB ); // FIXME how to select kind?
      dto.attrs().setValobj( ATRID_PARAMS, params );
      dto.attrs().setStr( ATRID_TEMPLATE_DOC_ID, aTemplateDocId );
      document = coreApi().objService().defineObject( dto );
      // copy template content to the new document
      if( !aTemplateDocId.equals( IStridable.NONE_ID ) ) {
        ISkSadDocument template = getDocument( aTemplateDocId );
        Gwid clobGwid = Gwid.createClob( template.skid(), CLBID_CONTENT );
        tmpFile = sadService.papiGetTemporaryFileNameForClob( clobGwid );
        template.downloadSadContentToTheFile( tmpFile );
        document.uploadSadContentFromTheFile( tmpFile );
      }
      // inform siblings
      GtMessage msg = sadService.makeSiblingMessage2( MSGID_DOCUMENT_CRUD, ///
          MSGARGID_CRUD_OP, avValobj( ECrudOp.CREATE ), ///
          MSGARGID_FOLDER_ID, strid(), ///
          MSGARGID_DOCUMENT_ID, document.strid() ///
      );
      sadService.sendMessageToSiblings( msg );
    }
    finally {
      sadService.resumeCoreValidationAndEvents();
      if( tmpFile != null ) {
        tmpFile.delete();
      }
    }
    return document;
  }

  @Override
  public void removeDocument( String aDocId ) {
    TsValidationFailedRtException.checkError( sadService.svs().validator().canRemoveDocument( this, aDocId ) );
    sadService.pauseCoreValidationAndEvents();
    try {
      Skid skid = new Skid( docClassId, aDocId );
      coreApi().objService().removeObject( skid );
      // inform siblings
      GtMessage msg = sadService.makeSiblingMessage2( MSGID_DOCUMENT_CRUD, ///
          MSGARGID_CRUD_OP, avValobj( ECrudOp.REMOVE ), ///
          MSGARGID_FOLDER_ID, strid(), ///
          MSGARGID_DOCUMENT_ID, aDocId ///
      );
      sadService.sendMessageToSiblings( msg );
    }
    finally {
      sadService.resumeCoreValidationAndEvents();
    }
  }

  @Override
  public void setNameAndDescription( String aName, String aDescription ) {
    TsValidationFailedRtException
        .checkError( sadService.svs().validator().canSetFolderNameAndDescription( this, aName, aDescription ) );
    sadService.pauseCoreValidationAndEvents();
    try {
      DtoObject dto = DtoObject.createFromSk( this, coreApi() );
      dto.attrs().setStr( AID_NAME, aName );
      dto.attrs().setStr( AID_DESCRIPTION, aDescription );
      if( dto.attrs().hasKey( ATRID_ATTRS_MODIFICATION_TIME ) ) {
        dto.attrs().setValobj( ATRID_ATTRS_MODIFICATION_TIME, LocalDateTime.now( ZoneId.of( "UTC" ) ) ); //$NON-NLS-1$
      }
      coreApi().objService().defineObject( dto );
      this.attrs().setStr( AID_NAME, aName );
      this.attrs().setStr( AID_DESCRIPTION, aDescription );
      // inform siblings
      GtMessage msg = sadService.makeSiblingMessage2( MSGID_FOLDER_CRUD, ///
          MSGARGID_CRUD_OP, avValobj( ECrudOp.EDIT ), ///
          MSGARGID_FOLDER_ID, strid() ///
      );
      sadService.sendMessageToSiblings( msg );
    }
    finally {
      sadService.resumeCoreValidationAndEvents();
    }
  }

  @Override
  public void archiveDocument( String aDocId ) {
    // TODO реализовать SadFolder.archiveDocument()
    throw new TsUnderDevelopmentRtException( "SadFolder.archiveDocument()" );
  }

}
