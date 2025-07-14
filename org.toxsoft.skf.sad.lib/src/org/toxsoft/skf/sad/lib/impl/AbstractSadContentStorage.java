package org.toxsoft.skf.sad.lib.impl;

import java.io.*;

import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.core.tslib.utils.files.*;
import org.toxsoft.skf.sad.lib.*;

/**
 * Base class for SAD document content storage implemention depending on {@link ESadContentStorageKind}.
 *
 * @author hazard157
 */
abstract class AbstractSadContentStorage {

  protected AbstractSadContentStorage() {
    // nnop
  }

  /**
   * Gets SAD document content from the server and writes itto the local file.
   *
   * @param aSad {@link ISkSadDocument} - the document
   * @param aLocalFile {@link File} - local file to write document content
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIoRtException I/O error or file access error
   */
  final void downloadSadContentToTheFile( ISkSadDocument aSad, File aLocalFile ) {
    TsNullArgumentRtException.checkNulls( aSad, aLocalFile );
    TsFileUtils.checkFileAppendable( aLocalFile );
    doDownloadSadContentToTheFile( aSad, aLocalFile );
  }

  /**
   * Reads SAD document content from the local file and stores it in the server.
   *
   * @param aSad {@link ISkSadDocument} - the document
   * @param aLocalFile {@link File} - local file to read document content
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIoRtException I/O error or file access error
   */
  void uploadSadContentFromTheFile( ISkSadDocument aSad, File aLocalFile ) {
    TsNullArgumentRtException.checkNulls( aSad, aLocalFile );
    TsFileUtils.checkFileReadable( aLocalFile );
    doUploadSadContentFromTheFile( aSad, aLocalFile );
  }

  // ------------------------------------------------------------------------------------
  // to override/implement
  //

  protected abstract void doDownloadSadContentToTheFile( ISkSadDocument aSad, File aLocalFile );

  protected abstract void doUploadSadContentFromTheFile( ISkSadDocument aSad, File aLocalFile );

}
