package com.juntanjt.yutudsl.dsl.script;

import com.juntanjt.yutudsl.dsl.Engine;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * A compiled Yutu DSL script.
 *
 * @author Jun Tan
 */
public class CompiledYutuDslScript extends CompiledScript {

  /**
   *
   */
  private final YutuDslScriptEngine engine;
  /**
   *
   */
  private final Engine expression;

  CompiledYutuDslScript(final YutuDslScriptEngine engine, final Engine expression) {
    this.engine = engine;
    this.expression = expression;
  }

  @Override
  public Object eval(final ScriptContext context) throws ScriptException {
    try {
      ProcessContext processContext = new ProcessContext();
      processContext.setBindings(context.getBindings(ScriptContext.ENGINE_SCOPE), ScriptContext.ENGINE_SCOPE);
      return this.expression.execute(processContext);
    } catch (Exception e) {
      throw new ScriptException(e);
    }
  }

  @Override
  public ScriptEngine getEngine() {
    return this.engine;
  }

}
