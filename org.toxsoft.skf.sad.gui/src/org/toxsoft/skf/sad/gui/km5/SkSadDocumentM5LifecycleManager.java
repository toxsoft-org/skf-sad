package org.toxsoft.skf.sad.gui.km5;

import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * LM fof {@link SkSadDocumentM5Model}
 *
 * @author hazard157
 */
public class SkSadDocumentM5LifecycleManager
    extends KM5LifecycleManagerBasic<ISkSadDocument> {

  private final ISkSadFolder sadFolder;

  /**
   * Constructor.
   *
   * @param aModel {@link IM5Model}&lt;T&gt; - the model
   * @param aMaster {@link ISkConnection} - master object
   * @param aFolder {@link ISkSadFolder} - owner folder to model it's documents
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public SkSadDocumentM5LifecycleManager( IM5Model<ISkSadDocument> aModel, ISkConnection aMaster,
      ISkSadFolder aFolder ) {
    super( aModel, true, true, true, true, aMaster );
    sadFolder = TsNullArgumentRtException.checkNull( aFolder );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private ISkSadService sadService() {
    return coreApi().getService( ISkSadService.SERVICE_ID );
  }

  // ------------------------------------------------------------------------------------
  // KM5LifecycleManagerBasic
  //

  @Override
  protected ValidationResult doBeforeCreate( IM5Bunch<ISkSadDocument> aValues ) {
    String documentId = aValues.getAsAv( AID_STRID ).asString();
    String templateId = aValues.getAsAv( ATRID_TEMPLATE_DOC_ID ).asString();
    IOptionSetEdit params = aValues.getAsAv( ATRID_PARAMS ).asValobj();
    params.setValue( AID_NAME, aValues.getAsAv( AID_NAME ) );
    params.setValue( AID_DESCRIPTION, aValues.getAsAv( AID_DESCRIPTION ) );
    return sadService().svs().validator().canCreateDocument( sadFolder, documentId, templateId, params );
  }

  @Override
  protected ISkSadDocument doCreate( IM5Bunch<ISkSadDocument> aValues ) {
    String documentId = aValues.getAsAv( AID_STRID ).asString();
    String templateId = aValues.getAsAv( ATRID_TEMPLATE_DOC_ID ).asString();
    IOptionSetEdit params = aValues.getAsAv( ATRID_PARAMS ).asValobj();
    params.setValue( AID_NAME, aValues.getAsAv( AID_NAME ) );
    params.setValue( AID_DESCRIPTION, aValues.getAsAv( AID_DESCRIPTION ) );
    return sadFolder.createDocument( documentId, templateId, params );
  }

  @Override
  protected ValidationResult doBeforeEdit( IM5Bunch<ISkSadDocument> aValues ) {
    String documentId = aValues.getAsAv( AID_STRID ).asString();
    ISkSadDocument document = sadFolder.getDocument( documentId );
    String name = aValues.getAsAv( AID_NAME ).asString();
    String description = aValues.getAsAv( AID_DESCRIPTION ).asString();
    return sadService().svs().validator().canSetDocumentNameAndDescription( sadFolder, document, name, description );
  }

  @Override
  protected ISkSadDocument doEdit( IM5Bunch<ISkSadDocument> aValues ) {
    String documentId = aValues.getAsAv( AID_STRID ).asString();
    ISkSadDocument document = sadFolder.getDocument( documentId );
    String name = aValues.getAsAv( AID_NAME ).asString();
    String description = aValues.getAsAv( AID_DESCRIPTION ).asString();
    document.setNameAndDescription( name, description );
    return document;
  }

  @Override
  protected ValidationResult doBeforeRemove( ISkSadDocument aEntity ) {
    return sadService().svs().validator().canRemoveDocument( sadFolder, aEntity.id() );
  }

  @Override
  protected void doRemove( ISkSadDocument aEntity ) {
    sadFolder.removeDocument( aEntity.id() );
  }

  @Override
  protected IList<ISkSadDocument> doListEntities() {
    return sadFolder.listDocuments();
  }

}
