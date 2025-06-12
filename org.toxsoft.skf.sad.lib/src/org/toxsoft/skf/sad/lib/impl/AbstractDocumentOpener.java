package org.toxsoft.skf.sad.lib.impl;

import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.uskat.core.connection.*;

public abstract class AbstractDocumentOpener {

  // FIXME API ???

  protected ISkConnection openDocument( ISkSadDocument aDoc, ITsContext aArgs ) {
    // TODO реализовать AbstractDocumentOpener.openDocument()
    throw new TsUnderDevelopmentRtException( "AbstractDocumentOpener.openDocument()" );
  }

  protected abstract ISkConnection doCreateConnection( ISkSadDocument aDoc, ITsContext aConnArgs,
      ITsContext aOpenArgs );

  protected abstract void onClose();

  // TODO access to the document content is only from this class

  String getRawContent() {
    // TODO реализовать AbstractDocumentOpener.getRawContent()
    throw new TsUnderDevelopmentRtException( "AbstractDocumentOpener.getRawContent()" );
  }

  void setRawContent( String aContent ) {
    // TODO реализовать AbstractDocumentOpener.setRawContent()
    throw new TsUnderDevelopmentRtException( "AbstractDocumentOpener.setRawContent()" );
  }

}
