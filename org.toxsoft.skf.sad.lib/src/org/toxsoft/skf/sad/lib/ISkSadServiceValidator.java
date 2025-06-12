package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.validator.*;

public interface ISkSadServiceValidator {

  ValidationResult canCreateDocType( String aTypeId, IOptionSet aParams );

  ValidationResult canEditDocType( ISkSadDocType aDocType, IOptionSet aNewParams );

  ValidationResult canRemoveDocType( String aTypeId );

}
