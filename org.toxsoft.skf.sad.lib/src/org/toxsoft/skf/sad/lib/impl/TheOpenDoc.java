package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;

import java.io.*;

import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.connection.*;

/**
 * {@link ISkTheOpenDoc} implementation.
 *
 * @author hazard157
 */
class TheOpenDoc
    implements ISkTheOpenDoc {

  private final ISkConnectionListener connectionListener = ( aSource, aOldState ) -> {
    if( !aSource.state().isOpen() ) {
      whenConnectionClosed();
    }
  };

  private final SkExtServiceSad sadService;
  private final SkSadDocument   sadDocument;
  private final boolean         readOnly;

  private ISkConnection skConn = null;

  /**
   * Constructor.
   *
   * @param aConn {@link ISkConnection} - open connection
   * @param aSad {@link SkSadDocument} - the document
   * @param aReadOnly boolean - read-only connection flag
   */
  TheOpenDoc( ISkConnection aConn, SkSadDocument aSad, boolean aReadOnly ) {
    sadDocument = aSad;
    sadService = sadDocument.sadFolder().sadService();
    readOnly = aReadOnly;
    skConn = aConn;
    skConn.addConnectionListener( connectionListener );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private void whenConnectionClosed() {
    if( skConn == null ) {
      return;
    }
    if( !readOnly ) {
      try {
        Gwid clobGwid = Gwid.createClob( sadDocument.skid(), CLBID_CONTENT );
        File file = sadService.papiGetFileNameForClob( clobGwid );
        sadService.pauseCoreValidationAndEvents();
        sadDocument.uploadSadContentFromTheFile( file );
        file.delete();
        skConn = null;
      }
      catch( Exception ex ) {
        // need more precise error handling
        sadService.logger().error( ex );
      }
      finally {
        sadService.resumeCoreValidationAndEvents();
      }
    }

    // TODO change document state

    // TODO inform on document state change

  }

  // ------------------------------------------------------------------------------------
  // ISkTheOpenDoc
  //

  @Override
  public boolean isReadOnly() {
    return readOnly;
  }

  @Override
  public ISkConnection conn() {
    TsIllegalStateRtException.checkNull( skConn );
    return skConn;
  }

  // ------------------------------------------------------------------------------------
  // ICloseable
  //

  @Override
  public void close() {
    if( skConn != null ) {
      skConn.close();
    }
  }

}
