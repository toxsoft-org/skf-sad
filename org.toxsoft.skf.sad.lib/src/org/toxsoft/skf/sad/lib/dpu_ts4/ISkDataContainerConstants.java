package org.toxsoft.skf.sad.lib.dpu_ts4;

import org.toxsoft.core.tslib.av.metainfo.*;
import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.time.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.utils.*;

/**
 * Constants related with USkat data container management.
 *
 * @author hazard157
 */
public interface ISkDataContainerConstants {

  /**
   * Determines if data history is to be extracted.
   * <p>
   * If this value
   * <p>
   * This is option for <code>aParams</code> argument of the method
   * {@link ISkDataContainerExtractor#extractData(IStringList, IOptionSet, IQueryInterval, ILongOpProgressCallback)}.
   * <p>
   * If option has value <code>false</code> then {@link ISkDataContainer#history()} will be <code>null</code>.
   * <p>
   * Default value: <code>false</code>
   */
  IDataDef OPDEF_IS_HISTORY_INCLUDED = null;

  /**
   * Determines if extra data is to be extracted.
   * <p>
   * If this value
   * <p>
   * This is option for <code>aParams</code> argument of the method
   * {@link ISkDataContainerExtractor#extractData(IStringList, IOptionSet, IQueryInterval, ILongOpProgressCallback)}.
   * <p>
   * If option has value <code>false</code> then {@link ISkDataContainer#extraData()} will be <code>null</code>.
   * <p>
   * Default value: <code>false</code>
   */
  IDataDef OPDEF_IS_EXTRA_DATA_INCLUDED = null;

  /**
   * Determines if data of the auto-added parent classes will be extracted too.
   * <p>
   * This is option for <code>aParams</code> argument of the method
   * {@link ISkDataContainerExtractor#extractData(IStringList, IOptionSet, IQueryInterval, ILongOpProgressCallback)}.
   * <p>
   * Default value: <code>true</code>
   */
  IDataDef OPDEF_IS_AUTO_ADDED_CLASSES_DATA_INCLUDED = null;

}
