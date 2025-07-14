package org.toxsoft.skf.sad.lib.impl;

import java.io.*;

import org.toxsoft.skf.sad.lib.*;

/**
 * {@link AbstractSadContentStorage} implementation for kind {@link ESadContentStorageKind#CLOB_ZB64}.
 *
 * @author hazard157
 */
class SadContentStorageClobZipBase64
    extends AbstractSadContentStorage {

  static final AbstractSadContentStorage INSTANCE = new SadContentStorageClobZipBase64();

  private SadContentStorageClobZipBase64() {
    // nop
  }

  // ------------------------------------------------------------------------------------
  // AbstractSadContentStorage
  //

  @Override
  protected void doDownloadSadContentToTheFile( ISkSadDocument aSad, File aLocalFile ) {
    // TODO SadContentStorageClobZipBase64.doDownloadSadContentToTheFile()
  }

  @Override
  protected void doUploadSadContentFromTheFile( ISkSadDocument aSad, File aLocalFile ) {
    // TODO SadContentStorageClobZipBase64.doUploadSadContentFromTheFile()
  }

}
