package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.validator.*;

public interface ISkSadServiceValidator {

  ValidationResult canCreateFolder( String aFolderId, IOptionSet aParams );

  ValidationResult canEditFolderParams( ISkSadFolder aFolder, IOptionSet aNewParams );

  ValidationResult canRemoveFolder( String aFolderId );

  ValidationResult canCreateDocument( String aDocumentId, IOptionSet aParams );

  ValidationResult canEditDocumentParams( ISkSadDocument aDocument, IOptionSet aNewParams );

  ValidationResult canRemoveDocument( String aDocumentId );

}
