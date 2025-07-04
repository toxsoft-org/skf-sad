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
  void onFoldersChanged( ISkSadService aSource, ECrudOp aOp, String aFolderId );

  /**
   * Notifies about changes in documents list and document properties.
   * <p>
   * Note: this event is <b>not</b> generated when document content changes.
   *
   * @param aSource {@link ISkSadService} - the event source
   * @param aFolder {@link ISkSadFolder} - the source folder
   * @param aOp {@link ECrudOp} - the event type
   * @param aDocumentId String - ID of changed document of <code>null</code> for batch operations
   */
  void onDocumentsChanged( ISkSadService aSource, ISkSadFolder aFolder, ECrudOp aOp, String aDocumentId );

  /**
   * Notifies about changes in the specified document content.
   *
   * @param aSource {@link ISkSadService} - the event source
   * @param aFolder {@link ISkSadFolder} - the source folder
   * @param aDocument {@link ISkSadDocument} - the changed document
   */
  void onDocumentContentChanged( ISkSadService aSource, ISkSadFolder aFolder, ISkSadDocument aDocument );

  /**
   * Notifies about changes in the specified document state.
   * <p>
   * TODO what is the document state
   *
   * @param aSource {@link ISkSadService} - the event source
   * @param aFolder {@link ISkSadFolder} - the source folder
   * @param aDocument {@link ISkSadDocument} - the changed document
   */
  void onDocumentStateChanged( ISkSadService aSource, ISkSadFolder aFolder, ISkSadDocument aDocument );

}
