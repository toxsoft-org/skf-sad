package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.validator.*;

public interface ISkSadServiceValidator {

  ValidationResult canCreateDocType( String aTypeId, IOptionSet aParams );

  ValidationResult canEditDocType( ISkSadFolder aDocType, IOptionSet aNewParams );

  ValidationResult canRemoveDocType( String aTypeId );

  // API for other actions

}
