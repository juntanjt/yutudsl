package com.juntanjt.yutudsl.dsl;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;
import com.juntanjt.yutudsl.dsl.exception.EngineException;
import com.juntanjt.yutudsl.dsl.exception.ErrorCode;

/**
 * action engine
 *
 * @author Jun Tan
 */
public class ActionEngine implements Engine {

  /**
   *
   */
  private BusinessAction action;

  public ActionEngine(BusinessAction action) {
    Preconditions.checkNotNull(action);
    this.action = action;
  }

  @Override
  public Object execute() {
    return execute(new ProcessContext());
  }

  @Override
  public Object execute(ProcessContext context) {
    Preconditions.checkNotNull(context);
    try {
      return action.eval(context);
    } catch (Exception e) {
      Throwables.throwIfInstanceOf(e, EngineException.class);
      throw new EngineException(ErrorCode.DSL_EXECUTE_ERROR, e);
    }
  }

}
