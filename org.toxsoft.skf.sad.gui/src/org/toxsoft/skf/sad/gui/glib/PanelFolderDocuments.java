package org.toxsoft.skf.sad.gui.glib;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.panels.generic.*;
import org.toxsoft.core.tsgui.utils.checkcoll.*;
import org.toxsoft.core.tslib.bricks.events.change.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.gui.km5.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel displayes and edits (if allowed) documents in the specified {@link ISkSadFolder}.
 * <p>
 * Note: this class does not generates {@link #genericChangeEventer()} events. Panel does not supports checks, that is
 * {@link ITsCheckSupport#isChecksSupported() checkSupport().isChecksSupported()} always returns <code>false</code>.
 *
 * @author hazard157
 */
public class PanelFolderDocuments
    extends AbstractSkStdEventsProducerLazyPanel<ISkSadDocument>
    implements IGenericCollPanel<ISkSadDocument> {

  private final boolean isViewer;

  private ISkSadFolder                       sadFolder = null;
  private IM5CollectionPanel<ISkSadDocument> collPanel = null;

  /**
   * Constructor.
   * <p>
   * Constructor stores reference to the context, does not creates copy.
   * <p>
   * The connection ID is the key to get connection from the {@link ISkConnectionSupplier#allConns()} map.
   *
   * @param aContext {@link ITsGuiContext} - the context
   * @param aUsedConnId {@link IdChain} - ID of connection to be used
   * @param aViewer boolean - viewer flag
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public PanelFolderDocuments( ITsGuiContext aContext, IdChain aUsedConnId, boolean aViewer ) {
    super( aContext, aUsedConnId );
    isViewer = aViewer;
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private void destroyContent() {
    if( !isControlValid() ) {
      return;
    }
    if( !isPanelContent() ) {
      return;
    }
    if( collPanel != null ) {
      collPanel.getControl().dispose();
      collPanel = null;
    }
  }

  private void createContent() {
    if( !isControlValid() ) {
      return;
    }
    TsInternalErrorRtException.checkNoNull( collPanel );
    if( sadFolder == null ) {
      return;
    }
    IM5Model<ISkSadDocument> model = m5().getModel( SkSadDocumentM5Model.MODEL_ID, ISkSadDocument.class );
    SkSadDocumentM5LifecycleManager lm = new SkSadDocumentM5LifecycleManager( model, skConn(), sadFolder );
    ITsGuiContext ctx = new TsGuiContext( tsContext() );
    if( isViewer ) {
      collPanel = model.panelCreator().createCollViewerPanel( ctx, lm.itemsProvider() );
    }
    else {
      collPanel = model.panelCreator().createCollEditPanel( ctx, lm.itemsProvider(), lm );
    }
    collPanel.createControl( getBackplane() );
    collPanel.addTsSelectionListener( selectionChangeEventHelper );
    collPanel.addTsDoubleClickListener( doubleClickEventHelper );
  }

  // ------------------------------------------------------------------------------------
  // AbstractSkStdEventsProducerLazyPanel
  //

  @Override
  protected void doInitGui( Composite aParent ) {
    createContent();
  }

  @Override
  protected ISkSadDocument doGetSelectedItem() {
    if( collPanel != null ) {
      return collPanel.selectedItem();
    }
    return null;
  }

  @Override
  protected void doSetSelectedItem( ISkSadDocument aItem ) {
    if( collPanel != null ) {
      collPanel.setSelectedItem( aItem );
    }
  }

  // ------------------------------------------------------------------------------------
  // API
  //

  /**
   * Determines if panel is created as viewer or as editor with ability to edit it's content.
   * <p>
   * If {@link #isViewer()} = <code>true</code> then this panel can not be switched to editing mode.
   *
   * @return boolean - viewer flag
   */
  @Override
  public boolean isViewer() {
    return isViewer;
  }

  /**
   * Sets {@link ISkSadFolder} used as a documents list source.
   *
   * @param aFolder {@link ISkSadFolder} - the folder or <code>null</code>
   */
  public void setSadFolder( ISkSadFolder aFolder ) {
    if( aFolder == sadFolder ) {
      return;
    }
    destroyContent();
    sadFolder = aFolder;
    createContent();
  }

  /**
   * Retruns {@link ISkSadFolder} used as a documents list source.
   *
   * @return {@link ISkSadFolder} - the folder or <code>null</code>
   */
  public ISkSadFolder sadFolder() {
    return sadFolder;
  }

  // ------------------------------------------------------------------------------------
  // IGenericContentPanel
  //

  @Override
  public IGenericChangeEventer genericChangeEventer() {
    return NoneGenericChangeEventer.INSTANCE;
  }

  // ------------------------------------------------------------------------------------
  // IGenericSelectorPanel
  //

  @Override
  public void refresh() {
    if( collPanel != null ) {
      collPanel.refresh();
    }
  }

  // ------------------------------------------------------------------------------------
  // IGenericCollPanel
  //

  @Override
  public IList<ISkSadDocument> items() {
    if( collPanel != null ) {
      return collPanel.items();
    }
    return IList.EMPTY;
  }

  @Override
  public ITsCheckSupport<ISkSadDocument> checkSupport() {
    return ITsCheckSupport.NONE;
  }

}
