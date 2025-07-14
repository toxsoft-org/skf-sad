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
 * LM fof {@link SkSadFolderM5Model}
 *
 * @author hazard157
 */
class SkSadFolderM5LifecycleManager
    extends KM5LifecycleManagerBasic<ISkSadFolder> {

  /**
   * Constructor.
   *
   * @param aModel {@link IM5Model}&lt;T&gt; - the model
   * @param aMaster {@link ISkConnection} - master object
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public SkSadFolderM5LifecycleManager( IM5Model<ISkSadFolder> aModel, ISkConnection aMaster ) {
    super( aModel, true, true, true, true, aMaster );
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
  protected ValidationResult doBeforeCreate( IM5Bunch<ISkSadFolder> aValues ) {
    String folderId = aValues.getAsAv( AID_STRID ).asString();
    IOptionSetEdit params = aValues.getAsAv( ATRID_PARAMS ).asValobj();
    params.setValue( AID_NAME, aValues.getAsAv( AID_NAME ) );
    params.setValue( AID_DESCRIPTION, aValues.getAsAv( AID_DESCRIPTION ) );
    return sadService().svs().validator().canCreateFolder( folderId, params );
  }

  @Override
  protected ISkSadFolder doCreate( IM5Bunch<ISkSadFolder> aValues ) {
    String folderId = aValues.getAsAv( AID_STRID ).asString();
    IOptionSetEdit params = aValues.getAsAv( ATRID_PARAMS ).asValobj();
    params.setValue( AID_NAME, aValues.getAsAv( AID_NAME ) );
    params.setValue( AID_DESCRIPTION, aValues.getAsAv( AID_DESCRIPTION ) );
    return sadService().createFolder( folderId, params );
  }

  @Override
  protected ValidationResult doBeforeEdit( IM5Bunch<ISkSadFolder> aValues ) {
    String folderId = aValues.getAsAv( AID_STRID ).asString();
    ISkSadFolder folder = sadService().getFolder( folderId );
    String name = aValues.getAsAv( AID_NAME ).asString();
    String description = aValues.getAsAv( AID_DESCRIPTION ).asString();
    return sadService().svs().validator().canSetFolderNameAndDescription( folder, name, description );
  }

  @Override
  protected ISkSadFolder doEdit( IM5Bunch<ISkSadFolder> aValues ) {
    String folderId = aValues.getAsAv( AID_STRID ).asString();
    ISkSadFolder folder = sadService().getFolder( folderId );
    String name = aValues.getAsAv( AID_NAME ).asString();
    String description = aValues.getAsAv( AID_DESCRIPTION ).asString();
    folder.setNameAndDescription( name, description );
    return folder;
  }

  @Override
  protected ValidationResult doBeforeRemove( ISkSadFolder aEntity ) {
    return sadService().svs().validator().canRemoveFolder( aEntity.id() );
  }

  @Override
  protected void doRemove( ISkSadFolder aEntity ) {
    sadService().removeFolder( aEntity.id() );
  }

  @Override
  protected IList<ISkSadFolder> doListEntities() {
    return sadService().listFolders();
  }

}
