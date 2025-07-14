package org.toxsoft.skf.sad.skide.main;

import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.sad.gui.ISkfSadGuiConstants.*;
import static org.toxsoft.skf.sad.skide.l10n.ISkfSadSkideSharedResources.*;
import static org.toxsoft.skide.core.ISkideCoreConstants.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.skide.core.api.*;

/**
 * SkIDE plugin: all units of the HDero-mdb application.
 *
 * @author hazard157
 */
public class SkidePluginSkfSad
    extends AbstractSkidePlugin {

  /**
   * The plugin ID.
   */
  public static final String SKIDE_PLUGIN_ID = SKIDE_FULL_ID + ".plugin.skf_sad"; //$NON-NLS-1$

  /**
   * The singleton instance.
   */
  public static final AbstractSkidePlugin INSTANCE = new SkidePluginSkfSad();

  SkidePluginSkfSad() {
    super( SKIDE_PLUGIN_ID, OptionSetUtils.createOpSet( ///
        TSID_NAME, STR_PLUGIN_SKF_SAD, ///
        TSID_DESCRIPTION, STR_PLUGIN_SKF_SAD_D, ///
        TSID_ICON_ID, ICONID_SKF_SAD ///
    ) );
  }

  @Override
  protected void doCreateUnits( ITsGuiContext aContext, IStridablesListEdit<ISkideUnit> aUnitsList ) {
    aUnitsList.add( new SkideUnitSkfSadOne( aContext, this ) );
  }

}
