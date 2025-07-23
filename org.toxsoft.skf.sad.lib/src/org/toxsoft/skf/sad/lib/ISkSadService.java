package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.events.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.archive.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.*;
import org.toxsoft.uskat.core.api.objserv.*;

/**
 * SAD (Stand-Alone Documents) service.
 * <p>
 * Main concepts:
 * <ul>
 * <li>document as ISkConnection backend;</li>
 * <li>document type - ???;</li>
 * <li>archive and archived documents - ???;</li>
 * <li>opening document as ISkConnection - ???;</li>
 * </ul>
 *
 * @author hazard157
 */
public interface ISkSadService
    extends ISkService {

  /**
   * Service identifier.
   */
  String SERVICE_ID = ISkHardConstants.SK_SYSEXT_SERVICE_ID_PREFIX + ".StandAloneDocs"; //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // document type management

  /**
   * Returns all folders.
   *
   * @return {@link IStridablesListEdit}&lt;{@link ISkSadFolder}&gt; - the list of folders
   */
  IStridablesList<ISkSadFolder> listFolders();

  /**
   * Returns the folder by ID if any found.
   *
   * @param aFolderId String - the folder ID
   * @return {@link ISkSadFolder} - found folder or <code>null</code>
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  ISkSadFolder findFolder( String aFolderId );

  /**
   * Creates the new folder.
   *
   * @param aFolderId String - the folder ID (an IDpath)
   * @param aParams {@link IOptionSet} - application-specific properties of the folder
   * @return {@link ISkSadFolder} - created folder
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException failed {@link ISkSadServiceValidator}
   */
  ISkSadFolder createFolder( String aFolderId, IOptionSet aParams );

  /**
   * Removes the folder and it's content.
   * <p>
   * Note: all documents and their contents will be permanently lost.
   *
   * @param aFolderId String - the folder ID
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException failed {@link ISkSadServiceValidator}
   */
  void removeFolder( String aFolderId );

  // ------------------------------------------------------------------------------------
  // miscellaneous

  /**
   * Returns the archived documents storage.
   * <p>
   * Note: changes in archived documents does not generates events.
   *
   * @return {@link ISkSadArchivedDocumentsStorage} - the documents archive
   */
  ISkSadArchivedDocumentsStorage getDocumentsArchive();

  // ------------------------------------------------------------------------------------
  // Service support

  /**
   * Returns the service validator.
   *
   * @return {@link ITsValidationSupport}&lt;{@link ISkObjectServiceValidator}&gt; - the service validator
   */
  ITsValidationSupport<ISkSadServiceValidator> svs();

  /**
   * Returns the service eventer.
   *
   * @return {@link ITsEventer}&lt;{@link ISkObjectServiceListener}&gt; - the service eventer
   */
  ITsEventer<ISkSadServiceListener> eventer();

  // ------------------------------------------------------------------------------------
  // Inline methods for convenience

  @SuppressWarnings( "javadoc" )
  default ISkSadFolder getFolder( String aFolderId ) {
    return TsItemNotFoundRtException.checkNull( findFolder( aFolderId ) );
  }

}
