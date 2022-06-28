package com.juntanjt.yutudsl.dsl.context;

import javax.script.SimpleScriptContext;
import java.util.Map;

/**
 * @author Jun Tan
 */
public class ProcessContext extends SimpleScriptContext {

    public ProcessContext() {
        super();
        super.setBindings(new ProcessBindings(), ENGINE_SCOPE);
    }

    public ProcessContext(Map<String, Object> initParam) {
        super();
        super.setBindings(new ProcessBindings(initParam), ENGINE_SCOPE);
    }
}
