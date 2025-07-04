package org.toxsoft.skf.sad.lib.impl;

import org.toxsoft.core.tslib.av.*;
import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.av.utils.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.uskat.core.impl.*;

/**
 * Class to be returned as {@link IParameterizedBatchEdit#paramsBatchEditor()} of the SAD entities.
 *
 * @author hazard157
 */
class ObjectParamsEditor<T extends SkObject & IParameterized>
    implements IOpsBatchEdit {

  private T skObj = null;

  public ObjectParamsEditor( T aObj ) {
    TsNullArgumentRtException.checkNull( aObj );
    skObj = aObj;
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  protected void saveParams( IOptionSet aOps ) {
    // DtoObject dto = DtoObject

    // TODO реализовать ObjectParamsEditor.saveParams()
    throw new TsUnderDevelopmentRtException( "ObjectParamsEditor.saveParams()" );
  }

  // ------------------------------------------------------------------------------------
  // IOpsBatchEdit
  //

  @Override
  public void addAll( IOptionSet aOps ) {
    TsNullArgumentRtException.checkNull( aOps );
    IOptionSetEdit p = new OptionSet( skObj.params() );
    p.addAll( aOps );
    saveParams( p );
  }

  @Override
  public void addAll( IMap<String, ? extends IAtomicValue> aOps ) {
    TsNullArgumentRtException.checkNull( aOps );
    IOptionSetEdit p = new OptionSet( skObj.params() );
    p.addAll( aOps );
    saveParams( p );
  }

  @Override
  public boolean extendSet( IOptionSet aOps ) {
    TsNullArgumentRtException.checkNull( aOps );
    IOptionSetEdit p = new OptionSet( skObj.params() );
    boolean b = p.extendSet( aOps );
    saveParams( p );
    return b;
  }

  @Override
  public boolean extendSet( IMap<String, ? extends IAtomicValue> aOps ) {
    TsNullArgumentRtException.checkNull( aOps );
    IOptionSetEdit p = new OptionSet( skObj.params() );
    boolean b = p.extendSet( aOps );
    saveParams( p );
    return b;
  }

  @Override
  public boolean refreshSet( IOptionSet aOps ) {
    TsNullArgumentRtException.checkNull( aOps );
    IOptionSetEdit p = new OptionSet( skObj.params() );
    boolean b = p.refreshSet( aOps );
    saveParams( p );
    return b;
  }

  @Override
  public boolean refreshSet( IMap<String, ? extends IAtomicValue> aOps ) {
    TsNullArgumentRtException.checkNull( aOps );
    IOptionSetEdit p = new OptionSet( skObj.params() );
    boolean b = p.refreshSet( aOps );
    saveParams( p );
    return b;
  }

  @Override
  public void setAll( IOptionSet aOps ) {
    TsNullArgumentRtException.checkNull( aOps );
    IOptionSetEdit p = new OptionSet( skObj.params() );
    p.setAll( aOps );
    saveParams( p );
  }

  @Override
  public void setAll( IMap<String, ? extends IAtomicValue> aOps ) {
    TsNullArgumentRtException.checkNull( aOps );
    IOptionSetEdit p = new OptionSet( skObj.params() );
    p.setAll( aOps );
    saveParams( p );
  }

}
