package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.events.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.archive.*;
import org.toxsoft.skf.sad.lib.impl.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.connection.*;

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
@SuppressWarnings( "javadoc" ) // TODO remove after API stabilization
public interface ISkSadService
    extends ISkService {

  /**
   * Service identifier.
   */
  String SERVICE_ID = ISkHardConstants.SK_SYSEXT_SERVICE_ID_PREFIX + ".StandAloneDocs"; //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // document type management

  IStridablesList<ISkSadDocType> listTypes();

  ISkSadDocType findDocType( String aTypeId );

  ISkSadDocType defineDocType( String aTypeId, IOptionSet aParams );

  void removeDocType( String aDocType );

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

  /**
   * Sets the document opening routine.
   * <p>
   * In the different environments opening the documents, namely the {@link ISkConnection} creation, might require
   * additional action. For example in GUI environments M5 support must be initialized. Defining the opener allows to
   * determine such additional actions.
   *
   * @param aOpener {@link AbstractDocumentOpener} - the document opening routine
   */
  void setDocumentOpener( AbstractDocumentOpener aOpener );

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
  default ISkSadDocType getDocType( String aTypeId ) {
    return TsItemNotFoundRtException.checkNoNull( findDocType( aTypeId ) );
  }

}
