package org.toxsoft.skf.sad.skadmin.sad;

import static org.toxsoft.core.tslib.bricks.validator.ValidationResult.*;
import static org.toxsoft.skf.sad.skadmin.l10n.ISkfSadSkadminSharedResources.*;
import static org.toxsoft.skf.sad.skadmin.sad.IHardConstants.*;
import static org.toxsoft.uskat.skadmin.core.EAdminCmdContextNames.*;

import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.impl.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.legacy.plexy.*;
import org.toxsoft.uskat.skadmin.core.*;
import org.toxsoft.uskat.skadmin.core.impl.*;

/**
 * skadmin command: list SAD folders.
 *
 * @author hazard157
 */
public class AdminCmdListFolders
    extends AbstractAdminCmd {

  /**
   * The command alias {@link #alias()}.
   */
  public static final String CMD_ALIAS = "lsf"; //$NON-NLS-1$

  /**
   * The command ID.
   */
  public static final String CMD_ID = CMD_ID_PREFIX + "." + CMD_ALIAS; //$NON-NLS-1$

  /**
   * Constructor.
   */
  public AdminCmdListFolders() {
    addArg( CTX_SK_CORE_API );
  }

  // ------------------------------------------------------------------------------------
  // AbstractAdminCmd
  //

  @Override
  public String id() {
    return CMD_ID;
  }

  @Override
  public String alias() {
    return CMD_ALIAS;
  }

  @Override
  public String nmName() {
    return STR_CMD_LIST_FOLDERS;
  }

  @Override
  public String description() {
    return STR_CMD_LIST_FOLDERS_D;
  }

  @Override
  public IPlexyType resultType() {
    return IPlexyType.NONE;
  }

  @Override
  public IStringList roles() {
    return IStringList.EMPTY;
  }

  @Override
  @SuppressWarnings( "boxing" )
  public void doExec( IStringMap<IPlexyValue> aArgValues, IAdminCmdCallback aCallback ) {
    ISkCoreApi coreApi = argSingleRef( CTX_SK_CORE_API );
    if( !coreApi.services().hasKey( ISkSadService.SERVICE_ID ) ) {
      print( aCallback, "There is no SAD service in this Sk-connection" );
      resultFail();
      return;
    }
    ISkSadService sadService = coreApi.getService( ISkSadService.SERVICE_ID );
    print( aCallback, "List of folders (ID - DocsQtty 'NAME' (DESCRIPTION):" );
    IStridablesList<ISkSadFolder> ff = sadService.listFolders();
    for( ISkSadFolder f : ff ) {
      int docsCount = f.listDocuments().size();
      print( aCallback, "  %s - %d '%s' (%s)", f.id(), docsCount, f.nmName(), f.description() ); //$NON-NLS-1$
    }
    print( aCallback, "Folders count = %d", ff.size() );
    resultOk();
  }

  @Override
  protected IList<IPlexyValue> doPossibleValues( String aArgId, IStringMap<IPlexyValue> aArgValues ) {
    return IList.EMPTY;
  }

  // ------------------------------------------------------------------------------------
  // Внутренние методы
  //

  /**
   * Вывести сообщение в callback клиента
   *
   * @param aMessage String - текст сообщения
   * @param aArgs Object[] - аргументы сообщения
   * @throws TsNullArgumentRtException любой аргумент = null
   */
  private static void print( IAdminCmdCallback aCallback, String aMessage, Object... aArgs ) {
    aCallback.onNextStep( new ElemArrayList<>( info( aMessage, aArgs ) ), 0, 0, false );
  }
}
