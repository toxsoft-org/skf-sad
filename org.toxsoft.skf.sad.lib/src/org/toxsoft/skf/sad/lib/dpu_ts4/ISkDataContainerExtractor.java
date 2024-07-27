package org.toxsoft.skf.sad.lib.dpu_ts4;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.time.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.uskat.core.utils.*;

/**
 * Extracts data from the USkat connection to the {@link ISkDataContainer}.
 *
 * @author hazard157
 */
public interface ISkDataContainerExtractor {

  /**
   * Returns the connection used as a data source.
   *
   * @return {@link ISkConnected} - connection to the data source
   */
  ISkConnected skConn();

  /**
   * Extracts data in the single transaction.
   * <p>
   * <ul>
   * <li>the absent class IDs from <code>aClassIds</code> are ignored;</li>
   * <li>all parent class IDs are automatically added to the <code>aClassIds</code>;</li>
   * <li>options from {@link ISkDataContainerConstants} are honored if specified in <code>aParams</code>;</li>
   * <li><code>aCallback</code> implementation may cancel extraction any time;</li>
   * <li><code>aInterval</code> has no sense if option {@link ISkDataContainerConstants#OPDEF_IS_HISTORY_INCLUDED} is
   * <code>false</code> or is not specified in <code>aParams</code>;</li>
   * </ul>
   * <p>
   * If for any reason extraction fails the method throws an exception.
   *
   * @param aClassIds {@link IStringList} - the IDs of the classes to be extracted
   * @param aParams {@link IOptionSet} - the extraction parameters as listed in {@link ISkDataContainerConstants}
   * @param aInterval {@link ITimeInterval} - history data time interval to be extracted
   * @param aCallback {@link ILongOpProgressCallback} - the callback
   * @return {@link ISkDataContainer} - created container
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  ISkDataContainer extractData( IStringList aClassIds, IOptionSet aParams, IQueryInterval aInterval,
      ILongOpProgressCallback aCallback );

}
