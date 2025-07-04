package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.skf.sad.lib.impl.ISkSadInternalConstants.*;

import java.time.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.impl.*;

/**
 * {@link ISkSadDocument} implementation.
 *
 * @author hazard157
 */
class SkSadDocument
    extends SkObject
    implements ISkSadDocument {

  /**
   * The object creator singleton.
   */
  public static final ISkObjectCreator<SkSadDocument> CREATOR = SkSadDocument::new;

  private SkSadDocument( Skid aSkid ) {
    super( aSkid );
  }

  // ------------------------------------------------------------------------------------
  // ISkSadDocument
  //

  @Override
  public IOptionSet params() {
    return attrs().getValobj( ATRID_PARAMS );
  }

  @Override
  public String templateDocId() {
    return attrs().getStr( ATRID_TEMPLATE_DOC_ID );
  }

  @Override
  public LocalDateTime contentModificationTime() {
    return attrs().getValobj( ATRID_CONTENT_MODIFICATION_TIME );
  }

  @Override
  public LocalDateTime paramsModificationTime() {
    return attrs().getValobj( ATRID_PARAMS_MODIFICATION_TIME );
  }

  @Override
  public LocalDateTime creationTime() {
    return attrs().getValobj( ATRID_CREATION_TIME );
  }

  @Override
  public IOpsBatchEdit paramsBatchEditor() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Pair<ISkTheOpenDoc, ValidationResult> tryOpen( ITsContext aArgs ) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ISkTheOpenDoc openReadOnly( ITsContext aArgs ) {
    // TODO Auto-generated method stub
    return null;
  }

}
