package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.archive.*;

/**
 * The type of the document.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" ) // TODO remove after API stabilization
public interface ISkSadDocType
    extends IStridableParameterized {

  IStridablesListEdit<ISkSadDocument> listDocuments( EQueryParamUsage aIsTemplate );

  ISkSadDocument findDocument( String aDocumentId );

  ISkSadDocument createDocument( String aDocId, String aTemplateDocId, IOptionSet aParams );

  default ISkSadDocument createTemplate( String aDocId, IOptionSet aParams ) {
    return createDocument( aDocId, IStridable.NONE_ID, aParams );
  }

  void removeDocument( String aDocId );

  /**
   * Moves document to the archive {@link ISkSadArchivedDocumentsStorage}.
   * <p>
   * Note: This is an irreversible operation; an archived document cannot be restored as common document.
   *
   * @param aDocId String - the document ID
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException no such document
   */
  void archiveDocument( String aDocId );

  // ------------------------------------------------------------------------------------
  // Inline methods for convenience

  @SuppressWarnings( "javadoc" )
  default IStridablesListEdit<ISkSadDocument> listAll() {
    return listDocuments( EQueryParamUsage.DONT_CARE );
  }

  @SuppressWarnings( "javadoc" )
  default IStridablesListEdit<ISkSadDocument> listDocuments() {
    return listDocuments( EQueryParamUsage.EXCLUDE );
  }

  @SuppressWarnings( "javadoc" )
  default IStridablesListEdit<ISkSadDocument> listTemplates() {
    return listDocuments( EQueryParamUsage.INCLUDE );
  }

  @SuppressWarnings( "javadoc" )
  default ISkSadDocument getDocument( String aDocumentId ) {
    return TsItemNotFoundRtException.checkNoNull( findDocument( aDocumentId ) );
  }

}
