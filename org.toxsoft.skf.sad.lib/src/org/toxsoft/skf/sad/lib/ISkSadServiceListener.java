package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.coll.helpers.*;

/**
 * {@link ISkSadService} events listener.
 *
 * @author hazard157
 */
public interface ISkSadServiceListener {

  /**
   * Notifies about changes in folders list and folder properties.
   * <p>
   * Note: this event is <b>not</b> generated when folder content (documents list) changes.
   *
   * @param aSource {@link ISkSadService} - the event source
   * @param aOp {@link ECrudOp} - the event type
   * @param aFolderId String - ID of changed folder of <code>null</code> for batch operations
   */
  void onFolderChanged( ISkSadService aSource, ECrudOp aOp, String aFolderId );

  /**
   * Notifies about changes in documents list and document properties.
   * <p>
   * Note: this event is <b>not</b> generated when document content changes.
   *
   * @param aSource {@link ISkSadService} - the event source
   * @param aFolderId String - the source folder ID
   * @param aOp {@link ECrudOp} - the event type
   * @param aDocumentId String - ID of changed document of <code>null</code> for batch operations
   */
  void onDocumentChanged( ISkSadService aSource, String aFolderId, ECrudOp aOp, String aDocumentId );

  /**
   * Notifies about changes in the specified document content.
   *
   * @param aSource {@link ISkSadService} - the event source
   * @param aDocument {@link ISkSadDocument} - the changed document
   */
  void onDocumentContentChanged( ISkSadService aSource, ISkSadDocument aDocument );

  /**
   * Notifies about changes in the specified document state.
   * <p>
   * TODO what is the document state: OPEN, OPEN_READ_ONLY, COMMON ???
   *
   * @param aSource {@link ISkSadService} - the event source
   * @param aDocument {@link ISkSadDocument} - the changed document
   */
  void onDocumentStateChanged( ISkSadService aSource, ISkSadDocument aDocument );

}
