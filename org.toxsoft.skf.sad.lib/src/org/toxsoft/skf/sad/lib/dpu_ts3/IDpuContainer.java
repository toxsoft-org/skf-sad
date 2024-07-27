package org.toxsoft.skf.sad.lib.dpu_ts3;

import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.uskat.core.api.linkserv.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;

/**
 * Контейнер данных uskat общего назначения.
 * <p>
 * Сделана как пара: интерфес "только-для-чтения" и редактируемый класс {@link DpuContainer}.
 * <p>
 * API контейнера разработан исходя из следующих соображений:
 * <ul>
 * <li>для каждого вида хранимых сущностей есть ровно один способ получить коллекцию сущностей (например, для классов
 * только {@link #classInfos()}, для связей {@link #links()});</li>
 * <li>каждая коллекция является ассоциативной, и содержит пары "ключ" - "значение". Это сделано с целья гарантировать
 * отсутствие дублирования информации);</li>
 * <li>TODO API для хранения DPU дополнительных служб, тоже с ключами, должны быть с Keeper-ами</li>
 * </ul>
 * <p>
 * В остальном, контейнер не накладывает никаких ограничений на содержмое, это просто набор коллекции DPU-структур.
 *
 * @author goga
 */
public interface IDpuContainer
    extends IDpuIdContainer {

  /**
   * Определяет, пустой ли контейнер.
   * <p>
   * Это метод для удобства, просто проверяет все коллекции в контейнере, есть ли хоть что-нибудь в одном из них.
   *
   * @return boolean - признак, что контейнер пустой
   */
  @Override
  boolean isEmpty();

  /**
   * Возвращает описания типов.
   *
   * @return {@link IStridablesList}&lt;{@link IDpuSdTypeInfo}&gt; - описания типов
   */
  // Absent in TS4 !
  // IStridablesList<IDpuSdTypeInfo> typeInfos();

  /**
   * Возвращает описания классов.
   *
   * @return {@link IStridablesList}&lt;{@link IDtoClassInfo}&gt; - описания классов
   */
  IStridablesList<IDtoClassInfo> classInfos();

  /**
   * Возвращает объекты.
   *
   * @return {@link IMap}&lt;{@link Skid},{@link IDtoFullObject}&gt; - карта "ИД" - "объект"
   */
  // TS3 used IDtoObject, TS4 includes BLOBs with IDtoFullObject
  IMap<Skid, IDtoFullObject> objs();

  /**
   * Возвращает связи.
   *
   * @return {@link IList}&lt;{@link IDtoLinkFwd}&gt; - связи
   */
  IMap<Gwid, IDtoLinkFwd> links();

  /**
   * Возвращает CLOB-ы, большие текстовые объекты.
   *
   * @return {@link IMap}&lt;{@link IdPair},{@link IDpuClob}&gt; - карта "ИД CLOB-а" - "содержимое CLOB-а"
   */
  // Absent in TS4 !
  // IMap<IdPair, IDtoClob> clobs();

  // TODO API для sysext служб

}
