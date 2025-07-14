package org.toxsoft.skf.sad.lib.impl;

import static org.toxsoft.skf.sad.lib.ISkSadServiceHardConstants.*;

import java.time.*;

import org.toxsoft.core.tslib.av.*;
import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.av.utils.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.uskat.core.impl.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * Class to be returned as {@link IParameterizedBatchEdit#paramsBatchEditor()} of the SAD entities.
 *
 * @author hazard157
 */
abstract class ObjectParamsEditor<T extends SkObject & IParameterized>
    implements IOpsBatchEdit {

  private T skObj = null;

  private final SkExtServiceSad sadService;

  public ObjectParamsEditor( T aObj, SkExtServiceSad aSadService ) {
    TsNullArgumentRtException.checkNulls( aObj, aSadService );
    skObj = aObj;
    sadService = aSadService;
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  protected void saveParams( IOptionSet aOps ) {
    sadService.pauseCoreValidationAndEvents();
    try {
      DtoObject dto = DtoObject.createFromSk( skObj, skObj.coreApi() );
      dto.attrs().setValobj( ATRID_PARAMS, aOps );
      if( dto.attrs().hasKey( ATRID_ATTRS_MODIFICATION_TIME ) ) {
        dto.attrs().setValobj( ATRID_ATTRS_MODIFICATION_TIME, LocalDateTime.now( ZoneId.of( "UTC" ) ) ); //$NON-NLS-1$
      }
      skObj.coreApi().objService().defineObject( dto );
      skObj.attrs().setValobj( ATRID_PARAMS, aOps );
    }
    finally {
      sadService.resumeCoreValidationAndEvents();
    }
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

  // ------------------------------------------------------------------------------------
  // To override
  //

  protected abstract void generateSiblingMessage();

}
