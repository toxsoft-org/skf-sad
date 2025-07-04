package org.toxsoft.skf.sad.lib.impl;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.events.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.skf.sad.lib.archive.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.devapi.*;
import org.toxsoft.uskat.core.impl.*;

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
  private static class Eventer
      extends AbstractTsEventer<ISkSadServiceListener> {

    @Override
    protected boolean doIsPendingEvents() {
      return false;
    }

    @Override
    protected void doFirePendingEvents() {
    }

    @Override
    protected void doClearPendingEvents() {
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
      return null;
    }

    @Override
    public ValidationResult canCreateDocType( String aTypeId, IOptionSet aParams ) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public ValidationResult canEditDocType( ISkSadFolder aDocType, IOptionSet aNewParams ) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public ValidationResult canRemoveDocType( String aTypeId ) {
      // TODO Auto-generated method stub
      return null;
    }

  }

  private final Svs     svs     = new Svs();
  private final Eventer eventer = new Eventer();

  SkExtServiceSad( IDevCoreApi aCoreApi ) {
    super( SERVICE_ID, aCoreApi );
    // validationSupport.addValidator( builtinValidator );
  }

  // ------------------------------------------------------------------------------------
  // AbstractSkService
  //

  @Override
  protected void doInit( ITsContextRo aArgs ) {
    // TODO SkExtServiceSad.doInit()
  }

  @Override
  protected void doClose() {
    // TODO SkExtServiceSad.doClose()
  }

  // ------------------------------------------------------------------------------------
  // ISkSadService
  //

  @Override
  public IStridablesList<ISkSadFolder> listFolders() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ISkSadFolder findFolder( String aFolderId ) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ISkSadFolder createFolder( String aFolderId, IOptionSet aParams ) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void removeFolder( String aFolderId ) {
    // TODO Auto-generated method stub

  }

  @Override
  public ISkSadArchivedDocumentsStorage getDocumentsArchive() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setDocumentOpener( AbstractDocumentOpener aOpener ) {
    // TODO Auto-generated method stub

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
