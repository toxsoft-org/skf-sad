package org.toxsoft.skf.sad.lib.dpu_ts4;

import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.core.txtproj.lib.storage.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;

/**
 * Container with possible all data stored in USkat.
 * <p>
 * Container may store all data either in memory or in external storage (such as set of file, a database). The API is
 * designed to make possible to work with both cases. However for <i>very big</i> data out of memory exception may
 * happen. It is recommended to create partial containers for such a cases and process containers one by one.
 *
 * @author hazard157
 */
public interface ISkDataContainer {

  /**
   * Returns all classes stored in container.
   * <p>
   * Includes the root class as the first element of the returned list. The hierarchy of the listed classes are valid,
   * meaning that for any particular class all ancestor classes are also listed.
   * <p>
   * It is mandatory requirement that all other entities in the container belongs to the one of the listed class.
   *
   * @return {@link IStridablesList}&lt;{@link IDtoClassInfo}&gt; - list of all classes in container
   */
  IStridablesList<IDtoClassInfo> classInfos();

  /**
   * Returns STRIDs list of all objects of the specified class.
   * <p>
   * Note: There may be happen out of memory error if there are too many objects of the asked class.
   *
   * @param aClassId String - the class ID
   * @return {@link IStringList} - STRIDs list
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException the class is not listed in {@link #classInfos()}
   */
  IStringList listObjectStrids( String aClassId );

  /**
   * Returns the reduced (only attributes values) data of of all objects of the specified class.
   * <p>
   * Note: There may be happen out of memory error if there are too many objects of the asked class.
   *
   * @param aClassId String - the class ID
   * @return {@link IStridablesList}&lt;{@link IDtoObject}&gt; - objects with attributes values
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException the class is not listed in {@link #classInfos()}
   */
  IStridablesList<IDtoObject> listObjects( String aClassId );

  /**
   * Returns full data (attributes, CLOBs and links) of the specified objects.
   *
   * @param aSkids {@link ISkidList} - SKIDs of the requested objects
   * @return {@link IMap}&lt;{@link Skid},{@link IDtoFullObject}&gt; - the objects map
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException argument contains SKID of the object not stored in the container
   */
  IMap<Skid, IDtoFullObject> getFullObjects( ISkidList aSkids );

  /**
   * Returns the data history if any is stored in container.
   *
   * @return {@link ISkDataHistory} - the history or <code>null</code> if container does not stores the history
   */
  ISkDataHistory history();

  // ------------------------------------------------------------------------------------
  // Other data if any is stored by backend extensions
  //

  /**
   * Returns optional extra data storage.
   * <p>
   * USkat core stores only the data as described by the methods above in this interface. However it is possible for
   * some backend addons to store data by the specific way bwsides USkat core. This method declares the way to access
   * such a data.
   *
   * @return {@link IKeepablesStorageRo} - extra data or <code>null</code> if no extra data is stored in container
   */
  IKeepablesStorageRo extraData();

}
