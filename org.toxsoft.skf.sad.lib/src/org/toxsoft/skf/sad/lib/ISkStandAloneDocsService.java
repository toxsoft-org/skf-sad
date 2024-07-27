package org.toxsoft.skf.sad.lib;

import org.toxsoft.core.tslib.bricks.strid.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.*;

/**
 * SAD (Stand-Alone Documents) service.
 *
 * @author hazard157
 */
public interface ISkStandAloneDocsService
    extends ISkService {

  /**
   * Service identifier.
   */
  String SERVICE_ID = ISkHardConstants.SK_SYSEXT_SERVICE_ID_PREFIX + ".StandAloneDocs"; //$NON-NLS-1$

}
