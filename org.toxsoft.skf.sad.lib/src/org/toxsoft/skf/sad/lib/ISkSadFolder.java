package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.utils.*;
import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.archive.*;
import org.toxsoft.uskat.core.api.objserv.*;

/**
 * The folder of the documents.
 * <p>
 * Folder has an application-specific properties {@link #params()}.
 *
 * @author hazard157
 */
public interface ISkSadFolder
    extends ISkObject, IStridableParameterized, IParameterizedBatchEdit {

  /**
   * Returns all documents in this folder.
   *
   * @return {@link IStridablesListEdit}&lt;{@link ISkSadDocument}&gt; - the list of documents
   */
  IStridablesListEdit<ISkSadDocument> listDocuments();

  /**
   * Searches document by ID in this folder .
   *
   * @param aDocumentId String - the document ID
   * @return {@link ISkSadDocument} - found document or <code>null</code>
   */
  ISkSadDocument findDocument( String aDocumentId );

  /**
   * Creates the new document.
   * <p>
   * Specifying an existing document as a template creates the copy with new ID.
   *
   * @param aDocId String - ID of the document to be created
   * @param aTemplateDocId - template document iD or {@link IStridable#NONE_ID}
   * @param aParams {@link IOptionSet} - application-specific document properties
   * @return {@link ISkSadDocument} - created document
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException failed {@link ISkSadServiceValidator}
   */
  ISkSadDocument createDocument( String aDocId, String aTemplateDocId, IOptionSet aParams );

  /**
   * Permanently deletes document and it's data.
   *
   * @param aDocId String - the document ID
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException failed {@link ISkSadServiceValidator}
   */
  void removeDocument( String aDocId );

  /**
   * Moves document to the archive {@link ISkSadArchivedDocumentsStorage}.
   * <p>
   * Note: This is an irreversible operation; an archived document cannot be restored as common document.
   *
   * @param aDocId String - the document ID
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException failed {@link ISkSadServiceValidator}
   */
  void archiveDocument( String aDocId );

  // ------------------------------------------------------------------------------------
  // Inline methods for convenience

  @SuppressWarnings( "javadoc" )
  default ISkSadDocument getDocument( String aDocumentId ) {
    return TsItemNotFoundRtException.checkNoNull( findDocument( aDocumentId ) );
  }

  @SuppressWarnings( "javadoc" )
  default ISkSadDocument createDocument( String aDocId, IOptionSet aParams ) {
    return createDocument( aDocId, IStridable.NONE_ID, aParams );
  }

}
