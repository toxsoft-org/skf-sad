package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.utils.valobj.*;
import org.toxsoft.skf.sad.lib.impl.*;
import org.toxsoft.uskat.core.api.*;
import org.toxsoft.uskat.core.impl.*;

/**
 * Initialization and utility methods.
 *
 * @author hazard157
 */
public class SkfSadLibUtils {

  /**
   * Core handler to register all registered Sk-connection bound {@link ISkUgwiKind} when connection opens.
   */
  private static final ISkCoreExternalHandler coreRegistrationHandler = aCoreApi -> {
    // ISkRefbookService rbServ = aCoreApi.getService( ISkRefbookService.SERVICE_ID );
    // rbServ.defineRefbook( REFBOOK_TRAFFIC_SIGNAL_KIND );
  };

  /**
   * The plugin initialization must be called before any action to access classes in this plugin.
   */
  public static void initialize() {
    TsValobjUtils.registerKeeper( ESadContentStorageKind.KEEPER_ID, ESadContentStorageKind.KEEPER );
    SkCoreUtils.registerSkServiceCreator( SkExtServiceSad.CREATOR );
    SkCoreUtils.registerCoreApiHandler( coreRegistrationHandler );
  }

  /**
   * No subclasses.
   */
  private SkfSadLibUtils() {
    // nop
  }

}
