package org.toxsoft.skf.sad.lib.dpu_ts4;

import org.toxsoft.core.tslib.av.temporal.*;
import org.toxsoft.core.tslib.bricks.time.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.uskat.core.api.cmdserv.*;
import org.toxsoft.uskat.core.api.evserv.*;

public interface ISkDataHistory {

  ITimeInterval interval();

  IGwidList listHistoricRtdataGwids();

  IGwidList listHistoricEventGwids();

  IGwidList listHistoricCommandGwids();

  IMap<Gwid, ITimedList<ITemporalAtomicValue>> queryRtdata( IQueryInterval aInterval, IGwidList aGeids );

  IMap<Gwid, ITimedList<SkEvent>> queryEvents( IQueryInterval aInterval, IGwidList aGeids );

  IMap<Gwid, ITimedList<IDtoCompletedCommand>> queryCommands( ITimeInterval aInterval, Gwid aGwid );

}