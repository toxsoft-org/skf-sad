package org.toxsoft.skf.sad.gui.km5;

import static org.toxsoft.core.tslib.utils.TsLibUtils.*;
import static org.toxsoft.skf.sad.gui.ISkfSadGuiConstants.*;

import java.io.*;

import org.toxsoft.core.tsgui.bricks.actions.asp.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.dialogs.*;
import org.toxsoft.core.tsgui.rcp.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.core.tslib.utils.logs.impl.*;
import org.toxsoft.skf.sad.lib.*;

/**
 * Base class provides actions to download/upload selected SAD content.
 *
 * @author hazard157
 */
public abstract class AspExpImpSadContent
    extends MethodPerActionTsActionSetProvider
    implements ITsGuiContextable {

  private final ITsGuiContext tsContext;

  private String lastPath = EMPTY_STRING;

  /**
   * Constructor.
   *
   * @param aContext {@link ITsGuiContext} - the context
   * @param aIsViewerMode boolean - <code>true</code> to create without import action
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public AspExpImpSadContent( ITsGuiContext aContext, boolean aIsViewerMode ) {
    TsNullArgumentRtException.checkNull( aContext );
    tsContext = aContext;
    defineAction( ACDEF_DOWNLOAD_SAD_CONTENT, this::doDownload, this::isSel );
    defineAction( ACDEF_UPLOAD_SAD_CONTENT, this::doUpload, this::isSel );
  }

  // ------------------------------------------------------------------------------------
  // Actions
  //

  void doDownload() {
    ISkSadDocument doc = selectedDoc();
    if( doc != null ) {
      File f = TsRcpDialogUtils.askFileSave( getShell(), lastPath );
      if( f != null ) {
        try {
          doc.downloadSadContentToTheFile( f );
        }
        catch( Exception ex ) {
          LoggerUtils.errorLogger().error( ex );
          TsDialogUtils.error( getShell(), ex );
        }
      }
    }
  }

  void doUpload() {
    ISkSadDocument doc = selectedDoc();
    if( doc != null ) {
      File f = TsRcpDialogUtils.askFileOpen( getShell(), lastPath );
      if( f != null ) {
        try {
          doc.uploadSadContentFromTheFile( f );
        }
        catch( Exception ex ) {
          LoggerUtils.errorLogger().error( ex );
          TsDialogUtils.error( getShell(), ex );
        }
      }
    }
  }

  boolean isSel() {
    return selectedDoc() != null;
  }

  // ------------------------------------------------------------------------------------
  // ITsGuiContextable
  //

  @Override
  public ITsGuiContext tsContext() {
    return tsContext;
  }

  // ------------------------------------------------------------------------------------
  // To override/implement
  //

  /**
   * Implementation must return document for export/import operations.
   *
   * @return {@link ISkSadDocument} - the document or <code>null</code>
   */
  protected abstract ISkSadDocument selectedDoc();

}
