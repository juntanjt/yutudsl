package com.juntanjt.yutudsl.dsl.script;

import com.juntanjt.yutudsl.dsl.utils.ProcessConstants;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.*;

/**
 * Yutu DSL script engine factory.
 *
 * @author Jun Tan
 */
public class YutuDslScriptEngineFactory implements ScriptEngineFactory {

  private static final List<String> EXTENSIONS =
      Collections.unmodifiableList(Arrays.asList("yt", "yutu"));
  private static final List<String> MIME_TYPES =
      Collections.unmodifiableList(Arrays.asList("text/yutu", "text/yutudsl"));
  private static final List<String> NAMES = Collections
      .unmodifiableList(Arrays.asList("Yutu", "yutu", "yutudsl", "YutuDsl"));

  private static final Map<String, String> PARAM_MAP = new HashMap<>();
  static {
    PARAM_MAP.put(ScriptEngine.ENGINE, "Yutu");
    PARAM_MAP.put(ScriptEngine.ENGINE_VERSION, ProcessConstants.VERSION);
    PARAM_MAP.put(ScriptEngine.LANGUAGE, "A high performance scripting language hosted on the JVM");
    PARAM_MAP.put(ScriptEngine.LANGUAGE_VERSION, ProcessConstants.VERSION);
  }

  public static final YutuDslScriptEngineFactory newInstance() {
    return new YutuDslScriptEngineFactory();
  }

  @Override
  public String getEngineName() {
    return PARAM_MAP.get(ScriptEngine.ENGINE);
  }

  @Override
  public String getEngineVersion() {
    return PARAM_MAP.get(ScriptEngine.ENGINE_VERSION);
  }

  @Override
  public List<String> getExtensions() {
    return EXTENSIONS;
  }

  @Override
  public String getLanguageName() {
    return PARAM_MAP.get(ScriptEngine.LANGUAGE);
  }

  @Override
  public String getLanguageVersion() {
    return PARAM_MAP.get(ScriptEngine.LANGUAGE_VERSION);
  }

  @Override
  public String getMethodCallSyntax(final String obj, final String m, final String... args) {
    StringBuilder sb = new StringBuilder(m);
    sb.append("(").append(obj);
    if (args != null) {
      for (String s : args) {
        sb.append(",").append(s);
      }
    }
    sb.append(")");
    return sb.toString();

  }

  @Override
  public List<String> getMimeTypes() {
    return MIME_TYPES;
  }

  @Override
  public List<String> getNames() {
    return NAMES;
  }

  @Override
  public String getOutputStatement(final String toDisplay) {
    return "print(+" + toDisplay + ")";
  }

  @Override
  public Object getParameter(final String key) {
    return PARAM_MAP.get(key);
  }

  @Override
  public String getProgram(final String... statements) {
    StringBuilder sb = new StringBuilder();
    for (String stmt : statements) {
      sb.append(stmt).append(";");
    }
    return sb.toString();
  }

  @Override
  public ScriptEngine getScriptEngine() {
    return new YutuDslScriptEngine(this);
  }

}
