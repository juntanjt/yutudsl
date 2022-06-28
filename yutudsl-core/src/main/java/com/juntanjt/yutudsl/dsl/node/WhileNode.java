package com.juntanjt.yutudsl.dsl.node;

import com.google.common.base.Preconditions;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

import java.util.List;

/**
 * while node
 *
 * @author Jun Tan
 */
public class WhileNode implements Node {

    /**
     *
     */
    private ScriptNode condition;
    /**
     *
     */
    private List<Node> statements;

    public WhileNode(ScriptNode condition, List<Node> statements) {
        Preconditions.checkNotNull(condition);
        Preconditions.checkNotNull(statements);
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    public Object eval(ProcessContext context) {
        Preconditions.checkNotNull(context);
        while ((Boolean) condition.eval(context)) {
            for (Node statement : statements) {
                statement.eval(context);
            }
        }
        return null;
    }
}
