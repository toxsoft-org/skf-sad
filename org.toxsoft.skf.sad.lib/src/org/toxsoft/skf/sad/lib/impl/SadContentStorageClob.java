package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;

import java.io.*;
import java.nio.file.*;

import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;

/**
 * {@link AbstractSadContentStorage} implementation for kind {@link ESadContentStorageKind#CLOB}.
 *
 * @author hazard157
 */
class SadContentStorageClob
    extends AbstractSadContentStorage {

  static final AbstractSadContentStorage INSTANCE = new SadContentStorageClob();

  private SadContentStorageClob() {
    // nop
  }

  // ------------------------------------------------------------------------------------
  // AbstractSadContentStorage
  //

  @Override
  protected void doDownloadSadContentToTheFile( ISkSadDocument aSad, File aLocalFile ) {
    try {
      Gwid clobGwid = Gwid.createClob( aSad.skid(), CLBID_CONTENT );
      String clobString = aSad.coreApi().clobService().readClob( clobGwid );
      Files.writeString( aLocalFile.toPath(), clobString );
    }
    catch( IOException ex ) {
      throw new TsIoRtException( ex );
    }
  }

  @Override
  protected void doUploadSadContentFromTheFile( ISkSadDocument aSad, File aLocalFile ) {
    try {
      String clobString = Files.readString( aLocalFile.toPath() );
      Gwid clobGwid = Gwid.createClob( aSad.skid(), CLBID_CONTENT );
      aSad.coreApi().clobService().writeClob( clobGwid, clobString );
    }
    catch( IOException ex ) {
      throw new TsIoRtException( ex );
    }
  }

}
