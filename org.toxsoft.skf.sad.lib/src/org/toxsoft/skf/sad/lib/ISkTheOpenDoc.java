package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.uskat.core.connection.*;

/**
 * The open document.
 *
 * @author hazard157
 */
public interface ISkTheOpenDoc
    extends ICloseable {

  ISkConnection conn();

  @Override
  default void close() {
    conn().close();
  }

}
