package org.toxsoft.skf.sad.lib;

import java.time.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.strid.*;

/**
 * The stand alone document.
 *
 * @author hazard157
 */
public interface ISkSadDocument
    extends IStridableParameterized {

  String typeId();

  String templateId();

  default boolean isTemplate() {
    return templateId().equals( IStridable.NONE_ID );
  }

  /**
   * Determines if the document is opened for editing.
   *
   * @return boolean - <code>true</code> document is editing now
   */
  boolean isOpen(); // This is a RTdata!

  IOpsBatchEdit paramsEditor();

  LocalDateTime contentModificationTime();

  LocalDateTime paramsModificationTime();

  LocalDateTime creationTime();

  ISkTheOpenDoc open( ITsContext aArgs );

  ISkTheOpenDoc openReadOnly( ITsContext aArgs );

}
