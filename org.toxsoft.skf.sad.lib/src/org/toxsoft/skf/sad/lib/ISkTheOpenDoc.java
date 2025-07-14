package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.uskat.core.connection.*;

/**
 * The open document.
 * <p>
 * Note: open document must be closed either by {@link ISkTheOpenDoc#close()} or {@link ISkConnection#close()
 * conn().close()}.
 *
 * @author hazard157
 */
public interface ISkTheOpenDoc
    extends ICloseable {

  /**
   * Determines if this connection is open in read-only mode.
   * <p>
   * Note: read-only for SAD document means that open document can be freely edited using {@link ISkConnection}, however
   * changes will never be written back to the server.
   *
   * @return boolean - the read-oly mode flag<br>
   *         <b>true</b> - read-only document can be edited but changes are never saved back to the server;<br>
   *         <b>false</b> - editable document will be saved to the server when closed.
   */
  boolean isReadOnly();

  /**
   * Returns the open connection to the document content.
   *
   * @return {@link ISkConnection} - connection to the document content
   * @throws TsIllegalStateRtException document is closed
   */
  ISkConnection conn();

}
