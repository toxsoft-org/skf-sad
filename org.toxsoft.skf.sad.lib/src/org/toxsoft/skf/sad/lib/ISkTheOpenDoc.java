package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.utils.*;
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
   * Returns the open connection to the document content.
   *
   * @return {@link ISkConnection} - connection to the document content
   */
  ISkConnection conn();

  // TODO listener: inform read-only connection about document content/params change
  // TODO listener: inform read-only connection about document was released by the editor

  @Override
  default void close() {
    conn().close();
  }

}
