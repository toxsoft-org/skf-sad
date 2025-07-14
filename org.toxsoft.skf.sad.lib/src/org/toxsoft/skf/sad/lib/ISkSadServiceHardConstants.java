package org.toxsoft.skf.sad.lib;

import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.core.tslib.gw.IGwHardConstants.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tslib.av.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.skf.sad.lib.impl.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * Package-private constants.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" )
public interface ISkSadServiceHardConstants {

  /**
   * Identifier prefix of all classes owned by this service.
   */
  String CLSID_PREFIX = SK_ID + ".sad_service.class"; //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // The folder

  String CLSID_SAD_FOLDER = CLSID_PREFIX + ".Folder"; //$NON-NLS-1$
  String ATRID_PARAMS     = "params";                 //$NON-NLS-1$

  IDtoClassInfo CLSINF_SAD_FOLDER = DtoClassInfo.create( CLSID_SAD_FOLDER, GW_ROOT_CLASS_ID, ///
      OptionSetUtils.createOpSet( ///
          TSID_NAME, "Folder", //$NON-NLS-1$
          TSID_DESCRIPTION, "The folder with the stand-alone documents", //$NON-NLS-1$
          OPDEF_SK_IS_SOURCE_CODE_DEFINED_CLASS, AV_TRUE, ///
          OPDEF_SK_IS_SOURCE_USKAT_SYSEXT_CLASS, AV_TRUE ///
      ), ///
      DtoAttrInfo.create2( ATRID_PARAMS, DT_OPTION_SET ) ///
  );

  // ------------------------------------------------------------------------------------
  // The document

  String CLSID_SAD_DOCUMENT              = CLSID_PREFIX + ".Document"; //$NON-NLS-1$
  String ATRID_TEMPLATE_DOC_ID           = "templateDocId";            //$NON-NLS-1$
  String ATRID_CREATION_TIME             = "creationTime";             //$NON-NLS-1$
  String ATRID_CONTENT_MODIFICATION_TIME = "contentModicifcationTime"; //$NON-NLS-1$
  String ATRID_ATTRS_MODIFICATION_TIME   = "attrsModicifcationTime";   //$NON-NLS-1$
  String ATRID_STORAGE_KIND              = "storageKind";              //$NON-NLS-1$
  String CLBID_CONTENT                   = "content";                  //$NON-NLS-1$
  String RTDID_EDITOR_USER_SKID          = "editorUserSkid";           //$NON-NLS-1$

  IDtoClassInfo CLSINF_SAD_DOCUMENT = DtoClassInfo.create( CLSID_SAD_DOCUMENT, GW_ROOT_CLASS_ID, ///
      OptionSetUtils.createOpSet( ///
          TSID_NAME, "Document", //$NON-NLS-1$
          TSID_DESCRIPTION, "The base class of all stand-alone documents, one subclass per folder", //$NON-NLS-1$
          OPDEF_SK_IS_SOURCE_CODE_DEFINED_CLASS, AV_TRUE, ///
          OPDEF_SK_IS_SOURCE_USKAT_SYSEXT_CLASS, AV_TRUE ///
      ), ///
      DtoAttrInfo.create2( ATRID_PARAMS, DT_OPTION_SET ), ///
      DtoAttrInfo.create2( ATRID_TEMPLATE_DOC_ID, DDEF_IDPATH, ///
          TSID_NAME, "Template ID", //$NON-NLS-1$
          TSID_DESCRIPTION, "Identifier of the document used as template for this document creation", //$NON-NLS-1$
          TSID_DEFAULT_VALUE, avStr( IStridable.NONE_ID ) ///
      ), ///
      DtoAttrInfo.create2( ATRID_CREATION_TIME, DT_LOCAL_DATE_TIME, ///
          TSID_NAME, "Created at", //$NON-NLS-1$
          TSID_DESCRIPTION, "Document creation time", //$NON-NLS-1$
          TSID_DEFAULT_VALUE, IAtomicValue.NULL ///
      ), ///
      DtoAttrInfo.create2( ATRID_CONTENT_MODIFICATION_TIME, DT_LOCAL_DATE_TIME, ///
          TSID_NAME, "Content at", //$NON-NLS-1$
          TSID_DESCRIPTION, "Document content modification time", //$NON-NLS-1$
          TSID_DEFAULT_VALUE, IAtomicValue.NULL ///
      ), ///
      DtoAttrInfo.create2( ATRID_ATTRS_MODIFICATION_TIME, DT_LOCAL_DATE_TIME, ///
          TSID_NAME, "Params at", //$NON-NLS-1$
          TSID_DESCRIPTION, "Document parameters modification time", //$NON-NLS-1$
          TSID_DEFAULT_VALUE, IAtomicValue.NULL ///
      ), ///
      DtoAttrInfo.create2( ATRID_STORAGE_KIND, DDEF_VALOBJ, ///
          TSID_NAME, "Storage kind", //$NON-NLS-1$
          TSID_DESCRIPTION, "Determines how the SAD content is stored", //$NON-NLS-1$
          TSID_KEEPER_ID, ESadContentStorageKind.KEEPER_ID, ///
          TSID_DEFAULT_VALUE, avValobj( ESadContentStorageKind.CLOB ) ///
      ), ///
      DtoClobInfo.create2( CLBID_CONTENT, ///
          TSID_NAME, "Content", //$NON-NLS-1$
          TSID_DESCRIPTION, "The content of the document stored in CLOB" //$NON-NLS-1$
      ), ///
      DtoRtdataInfo.create2( RTDID_EDITOR_USER_SKID, DT_SKID, true, false, false, 1000, ///
          TSID_NAME, "Editor user", //$NON-NLS-1$
          TSID_NAME, "SKID of the user currently editing the document", //$NON-NLS-1$
          TSID_DEFAULT_VALUE, avValobj( Skid.NONE ) ///
      ) ///
  );

}
