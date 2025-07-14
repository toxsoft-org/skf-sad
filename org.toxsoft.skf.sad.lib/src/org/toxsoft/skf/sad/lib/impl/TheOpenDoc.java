package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;

import java.io.*;

import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.connection.*;

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

  TheOpenDoc( ISkConnection aConn, SkSadDocument aSad, SkExtServiceSad aService, boolean aReadOnly ) {
    sadDocument = aSad;
    sadService = aService;
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
        File file = sadService.papiGetTemporaryFileNameForClob( clobGwid );
        sadService.pauseCoreValidationAndEvents();
        sadDocument.uploadSadContentFromTheFile( file );
        file.delete();
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

    skConn = null;
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
