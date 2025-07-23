package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;

import org.toxsoft.core.tslib.av.*;
import org.toxsoft.core.tslib.bricks.strid.impl.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.coll.impl.*;
import org.toxsoft.skf.sad.lib.impl.ISkSadInternalConstants.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;

/**
 * Package-private constants.
 *
 * @author hazard157
 */
interface ISkSadInternalConstants {

  // ------------------------------------------------------------------------------------
  // builtin classes

  /**
   * Information about class to be defined in {@link SkExtServiceSad}.
   *
   * @param dto {@link IDtoClassInfo} - class definition
   * @param objCreator {@link ISkObjectCreator} - object creator or null
   * @author hazard157
   */
  record BuiltinClassDef ( IDtoClassInfo dto, ISkObjectCreator<?> objCreator ) {

    public BuiltinClassDef( IDtoClassInfo dto, ISkObjectCreator<?> objCreator ) {
      // TsNullArgumentRtException.checkNull( dto );
      this.dto = dto;
      this.objCreator = objCreator;
    }

    String classId() {
      return dto.id();
    }
  }

  /**
   * Classes to be created by {@link SkExtServiceSad}.
   */
  IList<BuiltinClassDef> BUILTIN_CLASS_DEFS = new ElemArrayList<>( ///
      new BuiltinClassDef( CLSINF_SAD_FOLDER, SkSadFolder.CREATOR ), ///
      new BuiltinClassDef( CLSINF_SAD_DOCUMENT, null ) // creator will be registered for document and all subclasses
  );

  // ------------------------------------------------------------------------------------
  // Sibling messages

  String MSGARGID_FOLDER_ID   = "folderId"; //$NON-NLS-1$
  String MSGARGID_DOCUMENT_ID = "docId";    //$NON-NLS-1$
  String MSGARGID_CRUD_OP     = "crudOp";   //$NON-NLS-1$

  /**
   * Message for siblings: SAD folder CRUD operation happened.
   * <p>
   * Arguments:
   * <ul>
   * <li>{@link #MSGARGID_FOLDER_ID} - folder ID, {@link EAtomicType#STRING};</li>
   * <li>{@link #MSGARGID_CRUD_OP} - CRUD operation, {@link EAtomicType#VALOBJ}, contains {@link ECrudOp}.</li>
   * </ul>
   */
  String MSGID_FOLDER_CRUD = "FolderCrud"; //$NON-NLS-1$

  /**
   * Message for siblings: SAD folder CRUD operation happened.
   * <p>
   * Arguments:
   * <ul>
   * <li>{@link #MSGARGID_FOLDER_ID} - folder ID, {@link EAtomicType#STRING};</li>
   * <li>{@link #MSGARGID_DOCUMENT_ID} - document ID, {@link EAtomicType#STRING};</li>
   * <li>{@link #MSGARGID_CRUD_OP} - CRUD operation, {@link EAtomicType#VALOBJ}, contains {@link ECrudOp}.</li>
   * </ul>
   */
  String MSGID_DOCUMENT_CRUD = "DocCrud"; //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // Logic

  String SAD_CONTENT_LOCAL_STORAGE_TMP_DIR_PREFIX = "skSadServiceTmp_"; //$NON-NLS-1$
  String SAD_CONTENT_LOCAL_STORAGE_FILE_EXTENSION = "textual";          //$NON-NLS-1$

  static String makeDocumentClassId( String aFolderId ) {
    StridUtils.checkValidIdPath( aFolderId );
    return StridUtils.makeIdPath( CLSID_SAD_DOCUMENT, aFolderId );
  }

  static String extractFolderIdFromDocumentClassId( String aDocClassId ) {
    return StridUtils.removeStartingIdPath( aDocClassId, CLSID_SAD_DOCUMENT );
  }

  static boolean isSadClaimedClassId( String aClassId ) {
    return StridUtils.startsWithIdPath( aClassId, CLSID_PREFIX );
  }
}
