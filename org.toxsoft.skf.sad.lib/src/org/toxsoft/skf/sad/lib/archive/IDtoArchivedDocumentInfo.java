package org.toxsoft.skf.sad.lib.archive;

import java.time.*;

import org.toxsoft.core.tslib.bricks.strid.*;

public interface IDtoArchivedDocumentInfo
    extends IStridableParameterized {

  String typeId();

  String templateId();

  LocalDateTime archivedTime();

  // VERSIONS ?

}
