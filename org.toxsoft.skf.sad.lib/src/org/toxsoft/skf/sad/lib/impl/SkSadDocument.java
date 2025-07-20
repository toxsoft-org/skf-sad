package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;
import static org.toxsoft.skf.sad.lib.impl.ISkSadInternalConstants.*;
import static org.toxsoft.uskat.backend.memtext.MtbBackendToFile.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;
import static org.toxsoft.uskat.core.impl.ISkCoreConfigConstants.*;

import java.io.*;
import java.time.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.ctx.impl.*;
import org.toxsoft.core.tslib.bricks.events.msg.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.backend.memtext.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.impl.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * {@link ISkSadDocument} implementation.
 *
 * @author hazard157
 */
class SkSadDocument
    extends SkObject
    implements ISkSadDocument {

  /**
   * The object creator singleton.
   */
  public static final ISkObjectCreator<SkSadDocument> CREATOR = SkSadDocument::new;

  private SkSadFolder     sadFolder;
  private SkExtServiceSad sadService;
  private IOpsBatchEdit   paramsBatchEditor;

  private SkSadDocument( Skid aSkid ) {
    super( aSkid );
  }

  // ------------------------------------------------------------------------------------
  // SkObject
  //

  @Override
  protected void doPostConstruct() {
    String folderId = extractFolderIdFromDocumentClassId( classId() );
    Skid folderSkid = new Skid( CLSID_SAD_FOLDER, folderId );
    sadFolder = (SkSadFolder)coreApi().objService().get( folderSkid );
    sadService = (SkExtServiceSad)coreApi().getService( ISkSadService.SERVICE_ID );
    paramsBatchEditor = new ObjectParamsEditor<>( this, sadService ) {

      @Override
      protected void generateSiblingMessage() {
        GtMessage msg = sadService.makeSiblingMessage2( MSGID_DOCUMENT_CRUD, ///
            MSGARGID_CRUD_OP, avValobj( ECrudOp.EDIT ), ///
            MSGARGID_FOLDER_ID, sadFolder.strid(), ///
            MSGARGID_DOCUMENT_ID, strid() ///
        );
        sadService.sendMessageToSiblings( msg );
      }
    };
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  ESadContentStorageKind storageKind() {
    return attrs().getValobj( ATRID_STORAGE_KIND );
  }

  // ------------------------------------------------------------------------------------
  // ISkSadDocument
  //

  @Override
  public SkSadFolder sadFolder() {
    return sadFolder;
  }

  @Override
  public IOptionSet params() {
    return attrs().getValobj( ATRID_PARAMS );
  }

  @Override
  public String templateDocId() {
    return attrs().getStr( ATRID_TEMPLATE_DOC_ID );
  }

  @Override
  public LocalDateTime contentModificationTime() {
    return attrs().getValobj( ATRID_CONTENT_MODIFICATION_TIME );
  }

  @Override
  public LocalDateTime attributesModificationTime() {
    return attrs().getValobj( ATRID_ATTRS_MODIFICATION_TIME );
  }

  @Override
  public LocalDateTime creationTime() {
    return attrs().getValobj( ATRID_CREATION_TIME );
  }

  @Override
  public void setNameAndDescription( String aName, String aDescription ) {
    TsValidationFailedRtException.checkError(
        sadService.svs().validator().canSetDocumentNameAndDescription( sadFolder, this, aName, aDescription ) );
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
      GtMessage msg = sadService.makeSiblingMessage2( MSGID_DOCUMENT_CRUD, ///
          MSGARGID_CRUD_OP, avValobj( ECrudOp.EDIT ), ///
          MSGARGID_FOLDER_ID, sadFolder.strid(), ///
          MSGARGID_DOCUMENT_ID, strid() ///
      );
      sadService.sendMessageToSiblings( msg );
    }
    finally {
      sadService.resumeCoreValidationAndEvents();
    }
  }

  @Override
  public IOpsBatchEdit paramsBatchEditor() {
    return paramsBatchEditor;
  }

  @Override
  public Pair<ISkTheOpenDoc, ValidationResult> tryOpen( ISkConnection aConn, ITsContext aArgs ) {
    ITsContext args = new TsContext( aArgs );
    // TODO check if SAD can be edited and mark SAD as opened for edit
    // copy content from server to TMP local file
    Gwid clobGwid = Gwid.createClob( skid(), CLBID_CONTENT );
    File localFile = sadService.papiGetFileNameForClob( clobGwid );
    AbstractSadContentStorage storage = storageKind().getStorage();
    storage.downloadSadContentToTheFile( this, localFile );
    // open Sk-connection on TMP local file
    OPDEF_FILE_PATH.setValue( args.params(), avStr( localFile.getAbsolutePath() ) );
    REFDEF_BACKEND_PROVIDER.setRef( args, MtbBackendToFile.PROVIDER );
    TsIllegalArgumentRtException.checkFalse( args.hasKey( REFDEF_THREAD_EXECUTOR.refKey() ) );
    try {
      aConn.open( args );
      TheOpenDoc openDoc = new TheOpenDoc( aConn, this, false );
      return new Pair<>( openDoc, ValidationResult.SUCCESS );
    }
    catch( Exception ex ) {
      sadService.logger().error( ex );
      // clean-up
      if( localFile.exists() ) {
        localFile.delete();
      }
      // TODO remove EDINITG mark from the SK document
      return new Pair<>( null, ValidationResult.error( ex ) );
    }
  }

  @Override
  public Pair<ISkTheOpenDoc, ValidationResult> openReadOnly( ISkConnection aConn, ITsContext aArgs ) {
    ITsContext args = new TsContext( aArgs );
    // copy content from server to TMP local file
    Gwid clobGwid = Gwid.createClob( skid(), CLBID_CONTENT );
    File localFile = sadService.papiGetFileNameForClob( clobGwid );
    AbstractSadContentStorage storage = storageKind().getStorage();
    storage.downloadSadContentToTheFile( this, localFile );
    // open Sk-connection on TMP local file
    OPDEF_FILE_PATH.setValue( args.params(), avStr( localFile.getAbsolutePath() ) );
    REFDEF_BACKEND_PROVIDER.setRef( args, MtbBackendToFile.PROVIDER );
    TsIllegalArgumentRtException.checkFalse( args.hasKey( REFDEF_THREAD_EXECUTOR.refKey() ) );
    try {
      aConn.open( args );
      TheOpenDoc openDoc = new TheOpenDoc( aConn, this, false );
      return new Pair<>( openDoc, ValidationResult.SUCCESS );
    }
    catch( Exception ex ) {
      sadService.logger().error( ex );
      // clean-up
      if( localFile.exists() ) {
        localFile.delete();
      }
      return new Pair<>( null, ValidationResult.error( ex ) );
    }
  }

  @Override
  public void downloadSadContentToTheFile( File aLocalFile ) {
    storageKind().getStorage().downloadSadContentToTheFile( this, aLocalFile );
  }

  @Override
  public void uploadSadContentFromTheFile( File aLocalFile ) {
    sadService.pauseCoreValidationAndEvents();
    try {
      storageKind().getStorage().uploadSadContentFromTheFile( this, aLocalFile );
      // set content modification time
      DtoObject dto = DtoObject.createFromSk( this, coreApi() );
      LocalDateTime ldtNow = LocalDateTime.now( ZoneId.of( "UTC" ) ); //$NON-NLS-1$
      dto.attrs().setValobj( ATRID_CONTENT_MODIFICATION_TIME, ldtNow );
      coreApi().objService().defineObject( dto );
      this.attrs().setValobj( ATRID_CONTENT_MODIFICATION_TIME, ldtNow );
      // inform siblings
      GtMessage msg = sadService.makeSiblingMessage2( MSGID_DOCUMENT_CRUD, ///
          MSGARGID_CRUD_OP, avValobj( ECrudOp.EDIT ), ///
          MSGARGID_FOLDER_ID, sadFolder.strid(), ///
          MSGARGID_DOCUMENT_ID, strid() ///
      );
      sadService.sendMessageToSiblings( msg );
    }
    finally {
      sadService.resumeCoreValidationAndEvents();
    }
  }

}
