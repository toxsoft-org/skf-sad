package org.toxsoft.skf.sad.lib;

import java.time.*;

import org.toxsoft.core.tslib.bricks.strid.*;

public interface ISadDocument
    extends IStridable {

  String getSectionId();

  LocalDateTime getModificationTime();

  LocalDateTime getCreationTime();

}
