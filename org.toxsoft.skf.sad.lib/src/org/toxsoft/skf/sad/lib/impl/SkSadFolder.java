package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.skf.sad.lib.impl.ISkSadInternalConstants.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.events.msg.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.impl.*;

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
    paramsBatchEditor = new ObjectParamsEditor<>( this ) {

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
    ISkSadDocument templateDoc = findDocument( aTemplateDocId );
    TsValidationFailedRtException
        .checkError( sadService.svs().validator().canCreateDocument( this, aDocId, templateDoc, aParams ) );

    // TODO реализовать SadFolder.createDocument()
    throw new TsUnderDevelopmentRtException( "SadFolder.createDocument()" );
  }

  @Override
  public void removeDocument( String aDocId ) {
    // TODO реализовать SadFolder.removeDocument()
    throw new TsUnderDevelopmentRtException( "SadFolder.removeDocument()" );
  }

  @Override
  public void archiveDocument( String aDocId ) {
    // TODO реализовать SadFolder.archiveDocument()
    throw new TsUnderDevelopmentRtException( "SadFolder.archiveDocument()" );
  }

}
