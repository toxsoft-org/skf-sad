package org.toxsoft.skf.sad.lib.dpu_ts4;

import org.toxsoft.core.tslib.bricks.strio.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;

/**
 * Temporary declaration of the methods.
 *
 * @author hazard157
 */
public class SkDataContainerUtils {

  /**
   * Writes container to the output stream.
   *
   * @param aSw {@link IStrioWriter} - output stream writer
   * @param aContainer {@link ISkDataContainer} - the container to write
   * @param aCallback {@link ILongOpProgressCallback} - the callback
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public static void writeContainer( IStrioWriter aSw, ISkDataContainer aContainer,
      ILongOpProgressCallback aCallback ) {
    TsNullArgumentRtException.checkNulls( aSw, aContainer, aCallback );

    // TODO реализовать SkDataContainerUtils.writeContainer()
    throw new TsUnderDevelopmentRtException( "SkDataContainerUtils.writeContainer()" );

  }

}
