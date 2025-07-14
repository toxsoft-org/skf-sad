package org.toxsoft.skf.sad.gui.glib;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.gui.km5.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel to display folders and documents provider by {@link ISkSadServiceHardConstants},
 * <p>
 * Contains two panes:
 * <ul>
 * <li>left paneln - list of folders;</li>
 * <li>right pane - documents list of the folder selected in the left pane.</li>
 * </ul>
 *
 * @author hazard157
 */
public class PanelSadFoldersAndDocs
    extends AbstractSkLazyPanel {

  private final boolean isViewer;

  private IM5CollectionPanel<ISkSadFolder> panelFolders = null;
  private PanelFolderDocuments             panelDocs    = null;

  /**
   * Constructor.
   *
   * @param aContext {@link ITsGuiContext} - the context
   * @param aUsedConnId {@link IdChain} - ID of connection to be used, or<code>null</code>
   * @param aViewer boolean - viewer flag
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public PanelSadFoldersAndDocs( ITsGuiContext aContext, IdChain aUsedConnId, boolean aViewer ) {
    super( aContext, aUsedConnId );
    isViewer = aViewer;
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  @SuppressWarnings( "unused" )
  private void onFolderSelectionChanged( Object aSource, ISkSadFolder aSelectedItem ) {
    panelDocs.setSadFolder( aSelectedItem );
  }

  @SuppressWarnings( "unused" )
  private void onDocumentSelectionChanged( Object aSource, ISkSadDocument aSelectedItem ) {
    // nop
  }

  // ------------------------------------------------------------------------------------
  // AbstractSkLazyPanel
  //

  @Override
  protected void doInitGui( Composite aParent ) {
    // create lazy panels
    IM5Model<ISkSadFolder> model = m5().getModel( SkSadFolderM5Model.MODEL_ID, ISkSadFolder.class );
    IM5LifecycleManager<ISkSadFolder> lm = model.getLifecycleManager( skConn() );
    ITsGuiContext ctx1 = new TsGuiContext( tsContext() );
    if( isViewer ) {
      panelFolders = model.panelCreator().createCollViewerPanel( ctx1, lm.itemsProvider() );
    }
    else {
      panelFolders = model.panelCreator().createCollEditPanel( ctx1, lm.itemsProvider(), lm );
    }
    ITsGuiContext ctx2 = new TsGuiContext( tsContext() );
    panelDocs = new PanelFolderDocuments( ctx2, getUsedConnectionId(), isViewer );
    // init SWT
    SashForm sfMain = new SashForm( aParent, SWT.HORIZONTAL );
    panelFolders.createControl( sfMain );
    panelDocs.createControl( sfMain );
    sfMain.setWeights( 3500, 6500 );
    // setup user action handlers
    panelFolders.addTsSelectionListener( this::onFolderSelectionChanged );
    panelDocs.addTsSelectionListener( this::onDocumentSelectionChanged );
  }

}
