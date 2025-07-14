package org.toxsoft.skf.sad.skide.main;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.skf.sad.gui.glib.*;
import org.toxsoft.skide.core.api.*;
import org.toxsoft.skide.core.api.impl.*;
import org.toxsoft.uskat.core.gui.conn.*;

/**
 * Правая панель главного модуля SkIDE.
 *
 * @author hazard157
 */
public class PanelSkideUnitOne
    extends AbstractSkideUnitPanel {

  /**
   * Constructor.
   *
   * @param aContext {@link ITsGuiContext} - the context
   * @param aUnit {@link ISkideUnit} - the project unit, creator of the panel
   */
  public PanelSkideUnitOne( ITsGuiContext aContext, ISkideUnit aUnit ) {
    super( aContext, aUnit );
  }

  // ------------------------------------------------------------------------------------
  // AbstractSkideUnitPanel
  //

  @Override
  protected Control doCreateControl( Composite aParent ) {
    ITsGuiContext ctx = new TsGuiContext( tsContext() );
    PanelSadFoldersAndDocs p = new PanelSadFoldersAndDocs( ctx, ISkConnectionSupplier.DEF_CONN_ID, false );
    return p.createControl( aParent );
  }

}
