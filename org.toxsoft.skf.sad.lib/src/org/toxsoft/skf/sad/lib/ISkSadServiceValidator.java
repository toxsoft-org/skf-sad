package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.validator.*;

/**
 * SAD entities editing validator.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" )
public interface ISkSadServiceValidator {

  ValidationResult canCreateFolder( String aFolderId, IOptionSet aParams );

  ValidationResult canEditFolderParams( ISkSadFolder aFolder, IOptionSet aNewParams );

  ValidationResult canSetFolderNameAndDescription( ISkSadFolder aFolder, String aName, String aDescription );

  ValidationResult canRemoveFolder( String aFolderId );

  ValidationResult canCreateDocument( ISkSadFolder aFolder, String aDocumentId, String aTemplateDocId,
      IOptionSet aParams );

  ValidationResult canEditDocumentParams( ISkSadFolder aFolder, ISkSadDocument aDocument, IOptionSet aNewParams );

  ValidationResult canSetDocumentNameAndDescription( ISkSadFolder aFolder, ISkSadDocument aDocument, String aName,
      String aDescription );

  ValidationResult canRemoveDocument( ISkSadFolder aFolder, String aDocumentId );

}
