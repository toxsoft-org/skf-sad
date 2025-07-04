package org.toxsoft.skf.sad.skadmin;

import org.toxsoft.skf.sad.lib.*;
import org.toxsoft.skf.sad.skadmin.sad.*;
import org.toxsoft.uskat.skadmin.core.plugins.*;

/**
 * skadmin plugin: Stand Alone Documents management commands via {@link ISkSadService}.
 *
 * @author hazard157
 */
public class AdminPluginSad
    extends AbstractPluginCmdLibrary {

  /**
   * Constructor.
   */
  public AdminPluginSad() {
    // nop
  }

  // ------------------------------------------------------------------------------------
  // AbstractPluginCmdLibrary
  //

  @Override
  public String getName() {
    return getClass().getName();
  }

  @Override
  protected void doInit() {
    addCmd( new AdminCmdListFolders() );
  }

  @Override
  protected void doClose() {
    // nop
  }
}
