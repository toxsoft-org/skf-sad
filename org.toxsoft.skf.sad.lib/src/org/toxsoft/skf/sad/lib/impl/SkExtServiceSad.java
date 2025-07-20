package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.utils.TsLibUtils.*;
import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;
import static org.toxsoft.skf.sad.lib.impl.ISkSadInternalConstants.*;
import static org.toxsoft.skf.sad.lib.l10n.ISkSadLibSharedResources.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import java.io.*;
import java.nio.file.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.events.*;
import org.toxsoft.core.tslib.bricks.events.msg.*;
import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.strid.impl.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.bricks.validator.std.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.coll.primtypes.impl.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.core.tslib.utils.files.*;
import org.toxsoft.core.tslib.utils.txtmatch.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.skf.sad.lib.archive.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.devapi.*;
import org.toxsoft.uskat.core.impl.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * {@link ISkSadService} implementation.
 *
 * @author hazard157
 */
public class SkExtServiceSad
    extends AbstractSkService
    implements ISkSadService {

  /**
   * Service creator singleton.
   */
  public static final ISkServiceCreator<SkExtServiceSad> CREATOR = SkExtServiceSad::new;

  /**
   * {@link ISkSadService#eventer()} implementation.
   *
   * @author hazard157
   */
  private class Eventer
      extends AbstractTsEventer<ISkSadServiceListener> {

    record Item ( ECrudOp op, String id ) {
    }

    final IStringMapEdit<Item> docCrudEvMap = new StringMap<>();

    Item folderItem = null;

    @Override
    protected boolean doIsPendingEvents() {
      return folderItem != null || !docCrudEvMap.isEmpty();
    }

    @Override
    protected void doFirePendingEvents() {
      if( folderItem != null ) {
        reallyFireFolderChanged( folderItem.op, folderItem.id );
      }
      for( String fid : docCrudEvMap.keys() ) {
        Item item = docCrudEvMap.getByKey( fid );
        reallyFireDocumentChanged( fid, item.op, item.id );
      }
    }

    @Override
    protected void doClearPendingEvents() {
      folderItem = null;
      docCrudEvMap.clear();
    }

    private void reallyFireFolderChanged( ECrudOp aOp, String aFolderId ) {
      for( ISkSadServiceListener l : listeners() ) {
        l.onFolderChanged( SkExtServiceSad.this, aOp, aFolderId );
      }
    }

    private void reallyFireDocumentChanged( String aFolderId, ECrudOp aOp, String aDocumentId ) {
      for( ISkSadServiceListener l : listeners() ) {
        l.onDocumentChanged( SkExtServiceSad.this, aFolderId, aOp, aDocumentId );
      }
    }

    void fireFolderChanged( ECrudOp aOp, String aFolderId ) {
      if( isFiringPaused() ) {
        if( folderItem != null ) {
          folderItem = new Item( ECrudOp.LIST, null );
        }
        else {
          folderItem = new Item( aOp, aFolderId );
        }
      }
      else {
        reallyFireFolderChanged( aOp, aFolderId );
      }
    }

    void fireDocumentChanged( String aFolderId, ECrudOp aOp, String aDocumentId ) {
      if( isFiringPaused() ) {
        if( docCrudEvMap.hasKey( aFolderId ) ) {
          docCrudEvMap.put( aFolderId, new Item( ECrudOp.LIST, null ) );
        }
        else {
          docCrudEvMap.put( aFolderId, new Item( aOp, aDocumentId ) );
        }
      }
      else {
        reallyFireDocumentChanged( aFolderId, aOp, aDocumentId );
      }
    }

  }

  /**
   * {@link ISkSadService#svs()} implementation.
   * <p>
   * This is a package-private class.
   *
   * @author hazard157
   */
  class Svs
      extends AbstractTsValidationSupport<ISkSadServiceValidator>
      implements ISkSadServiceValidator {

    @Override
    public ISkSadServiceValidator validator() {
      return this;
    }

    @Override
    public ValidationResult canCreateFolder( String aFolderId, IOptionSet aParams ) {
      TsNullArgumentRtException.checkNulls( aFolderId, aParams );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canCreateFolder( aFolderId, aParams ) );
        if( vr.isError() ) {
          break;
        }
      }
      return vr;
    }

    @Override
    public ValidationResult canEditFolderParams( ISkSadFolder aFolder, IOptionSet aNewParams ) {
      TsNullArgumentRtException.checkNulls( aFolder, aNewParams );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canEditFolderParams( aFolder, aNewParams ) );
        if( vr.isError() ) {
          break;
        }
      }
      return vr;
    }

    @Override
    public ValidationResult canSetFolderNameAndDescription( ISkSadFolder aFolder, String aName, String aDescription ) {
      TsNullArgumentRtException.checkNulls( aFolder, aName, aDescription );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canSetFolderNameAndDescription( aFolder, aName, aDescription ) );
        if( vr.isError() ) {
          break;
        }
      }
      return vr;
    }

    @Override
    public ValidationResult canRemoveFolder( String aFolderId ) {
      TsNullArgumentRtException.checkNull( aFolderId );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canRemoveFolder( aFolderId ) );
        if( vr.isError() ) {
          break;
        }
      }
      return vr;
    }

    @Override
    public ValidationResult canCreateDocument( ISkSadFolder aFolder, String aDocumentId, String aTemplateDocId,
        IOptionSet aParams ) {
      TsNullArgumentRtException.checkNulls( aFolder, aDocumentId, aTemplateDocId, aParams );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canCreateDocument( aFolder, aDocumentId, aTemplateDocId, aParams ) );
        if( vr.isError() ) {
          break;
        }
      }
      return vr;
    }

    @Override
    public ValidationResult canEditDocumentParams( ISkSadFolder aFolder, ISkSadDocument aDocument,
        IOptionSet aNewParams ) {
      TsNullArgumentRtException.checkNulls( aFolder, aDocument, aNewParams );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canEditDocumentParams( aFolder, aDocument, aNewParams ) );
        if( vr.isError() ) {
          break;
        }
      }
      return vr;
    }

    @Override
    public ValidationResult canSetDocumentNameAndDescription( ISkSadFolder aFolder, ISkSadDocument aDocument,
        String aName, String aDescription ) {
      TsNullArgumentRtException.checkNulls( aFolder, aDocument, aName, aDescription );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr,
            v.canSetDocumentNameAndDescription( aFolder, aDocument, aName, aDescription ) );
        if( vr.isError() ) {
          break;
        }
      }
      return vr;
    }

    @Override
    public ValidationResult canRemoveDocument( ISkSadFolder aFolder, String aDocumentId ) {
      TsNullArgumentRtException.checkNull( aFolder, aDocumentId );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canRemoveDocument( aFolder, aDocumentId ) );
        if( vr.isError() ) {
          break;
        }
      }
      return vr;
    }

  }

  /**
   * Built-in non-removable validation rules.
   */
  private final ISkSadServiceValidator builtinValidator = new ISkSadServiceValidator() {

    @Override
    public ValidationResult canCreateFolder( String aFolderId, IOptionSet aParams ) {
      ValidationResult vr = StridUtils.validateIdPath( aFolderId );
      if( vr.isError() ) {
        return vr;
      }
      if( aFolderId.equals( IStridable.NONE_ID ) ) {
        return ValidationResult.error( FMT_ERR_CANT_CREATE_NONE_FOLDER, IStridable.NONE_ID );
      }
      Skid skid = new Skid( CLSID_SAD_FOLDER, aFolderId );
      if( objServ().find( skid ) != null ) {
        return ValidationResult.error( FMT_ERR_FOLDER_ID_ALREADY_EXISTS, aFolderId );
      }
      String name = aParams.getStr( AID_NAME, EMPTY_STRING );
      vr = ValidationResult.firstNonOk( vr, NameStringValidator.VALIDATOR.validate( name ) );
      return vr;
    }

    @Override
    public ValidationResult canEditFolderParams( ISkSadFolder aFolder, IOptionSet aNewParams ) {
      return ValidationResult.SUCCESS;
    }

    @Override
    public ValidationResult canSetFolderNameAndDescription( ISkSadFolder aFolder, String aName, String aDescription ) {
      return NameStringValidator.VALIDATOR.validate( aName );
    }

    @Override
    public ValidationResult canRemoveFolder( String aFolderId ) {
      Skid skid = new Skid( CLSID_SAD_FOLDER, aFolderId );
      ISkSadFolder folder = objServ().find( skid );
      if( folder == null ) {
        return ValidationResult.warn( FMT_WARN_FOLDER_ID_NOT_EXISTS, aFolderId );
      }
      int docsCount = folder.listDocuments().size();
      if( docsCount > 0 ) {
        return ValidationResult.error( FMT_ERR_CANT_REMOVE_FOLDER_WITH_DOCS, aFolderId, Integer.valueOf( docsCount ) );
      }
      return ValidationResult.SUCCESS;
    }

    @Override
    public ValidationResult canCreateDocument( ISkSadFolder aFolder, String aDocumentId, String aTemplateDocId,
        IOptionSet aParams ) {
      ValidationResult vr = StridUtils.validateIdPath( aDocumentId );
      if( vr.isError() ) {
        return vr;
      }
      if( aDocumentId.equals( IStridable.NONE_ID ) ) {
        return ValidationResult.error( FMT_ERR_CANT_CREATE_NONE_DOCUMENT, IStridable.NONE_ID );
      }
      String docClassId = makeDocumentClassId( aFolder.id() );
      Skid skid = new Skid( docClassId, aDocumentId );
      if( objServ().find( skid ) != null ) {
        return ValidationResult.error( FMT_ERR_DOCUMENT_ID_ALREADY_EXISTS, aFolder.id(), aDocumentId );
      }
      ISkSadDocument templateDoc = null;
      if( !aTemplateDocId.equals( IStridable.NONE_ID ) ) {
        templateDoc = aFolder.findDocument( aTemplateDocId );
        if( templateDoc == null ) {
          return ValidationResult.error( FMT_ERR_TEMPLATE_DOC_NOT_FOUND, aFolder.id(), aTemplateDocId );
        }
      }
      String name = aParams.getStr( AID_NAME, EMPTY_STRING );
      vr = ValidationResult.firstNonOk( vr, NameStringValidator.VALIDATOR.validate( name ) );
      return vr;
    }

    @Override
    public ValidationResult canEditDocumentParams( ISkSadFolder aFolder, ISkSadDocument aDocument,
        IOptionSet aNewParams ) {
      return ValidationResult.SUCCESS;
    }

    @Override
    public ValidationResult canSetDocumentNameAndDescription( ISkSadFolder aFolder, ISkSadDocument aDocument,
        String aName, String aDescription ) {
      return NameStringValidator.VALIDATOR.validate( aName );
    }

    @Override
    public ValidationResult canRemoveDocument( ISkSadFolder aFolder, String aDocumentId ) {
      String docClassId = makeDocumentClassId( aFolder.id() );
      Skid skid = new Skid( docClassId, aDocumentId );
      if( objServ().find( skid ) == null ) {
        return ValidationResult.warn( FMT_WARN_DOCUMENT_ID_NOT_EXISTS, aFolder.id(), aDocumentId );
      }
      return ValidationResult.SUCCESS;
    }
  };

  private final ClassClaimingCoreValidator    claimingValidator = new ClassClaimingCoreValidator();
  private final SkSadArchivedDocumentsStorage archStorage;

  private final File tmpDir;

  private final Svs     svs     = new Svs();
  private final Eventer eventer = new Eventer();

  SkExtServiceSad( IDevCoreApi aCoreApi ) {
    super( SERVICE_ID, aCoreApi );
    svs.addValidator( builtinValidator );
    archStorage = new SkSadArchivedDocumentsStorage( this );
    tmpDir = createTemporaryDirectory();
  }

  // ------------------------------------------------------------------------------------
  // AbstractSkService
  //

  @Override
  protected void doInit( ITsContextRo aArgs ) {
    // create classes and register object creators
    for( ISkSadInternalConstants.BuiltinClassDef b : BUILTIN_CLASS_DEFS ) {
      sysdescr().defineClass( b.dto() );
      if( b.objCreator() != null ) {
        objServ().registerObjectCreator( b.classId(), b.objCreator() );
      }
    }
    // register creator of document class and all subclasses
    TextMatcher rule = new TextMatcher( ETextMatchMode.STARTS, CLSID_SAD_DOCUMENT, true );
    objServ().registerObjectCreator( rule, SkSadDocument.CREATOR );
    // claim on classes
    sysdescr().svs().addValidator( claimingValidator );
    objServ().svs().addValidator( claimingValidator );
    linkService().svs().addValidator( claimingValidator );
    clobService().svs().addValidator( claimingValidator );
  }

  @Override
  protected void doClose() {
    // remove temporary directory on exit
    TsFileUtils.deleteDirectory( tmpDir, ILongOpProgressCallback.NONE );
  }

  @Override
  protected boolean doIsClassClaimedByService( String aClassId ) {
    return isSadClaimedClassId( aClassId );
  }

  @Override
  protected boolean onBackendMessage( GenericMessage aMessage ) {
    return switch( aMessage.messageId() ) {
      case MSGID_FOLDER_CRUD -> {
        eventer.fireFolderChanged( ///
            aMessage.args().getValobj( MSGARGID_CRUD_OP ), ///
            aMessage.args().getStr( MSGARGID_FOLDER_ID, null ) ///
        );
        yield true;
      }
      case MSGID_DOCUMENT_CRUD -> {
        eventer.fireDocumentChanged( ///
            aMessage.args().getStr( MSGARGID_FOLDER_ID ), ///
            aMessage.args().getValobj( MSGARGID_CRUD_OP ), ///
            aMessage.args().getStr( MSGARGID_DOCUMENT_ID, null ) ///
        );
        yield true;
      }
      default -> false;
    };
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private File createTemporaryDirectory() {
    File f;
    try {
      f = Files.createTempDirectory( SAD_CONTENT_LOCAL_STORAGE_TMP_DIR_PREFIX ).toFile();
      f.deleteOnExit();
      return f;
    }
    catch( IOException ex ) {
      logger().error( ex );
      throw new TsIoRtException( ex );
    }
  }

  // ------------------------------------------------------------------------------------
  // package API
  //

  File papiGetFileNameForClob( Gwid aClob ) {
    TsInternalErrorRtException.checkTrue( aClob.isAbstract() );
    TsInternalErrorRtException.checkTrue( aClob.kind() != EGwidKind.GW_CLOB );
    String name = aClob.skid().toString() + "---" + aClob.propId(); //$NON-NLS-1$
    return new File( tmpDir, name + '.' + SAD_CONTENT_LOCAL_STORAGE_FILE_EXTENSION );
  }

  File papiGetTemporaryFileNameForClob( Gwid aClob ) {
    TsInternalErrorRtException.checkTrue( aClob.isAbstract() );
    TsInternalErrorRtException.checkTrue( aClob.kind() != EGwidKind.GW_CLOB );
    String name = "tmp---" + aClob.skid().toString() + "---" + aClob.propId(); //$NON-NLS-1$ //$NON-NLS-2$
    return new File( tmpDir, name + '.' + SAD_CONTENT_LOCAL_STORAGE_FILE_EXTENSION );

  }

  void pauseCoreValidationAndEvents() {
    sysdescr().svs().pauseValidator( claimingValidator );
    objServ().svs().pauseValidator( claimingValidator );
    linkService().svs().pauseValidator( claimingValidator );
    clobService().svs().pauseValidator( claimingValidator );
    sysdescr().eventer().pauseFiring();
    objServ().eventer().pauseFiring();
    linkService().eventer().pauseFiring();
    clobService().eventer().pauseFiring();
  }

  void resumeCoreValidationAndEvents() {
    sysdescr().svs().resumeValidator( claimingValidator );
    objServ().svs().resumeValidator( claimingValidator );
    linkService().svs().resumeValidator( claimingValidator );
    clobService().svs().resumeValidator( claimingValidator );
    sysdescr().eventer().resumeFiring( true );
    objServ().eventer().resumeFiring( true );
    linkService().eventer().resumeFiring( true );
    clobService().eventer().resumeFiring( true );
  }

  // ------------------------------------------------------------------------------------
  // ISkSadService
  //

  @Override
  public IStridablesList<ISkSadFolder> listFolders() {
    IList<ISkSadFolder> ll = objServ().listObjs( CLSID_SAD_FOLDER, false );
    return new StridablesList<>( ll );
  }

  @Override
  public ISkSadFolder findFolder( String aFolderId ) {
    Skid skid = new Skid( CLSID_SAD_FOLDER, aFolderId );
    return objServ().find( skid );
  }

  @Override
  public ISkSadFolder createFolder( String aFolderId, IOptionSet aParams ) {
    TsValidationFailedRtException.checkError( svs.validator().canCreateFolder( aFolderId, aParams ) );
    // prepare attributes and remove system attributes if any found in aParameters
    String name = aParams.getStr( AID_NAME, EMPTY_STRING );
    String description = aParams.getStr( AID_DESCRIPTION, EMPTY_STRING );
    IOptionSetEdit params = new OptionSet();
    for( String pid : aParams.keys() ) {
      if( !isSkSysAttrId( pid ) ) {
        params.put( pid, aParams.getValue( pid ) );
      }
    }
    ISkSadFolder folder;
    pauseCoreValidationAndEvents();
    try {
      // create item class
      String classId = makeDocumentClassId( aFolderId );
      IDtoClassInfo docClassInfo = DtoClassInfo.create( classId, CLSID_SAD_DOCUMENT, aParams );
      sysdescr().defineClass( docClassInfo );
      // create folder object
      Skid skid = new Skid( CLSID_SAD_FOLDER, aFolderId );
      DtoObject dto = new DtoObject( skid );
      dto.attrs().setStr( AID_NAME, name );
      dto.attrs().setStr( AID_DESCRIPTION, description );
      dto.attrs().setValobj( ATRID_PARAMS, params );
      folder = objServ().defineObject( dto );
      // inform siblings
      GtMessage msg = makeSiblingMessage2( MSGID_FOLDER_CRUD, ///
          MSGARGID_CRUD_OP, avValobj( ECrudOp.CREATE ), ///
          MSGARGID_FOLDER_ID, folder.strid() ///
      );
      sendMessageToSiblings( msg );
    }
    finally {
      resumeCoreValidationAndEvents();
    }
    return folder;
  }

  @Override
  public void removeFolder( String aFolderId ) {
    TsValidationFailedRtException.checkError( svs.validator().canRemoveFolder( aFolderId ) );
    Skid skid = new Skid( CLSID_SAD_FOLDER, aFolderId );
    pauseCoreValidationAndEvents();
    try {
      // remove documents class
      String classId = makeDocumentClassId( aFolderId );
      sysdescr().removeClass( classId );
      // remove folder object
      objServ().removeObject( skid );
      // inform siblings
      GtMessage msg = makeSiblingMessage2( MSGID_FOLDER_CRUD, ///
          MSGARGID_CRUD_OP, avValobj( ECrudOp.REMOVE ), ///
          MSGARGID_FOLDER_ID, aFolderId ///
      );
      sendMessageToSiblings( msg );
    }
    finally {
      resumeCoreValidationAndEvents();
    }
  }

  @Override
  public ISkSadArchivedDocumentsStorage getDocumentsArchive() {
    return archStorage;
  }

  @Override
  public Svs svs() {
    return svs;
  }

  @Override
  public ITsEventer<ISkSadServiceListener> eventer() {
    return eventer;
  }

}
