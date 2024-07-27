package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;

public interface ISadSection
    extends IStridable {

  IStridablesListEdit<ISadDocument> listDocuments();

  ISadDocument findDocument( String aDucumentId );

  ISadDocument getDocument( String aDucumentId );

  IStridablesListEdit<ISadDocument> listArchivedDocuments();

  void setDocumentState( String aDucument, boolean aArchived );

}
