package org.toxsoft.skf.sad.skide.e4.addons;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.quant.*;
import org.toxsoft.core.tsgui.mws.bases.*;
import org.toxsoft.skf.sad.gui.*;
import org.toxsoft.skf.sad.skide.*;
import org.toxsoft.skf.sad.skide.Activator;
import org.toxsoft.skf.sad.skide.main.*;
import org.toxsoft.skide.core.api.*;

/**
 * Plugin addon - initializes all subsystems and modules..
 *
 * @author hazard157
 */
public class AddonSkfSadSkide
    extends MwsAbstractAddon {

  /**
   * Constructor.
   */
  public AddonSkfSadSkide() {
    super( Activator.PLUGIN_ID );
    // HERE register keepers
  }

  // ------------------------------------------------------------------------------------
  // MwsAbstractAddon
  //

  @Override
  protected void doRegisterQuants( IQuantRegistrator aQuantRegistrator ) {
    // HERE register quants
    aQuantRegistrator.registerQuant( new QuantSkfSadGui() );
  }

  @Override
  protected void initApp( IEclipseContext aAppContext ) {
    ISkideEnvironment skEnv = aAppContext.get( ISkideEnvironment.class );
    skEnv.pluginsRegistrator().registerPlugin( SkidePluginSkfSad.INSTANCE );
  }

  @Override
  protected void initWin( IEclipseContext aWinContext ) {
    ISkfSadSkideConstants.init( aWinContext );
  }

}
