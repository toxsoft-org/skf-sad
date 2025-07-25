package org.toxsoft.skf.sad.skide.main;

import static org.toxsoft.core.tsgui.bricks.actions.ITsStdActionDefs.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.sad.gui.ISkfSadGuiConstants.*;
import static org.toxsoft.skf.sad.skide.l10n.ISkfSadSkideSharedResources.*;
import static org.toxsoft.skide.core.ISkideCoreConstants.*;
import static org.toxsoft.skide.core.api.ucateg.ISkideUnitCategoryConstants.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.skide.core.api.*;
import org.toxsoft.skide.core.api.impl.*;
import org.toxsoft.skide.core.api.tasks.*;

/**
 * HDero-mdb SkiDE unit: FEMJOY girls.
 *
 * @author hazard157
 */
public class SkideUnitSkfSadOne
    extends AbstractSkideUnit {

  /**
   * The plugin ID.
   */
  public static final String UNIT_ID = SKIDE_FULL_ID + ".unit.skf_sad.one"; //$NON-NLS-1$

  SkideUnitSkfSadOne( ITsGuiContext aContext, AbstractSkidePlugin aCreator ) {
    super( UNIT_ID, OptionSetUtils.createOpSet( //
        TSID_NAME, STR_UNIT_SKF_SAD_ONE, //
        TSID_DESCRIPTION, STR_UNIT_SKF_SAD_ONE_D, //
        OPDEF_SKIDE_UNIT_CATEGORY, UCATEGID_APP_SPECIFIC, //
        TSID_ICON_ID, ICONID_SKF_SAD //
    ), aContext, aCreator );
    unitActions().add( ACDEF_ABOUT );
  }

  @Override
  protected AbstractSkideUnitPanel doCreateUnitPanel( ITsGuiContext aContext ) {
    return new PanelSkideUnitOne( aContext, this );
  }

  @Override
  protected void doFillTasks( IStringMapEdit<AbstractSkideUnitTask> aTaskRunnersMap ) {
    // nop
  }

}
