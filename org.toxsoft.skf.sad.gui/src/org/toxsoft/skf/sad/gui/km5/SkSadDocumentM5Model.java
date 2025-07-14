package org.toxsoft.skf.sad.gui.km5;

import static org.toxsoft.core.tsgui.m5.IM5Constants.*;
import static org.toxsoft.skf.sad.gui.ISkfSadGuiConstants.*;
import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;
import static org.toxsoft.skf.sad.lib.l10n.ISkSadLibSharedResources.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.impl.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.gui.panels.impl.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tslib.av.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * M5-model of {@link SkSadDocumentM5Model}.
 *
 * @author hazard157
 */
public class SkSadDocumentM5Model
    extends KM5ModelBasic<ISkSadDocument> {

  // FIXME add to MPC buttons EXPORT_CONTENT and IMPORT_CONTENT

  /**
   * The model ID.
   */
  public static final String MODEL_ID = SKF_SAD_M5_ID + ".Document"; //$NON-NLS-1$

  /**
   * Attribute {@link ISkSadDocument#templateDocId()}.
   */
  public final IM5AttributeFieldDef<ISkSadDocument> TEMPLATE_ID =
      new KM5AttributeFieldDef<>( CLSINF_SAD_DOCUMENT.attrInfos().getByKey( ATRID_TEMPLATE_DOC_ID ) );

  /**
   * Attribute {@link ISkSadDocument#params()}.
   */
  public final IM5AttributeFieldDef<ISkSadDocument> PARAMS =
      new KM5AttributeFieldDef<>( CLSINF_SAD_DOCUMENT.attrInfos().getByKey( ATRID_PARAMS ) );

  /**
   * Constructor.
   *
   * @param aConn {@link ISkConnection} - Sk-connection to be used in constructor
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  @SuppressWarnings( "rawtypes" )
  public SkSadDocumentM5Model( ISkConnection aConn ) {
    super( MODEL_ID, ISkSadDocument.class, aConn );
    STRID.setNameAndDescription( STR_ATR_FOLDER_ID, STR_ATR_FOLDER_ID_D );
    ((KM5AttributeFieldDef)TEMPLATE_ID).setFlags( M5FF_COLUMN | M5FF_INVARIANT );
    ((KM5AttributeFieldDef)PARAMS).setFlags( M5FF_HIDDEN );
    addFieldDefs( STRID, NAME, DESCRIPTION, TEMPLATE_ID, PARAMS );
    setPanelCreator( new M5DefaultPanelCreator<>() {

      protected IM5CollectionPanel<ISkSadDocument> doCreateCollEditPanel( ITsGuiContext aContext,
          IM5ItemsProvider<ISkSadDocument> aItemsProvider, IM5LifecycleManager<ISkSadDocument> aLifecycleManager ) {
        IMultiPaneComponentConstants.OPDEF_IS_ACTIONS_CRUD.setValue( aContext.params(), AvUtils.AV_TRUE );
        MultiPaneComponentModown<ISkSadDocument> mpc =
            new MultiPaneComponentModown<>( aContext, model(), aItemsProvider, aLifecycleManager );
        mpc.addLocalAsp( new AspExpImpSadContent( aContext, false ) {

          @Override
          protected ISkSadDocument selectedDoc() {
            return mpc.selectedItem();
          }
        } );
        return new M5CollectionPanelMpcModownWrapper<>( mpc, false );
      }

      protected IM5CollectionPanel<ISkSadDocument> doCreateCollViewerPanel( ITsGuiContext aContext,
          IM5ItemsProvider<ISkSadDocument> aItemsProvider ) {
        IMultiPaneComponentConstants.OPDEF_IS_ACTIONS_CRUD.setValue( aContext.params(), AvUtils.AV_FALSE );
        MultiPaneComponentModown<ISkSadDocument> mpc =
            new MultiPaneComponentModown<>( aContext, model(), aItemsProvider );
        mpc.addLocalAsp( new AspExpImpSadContent( aContext, true ) {

          @Override
          protected ISkSadDocument selectedDoc() {
            return mpc.selectedItem();
          }
        } );
        return new M5CollectionPanelMpcModownWrapper<>( mpc, true );
      }

    } );
  }

}
