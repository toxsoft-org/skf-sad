package org.toxsoft.skf.sad.gui;

import static org.toxsoft.core.tsgui.graphics.icons.ITsStdIconIds.*;
import static org.toxsoft.skf.sad.gui.l10n.ISkfSadGuiSharedResources.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.actions.*;
import org.toxsoft.core.tsgui.graphics.icons.*;

/**
 * Plugin common constants.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" )
public interface ISkfSadGuiConstants {

  // ------------------------------------------------------------------------------------
  // plugin constants prefixes

  String SKF_SAD_FULL_ID = USKAT_FULL_ID + ".skf.sad"; //$NON-NLS-1$ general full ID prefix (IDpath)
  String SKF_SAD_ID      = SK_ID + ".skf_sad";         //$NON-NLS-1$ general short ID prefix (IDname)
  String SKF_SAD_ACT_ID  = SKF_SAD_ID + ".act";        //$NON-NLS-1$ prefix of the ITsActionDef IDs
  String SKF_SAD_M5_ID   = SKF_SAD_ID + ".m5";         //$NON-NLS-1$ perfix of M5-model IDs

  // ------------------------------------------------------------------------------------
  // Actions

  String ACTID_DOWNLOAD_SAD_CONTENT = SKF_SAD_ACT_ID + ".DownloadSadContent"; //$NON-NLS-1$
  String ACTID_UPLOAD_SAD_CONTENT   = SKF_SAD_ACT_ID + ".UploadSadContent";   //$NON-NLS-1$

  ITsActionDef ACDEF_DOWNLOAD_SAD_CONTENT = TsActionDef.ofPush2( ACTID_DOWNLOAD_SAD_CONTENT, //
      STR_DOWNLOAD_SAD_CONTENT, STR_DOWNLOAD_SAD_CONTENT_D, ICONID_DOCUMENT_EXPORT );

  ITsActionDef ACDEF_UPLOAD_SAD_CONTENT = TsActionDef.ofPush2( ACTID_UPLOAD_SAD_CONTENT, //
      STR_UPLOAD_SAD_CONTENT, STR_UPLOAD_SAD_CONTENT_D, ICONID_DOCUMENT_IMPORT );

  // ------------------------------------------------------------------------------------
  // Icons

  String PREFIX_OF_ICON_FIELD_NAME = "ICONID_"; //$NON-NLS-1$
  String ICONID_SKF_SAD            = "skf-sad"; //$NON-NLS-1$

  /**
   * Constants registration.
   *
   * @param aWinContext {@link IEclipseContext} - windows level context
   */
  static void init( IEclipseContext aWinContext ) {
    // register plug-in built-in icons
    ITsIconManager iconManager = aWinContext.get( ITsIconManager.class );
    iconManager.registerStdIconByIds( Activator.PLUGIN_ID, ISkfSadGuiConstants.class, PREFIX_OF_ICON_FIELD_NAME );
  }

}
