package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.utils.TsLibUtils.*;
import static org.toxsoft.skf.sad.lib.impl.ISkSadInternalConstants.*;
import static org.toxsoft.skf.sad.lib.l10n.ISkSadLibSharedResources.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.events.*;
import org.toxsoft.core.tslib.bricks.events.msg.*;
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
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.skf.sad.lib.archive.*;
import org.toxsoft.uskat.core.*;
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
   * FIXME generate messages for siblings and listen them
   */

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
    public ValidationResult canCreateDocument( ISkSadFolder aFolder, String aDocumentId, IOptionSet aParams ) {
      TsNullArgumentRtException.checkNulls( aFolder, aDocumentId, aParams );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkSadServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canCreateDocument( aFolder, aDocumentId, aParams ) );
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
    public ValidationResult canRemoveFolder( String aFolderId ) {
      Skid skid = new Skid( CLSID_SAD_FOLDER, aFolderId );
      if( objServ().find( skid ) == null ) {
        return ValidationResult.warn( FMT_WARN_FOLDER_ID_NOT_EXISTS, aFolderId );
      }
      return ValidationResult.SUCCESS;
    }

    @Override
    public ValidationResult canCreateDocument( ISkSadFolder aFolder, String aDocumentId, IOptionSet aParams ) {
      ValidationResult vr = StridUtils.validateIdPath( aDocumentId );
      if( vr.isError() ) {
        return vr;
      }
      String docClassId = makeDocumentClassId( aFolder.id() );
      Skid skid = new Skid( docClassId, aDocumentId );
      if( objServ().find( skid ) != null ) {
        return ValidationResult.error( FMT_ERR_DOCUMENT_ID_ALREADY_EXISTS, aFolder.id(), aDocumentId );
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
    public ValidationResult canRemoveDocument( ISkSadFolder aFolder, String aDocumentId ) {
      String docClassId = makeDocumentClassId( aFolder.id() );
      Skid skid = new Skid( docClassId, aDocumentId );
      if( objServ().find( skid ) == null ) {
        return ValidationResult.warn( FMT_WARN_DOCUMENT_ID_NOT_EXISTS, aFolder.id(), aDocumentId );
      }
      return ValidationResult.SUCCESS;
    }
  };

  private final ClassClaimingCoreValidator claimingValidator = new ClassClaimingCoreValidator();

  private final Svs     svs     = new Svs();
  private final Eventer eventer = new Eventer();

  SkExtServiceSad( IDevCoreApi aCoreApi ) {
    super( SERVICE_ID, aCoreApi );
    svs.addValidator( builtinValidator );
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
    // claim on classes
    sysdescr().svs().addValidator( claimingValidator );
    objServ().svs().addValidator( claimingValidator );
    linkService().svs().addValidator( claimingValidator );
    clobService().svs().addValidator( claimingValidator );
  }

  @Override
  protected void doClose() {
    // nop
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
    // create folder object
    Skid skid = new Skid( CLSID_SAD_FOLDER, aFolderId );
    DtoObject dto = new DtoObject( skid );
    dto.attrs().setStr( AID_NAME, name );
    dto.attrs().setStr( AID_DESCRIPTION, description );
    dto.attrs().setValobj( ATRID_PARAMS, params );
    ISkSadFolder folder = objServ().defineObject( dto );
    // inform siblings
    GtMessage msg = makeSiblingMessage2( MSGID_FOLDER_CRUD, ///
        MSGARGID_CRUD_OP, avValobj( ECrudOp.CREATE ), ///
        MSGARGID_FOLDER_ID, folder.strid() ///
    );
    sendMessageToSiblings( msg );
    return folder;
  }

  @Override
  public void removeFolder( String aFolderId ) {
    TsValidationFailedRtException.checkError( svs.validator().canRemoveFolder( aFolderId ) );
    Skid skid = new Skid( CLSID_SAD_FOLDER, aFolderId );
    objServ().removeObject( skid );
    // inform siblings
    GtMessage msg = makeSiblingMessage2( MSGID_FOLDER_CRUD, ///
        MSGARGID_CRUD_OP, avValobj( ECrudOp.REMOVE ), ///
        MSGARGID_FOLDER_ID, aFolderId ///
    );
    sendMessageToSiblings( msg );
  }

  @Override
  public ISkSadArchivedDocumentsStorage getDocumentsArchive() {
    // TODO реализовать SkExtServiceSad.getDocumentsArchive()
    throw new TsUnderDevelopmentRtException( "SkExtServiceSad.getDocumentsArchive()" );
  }

  @Override
  public void setDocumentOpener( AbstractDocumentOpener aOpener ) {
    // TODO реализовать SkExtServiceSad.setDocumentOpener()
    throw new TsUnderDevelopmentRtException( "SkExtServiceSad.setDocumentOpener()" );
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
