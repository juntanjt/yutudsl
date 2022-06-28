package com.juntanjt.yutudsl.dsl.script;

import com.google.common.collect.Lists;
import com.juntanjt.yutudsl.dsl.BusinessAction;
import com.juntanjt.yutudsl.dsl.ProcessEvaluator;
import com.juntanjt.yutudsl.dsl.ProcessEvaluatorInstance;
import com.juntanjt.yutudsl.dsl.context.ProcessBindings;
import com.juntanjt.yutudsl.dsl.utils.ProcessConstants;
import com.juntanjt.yutudsl.dsl.utils.ProcessUtils;

import javax.script.*;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Yutu DSL script engine
 *
 * @author Jun Tan
 */
public class YutuDslScriptEngine extends AbstractScriptEngine implements Compilable {

  private static final int KEY_THRESHOLD = 4096;
  private boolean cached = true;
  private final YutuDslScriptEngineFactory factory;
  private final ProcessEvaluatorInstance engine;

  public YutuDslScriptEngine() {
    super();
    this.factory = YutuDslScriptEngineFactory.newInstance();
    this.engine = ProcessEvaluator.newInstance();
  }

  public YutuDslScriptEngine(final Bindings n) {
    super(n);
    this.factory = YutuDslScriptEngineFactory.newInstance();
    this.engine = ProcessEvaluator.newInstance();
  }

  public YutuDslScriptEngine(final YutuDslScriptEngineFactory factory) {
    this.factory = factory;
    this.engine = ProcessEvaluator.newInstance();
  }

  @Override
  public CompiledScript compile(final String script) {
    List<BusinessAction> actions = (List<BusinessAction>) this.get(ProcessConstants.CONTEXT_ACTIONS);
    if (actions==null || actions.isEmpty()) {
      actions = Lists.newArrayList();
    }
    return new CompiledYutuDslScript(this,
        this.engine.compile(getCachingKey(script), script, actions, this.cached));
  }

  @Override
  public CompiledScript compile(final Reader script) throws ScriptException {
    try {
      return this.compile(ProcessUtils.readFully(script));
    } catch (IOException e) {
      throw new ScriptException(e);
    }
  }

  private String getCachingKey(final String script) {
    if (script.length() < KEY_THRESHOLD) {
      return script;
    } else {
      return ProcessUtils.md5sum(script);
    }
  }

  @Override
  public Bindings createBindings() {
    final ProcessBindings bindings = new ProcessBindings();
    return bindings;
  }

  @Override
  public Object eval(final String script, final ScriptContext context) throws ScriptException {
    return this.compile(script).eval(context);
  }

  @Override
  public Object eval(final Reader reader, final ScriptContext context) throws ScriptException {
    try {
      return eval(ProcessUtils.readFully(reader), context);
    } catch (IOException e) {
      throw new ScriptException(e);
    }
  }

  @Override
  public ScriptEngineFactory getFactory() {
    return this.factory;
  }

  public boolean isCached() {
    return this.cached;
  }

  /**
   * Setting whether to cache the compiled script, default is true(caching).
   *
   * @param cached true means enable caching.
   */
  public void setCached(final boolean cached) {
    this.cached = cached;
  }
}
