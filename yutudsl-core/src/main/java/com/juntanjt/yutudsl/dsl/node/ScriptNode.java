package com.juntanjt.yutudsl.dsl.node;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;
import com.juntanjt.yutudsl.dsl.exception.EngineException;
import com.juntanjt.yutudsl.dsl.exception.ErrorCode;

import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

/**
 * script node
 *
 * @author Jun Tan
 */
public class ScriptNode implements Node {

    /**
     *
     */
    private CompiledScript script;

    public ScriptNode(CompiledScript script) {
        Preconditions.checkNotNull(script);
        this.script = script;
    }

    @Override
    public Object eval(ProcessContext context) {
        Preconditions.checkNotNull(context);
        ScriptContext scriptContext = new SimpleScriptContext();
        scriptContext.setBindings(context.getBindings(ProcessContext.ENGINE_SCOPE), ProcessContext.ENGINE_SCOPE);
        try {
            return script.eval(scriptContext);
        } catch (Exception e) {
            Throwables.throwIfInstanceOf(e, EngineException.class);
            throw new EngineException(ErrorCode.SYSTEM_ERROR, e);
        }
    }

}
