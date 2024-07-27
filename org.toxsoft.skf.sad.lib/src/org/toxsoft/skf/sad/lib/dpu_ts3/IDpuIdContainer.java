package org.toxsoft.skf.sad.lib.dpu_ts3;

import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.gw.skid.*;

/**
 * Контейне идентификаторов сущностей uskat общего назначения.
 * <p>
 * Содержит те же сущности, что и {@link IDpuContainer}, но не их значения, а только идентификаторы. Может
 * использоваться для различных целей, например:
 * <ul>
 * <li>в методах-извещениях (listener-ах) для перчисления изменившихся сущностей;</li>
 * <li>для составляния перечня сущностей, которые следует удалить;</li>
 * <li>м т.п.</li>
 * </ul>
 * <p>
 * Сделана как пара: интерфес "только-для-чтения" и редактируемый класс {@link DpuContainer}.
 *
 * @author goga
 */
public interface IDpuIdContainer {

  /**
   * Определяет, пустой ли контейнер.
   * <p>
   * Это метод для удобства, просто проверяет все коллекции в контейнере, есть ли хоть что-нибудь в одном из них.
   *
   * @return boolean - признак, что контейнер пустой
   */
  boolean isEmpty();

  /**
   * Возвращает идентификаторы типов.
   *
   * @return {@link IStringList} - идентификаторы типов
   */
  // Absent in TS4
  // IStringList typeIds();

  /**
   * Возвращает идентификаторы классов.
   *
   * @return {@link IStringList} - идентификаторы классов
   */
  IStringList classIds();

  /**
   * Возвращает идентификаторы объектов.
   *
   * @return {@link ISkidList} - идентификаторы объектов
   */
  IList<Skid> objIds();

  /**
   * Возвращает идентификаторы связей.
   *
   * @return {@link IGwidList} - идентификаторы связей
   */
  IGwidList linkIds();

  /**
   * Возвращает идентификаторы CLOB-ов.
   *
   * @return {@link IList}&lt;{@link IdPair}&gt; - идентификаторы CLOB-ов
   */
  // Absent in TS4
  // IList<IdPair> clobIds();

  // TODO API для sysext служб

}
