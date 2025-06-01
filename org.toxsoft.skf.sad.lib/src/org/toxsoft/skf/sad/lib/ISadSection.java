package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;

public interface ISadSection
    extends IStridable {

  IStridablesListEdit<ISadDocument> listDocuments();

  ISadDocument findDocument( String aDocumentId );

  ISadDocument getDocument( String aDocumentId );

  IStridablesListEdit<ISadDocument> listArchivedDocuments();

  void setDocumentState( String aDocument, boolean aArchived );

}
