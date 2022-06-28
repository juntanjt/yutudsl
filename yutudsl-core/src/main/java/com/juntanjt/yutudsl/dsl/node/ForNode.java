package com.juntanjt.yutudsl.dsl.node;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

import java.util.List;

/**
 * for node
 *
 * @author Jun Tan
 */
public class ForNode implements Node {

    /**
     *
     */
    private String elementKey;
    /**
     *
     */
    private String iterableKey;
    /**
     *
     */
    private ScriptNode rangeScriptNode;
    /**
     *
     */
    private List<Node> statements;

    public ForNode(String elementKey, String iterableKey, List<Node> statements) {
        Preconditions.checkNotNull(elementKey);
        Preconditions.checkNotNull(iterableKey);
        Preconditions.checkNotNull(statements);
        this.elementKey = elementKey;
        this.iterableKey = iterableKey;
        this.statements = statements;
    }

    public ForNode(String elementKey, ScriptNode rangeScriptNode, List<Node> statements) {
        Preconditions.checkNotNull(elementKey);
        Preconditions.checkNotNull(rangeScriptNode);
        Preconditions.checkNotNull(statements);
        this.elementKey = elementKey;
        this.rangeScriptNode = rangeScriptNode;
        this.statements = statements;
    }

    @Override
    public Object eval(ProcessContext context) {
        Preconditions.checkNotNull(context);
        Iterable iterable;
        if (! Strings.isNullOrEmpty(iterableKey)) {
            iterable = (Iterable) context.getAttribute(iterableKey);
        } else {
            iterable = (Iterable) rangeScriptNode.eval(context);
        }
        for (Object element : iterable) {
            context.setAttribute(elementKey, element, ProcessContext.ENGINE_SCOPE);
            for (Node statement : statements) {
                statement.eval(context);
            }
        }
        context.removeAttribute(elementKey, ProcessContext.ENGINE_SCOPE);
        return null;
    }


}
