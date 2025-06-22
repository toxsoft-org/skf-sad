package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.coll.helpers.*;

/**
 * {@link ISkSadService} events listener.
 *
 * @author hazard157
 */
public interface ISkSadServiceListener {

  void onDocTypeChanged( ISkSadService aSource, ECrudOp aOp, String aTypeId );

  // TODO separate document params and content editing events ???
  // TODO document /open/edit/close events

  void onDocumentChanged( ISkSadService aSource, ISkSadFolder aType, ECrudOp aOp, String aDocId );

  void onDocumentState( ISkSadService aSource, ISkSadDocument aDoc, Object... aFoo );

  // TODO API for other actions

}
