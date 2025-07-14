package org.toxsoft.skf.sad.gui;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.quant.*;
import org.toxsoft.skf.sad.gui.km5.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * The library quant.
 *
 * @author hazard157
 */
public class QuantSkfSadGui
    extends AbstractQuant {

  /**
   * Constructor.
   */
  public QuantSkfSadGui() {
    super( QuantSkfSadGui.class.getSimpleName() );
    SkfSadLibUtils.initialize();
    KM5Utils.registerContributorCreator( KM5SkfSadContributor.CREATOR );
  }

  // ------------------------------------------------------------------------------------
  // AbstractQuant
  //

  @Override
  protected void doInitApp( IEclipseContext aAppContext ) {
    // nop
  }

  @Override
  protected void doInitWin( IEclipseContext aWinContext ) {
    ISkfSadGuiConstants.init( aWinContext );
  }

}
