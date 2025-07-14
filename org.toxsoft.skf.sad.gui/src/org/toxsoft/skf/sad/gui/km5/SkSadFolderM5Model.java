package org.toxsoft.skf.sad.gui.km5;

import static org.toxsoft.core.tsgui.m5.IM5Constants.*;
import static org.toxsoft.skf.sad.gui.ISkfSadGuiConstants.*;
import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;
import static org.toxsoft.skf.sad.lib.l10n.ISkSadLibSharedResources.*;

import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * M5-model of {@link SkSadFolderM5Model}.
 *
 * @author hazard157
 */
public class SkSadFolderM5Model
    extends KM5ModelBasic<ISkSadFolder> {

  /**
   * The model ID.
   */
  public static final String MODEL_ID = SKF_SAD_M5_ID + ".Folder"; //$NON-NLS-1$

  /**
   * Attribute {@link ISkSadFolder#params()}.
   */
  public final IM5AttributeFieldDef<ISkSadFolder> PARAMS =
      new KM5AttributeFieldDef<>( CLSINF_SAD_FOLDER.attrInfos().getByKey( ATRID_PARAMS ) );

  /**
   * Constructor.
   *
   * @param aConn {@link ISkConnection} - Sk-connection to be used in constructor
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  @SuppressWarnings( "rawtypes" )
  public SkSadFolderM5Model( ISkConnection aConn ) {
    super( MODEL_ID, ISkSadFolder.class, aConn );
    STRID.setNameAndDescription( STR_ATR_FOLDER_ID, STR_ATR_FOLDER_ID_D );
    ((KM5AttributeFieldDef)PARAMS).setFlags( M5FF_HIDDEN );
    addFieldDefs( STRID, NAME, DESCRIPTION, PARAMS );
  }

  @Override
  protected IM5LifecycleManager<ISkSadFolder> doCreateLifecycleManager( Object aMaster ) {
    return new SkSadFolderM5LifecycleManager( this, ISkConnection.class.cast( aMaster ) );
  }

}
