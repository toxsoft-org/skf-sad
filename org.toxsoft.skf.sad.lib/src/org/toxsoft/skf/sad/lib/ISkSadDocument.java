package org.toxsoft.skf.sad.lib;

import java.io.*;
import java.time.*;

import org.toxsoft.core.tslib.av.utils.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.impl.*;

/**
 * The stand alone document is a meta-information about documents together with the means to access document content,
 * <p>
 * To edit the document is must be opened (established connection) by {@link #tryOpen(ISkConnection, ITsContext)}.
 * Already editing document can not be opened for editing until open session is closed. However document may be open in
 * read-only mode simultaneously by any number of users, even if it is edited.
 *
 * @author hazard157
 */
public interface ISkSadDocument
    extends ISkObject, IStridableParameterized, IParameterizedBatchEdit {

  /**
   * Returns the document owner folder.
   *
   * @return {@link ISkSadFolder} - the document owner folder
   */
  ISkSadFolder sadFolder();

  /**
   * Opens the document for modification.
   * <p>
   * If document can not be opened for any reason, returns {@link Pair#left()} = <code>null</code> and
   * {@link Pair#right()} containing the reason of the failure. On success, {@link Pair#right()} contains
   * {@link ValidationResult#SUCCESS}.
   *
   * @param aConn {@link ISkConnection} - created closed instance of the connection
   * @param aArgs {@link ITsContext} - the opening arguments
   * @return {@link Pair}&lt;{@link ISkSadDocument},{@link ValidationResult}&gt; - the pair of open document and success
   *         indicator, {@link Pair#left()} will be <code>null</code> on open failure
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIllegalArgumentRtException missed reference {@link ISkCoreConfigConstants#REFDEF_THREAD_EXECUTOR}
   */
  Pair<ISkTheOpenDoc, ValidationResult> tryOpen( ISkConnection aConn, ITsContext aArgs );

  /**
   * Opens the document only for reading data, not for modification.
   *
   * @param aConn {@link ISkConnection} - created closed instance of the connection
   * @param aArgs {@link ITsContext} - the opening arguments
   * @return {@link Pair}&lt;{@link ISkSadDocument},{@link ValidationResult}&gt; - the pair of open document and success
   *         indicator, {@link Pair#left()} will be <code>null</code> on open failure
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsRuntimeException (and subclasses) on any unexpected error
   * @throws TsIllegalArgumentRtException missed reference {@link ISkCoreConfigConstants#REFDEF_THREAD_EXECUTOR}
   */
  Pair<ISkTheOpenDoc, ValidationResult> openReadOnly( ISkConnection aConn, ITsContext aArgs );

  /**
   * Returns the ID of the document used as a template to create this document.
   * <p>
   * If document was created as a new, empty document, returns {@link IStridable#NONE_ID}.
   * <p>
   *
   * @return String - template document ID or {@link IStridable#NONE_ID}
   */
  String templateDocId();

  /**
   * Returns last modification time of the document content.
   * <p>
   * Last modification time is the moment when last editing session was closed.
   * <p>
   * The returned value is the instant in the UTC time zone.
   *
   * @return {@link LocalDateTime} - content modification time
   */
  LocalDateTime contentModificationTime();

  /**
   * Returns last modification time of the document {@link #params()}.
   * <p>
   * The returned value is the instant in the UTC time zone.
   *
   * @return {@link LocalDateTime} - properties modification time
   */
  LocalDateTime attributesModificationTime();

  /**
   * Returns document creation time.
   * <p>
   * The returned value is the instant in the UTC time zone.
   *
   * @return {@link LocalDateTime} - creation time
   */
  LocalDateTime creationTime();

  /**
   * Sets name {@link #nmName()} and {@link #description()}.
   *
   * @param aName String - the name
   * @param aDescription String - the description
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  void setNameAndDescription( String aName, String aDescription );

  /**
   * Gets SAD document content from the server and writes itto the local file.
   *
   * @param aLocalFile {@link File} - local file to write document content
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIoRtException I/O error or file access error
   */
  void downloadSadContentToTheFile( File aLocalFile );

  /**
   * Reads SAD document content from the local file and stores it in the server.
   *
   * @param aLocalFile {@link File} - local file to read document content
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIoRtException I/O error or file access error
   */
  void uploadSadContentFromTheFile( File aLocalFile );

  // ------------------------------------------------------------------------------------
  // Inline methods for convenience

  @SuppressWarnings( "javadoc" )
  default boolean isBasedOnTemplate() {
    return templateDocId().equals( IStridable.NONE_ID );
  }

}
