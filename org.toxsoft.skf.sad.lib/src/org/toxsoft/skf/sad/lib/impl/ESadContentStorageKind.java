package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.skf.sad.lib.l10n.ISkSadLibSharedResources.*;

import org.toxsoft.core.tslib.bricks.keeper.*;
import org.toxsoft.core.tslib.bricks.keeper.std.*;
import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;

/**
 * The enumeration of XXX.
 *
 * @author AUTHOR_NAME
 */
public enum ESadContentStorageKind
    implements IStridable {

  /**
   * Textual CLOB with 2GB restriction.
   */
  CLOB( "clob", STR_SCSK_CLOB, STR_SCSK_CLOB_D ) { //$NON-NLS-1$

    @Override
    AbstractSadContentStorage getStorage() {
      return SadContentStorageClob.INSTANCE;
    }

  },

  /**
   * CLOB contains ZIPped content in Base64 encoding.
   */
  CLOB_ZB64( "clob_zb64", STR_SCSK_CLOB_ZB64, STR_SCSK_CLOB_ZB64_D ) { //$NON-NLS-1$

    @Override
    AbstractSadContentStorage getStorage() {
      return SadContentStorageClobZipBase64.INSTANCE;
    }

  }

  ;

  /**
   * The registered keeper ID.
   */
  public static final String KEEPER_ID = "ESadContentStorageKind"; //$NON-NLS-1$

  /**
   * The keeper singleton.
   */
  public static final IEntityKeeper<ESadContentStorageKind> KEEPER =
      new StridableEnumKeeper<>( ESadContentStorageKind.class );

  private static IStridablesListEdit<ESadContentStorageKind> list = null;

  private final String id;
  private final String name;
  private final String description;

  ESadContentStorageKind( String aId, String aName, String aDescription ) {
    id = aId;
    name = aName;
    description = aDescription;
  }

  // --------------------------------------------------------------------------
  // IStridable
  //

  @Override
  public String id() {
    return id;
  }

  @Override
  public String nmName() {
    return name;
  }

  @Override
  public String description() {
    return description;
  }

  // ------------------------------------------------------------------------------------
  // package API
  //

  /**
   * Retruns storage kind implementation.
   *
   * @return {@link AbstractSadContentStorage} - the storage implementation
   */
  abstract AbstractSadContentStorage getStorage();

  // ----------------------------------------------------------------------------------
  // Stridable enum common API
  //

  /**
   * Returns all constants in single list.
   *
   * @return {@link IStridablesList}&lt; {@link ESadContentStorageKind} &gt; - list of constants in order of declaraion
   */
  public static IStridablesList<ESadContentStorageKind> asList() {
    if( list == null ) {
      list = new StridablesList<>( values() );
    }
    return list;
  }

  /**
   * Returns the constant by the ID.
   *
   * @param aId String - the ID
   * @return {@link ESadContentStorageKind} - found constant
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException no constant found by specified ID
   */
  public static ESadContentStorageKind getById( String aId ) {
    return asList().getByKey( aId );
  }

  /**
   * Finds the constant by the name.
   *
   * @param aName String - the name
   * @return {@link ESadContentStorageKind} - found constant or <code>null</code>
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public static ESadContentStorageKind findByName( String aName ) {
    TsNullArgumentRtException.checkNull( aName );
    for( ESadContentStorageKind item : values() ) {
      if( item.name.equals( aName ) ) {
        return item;
      }
    }
    return null;
  }

  /**
   * Returns the constant by the name.
   *
   * @param aName String - the name
   * @return {@link ESadContentStorageKind} - found constant
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException no constant found by specified name
   */
  public static ESadContentStorageKind getByName( String aName ) {
    return TsItemNotFoundRtException.checkNull( findByName( aName ) );
  }

}
