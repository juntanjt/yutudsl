package com.juntanjt.yutudsl.dsl.node;

import com.google.common.base.Preconditions;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

import java.util.List;

/**
 * else if node
 *
 * @author Jun Tan
 */
public class ElifNode implements Node {

    /**
     *
     */
    private ScriptNode condition;
    /**
     *
     */
    private List<Node> statements;

    public ElifNode(ScriptNode condition, List<Node> statements) {
        Preconditions.checkNotNull(condition);
        Preconditions.checkNotNull(statements);
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    public Object eval(ProcessContext context) {
        Preconditions.checkNotNull(context);
        if ((Boolean) condition.eval(context)) {
            for (Node statement : statements) {
                statement.eval(context);
            }
        }
        return null;
    }

    public Boolean getCondition(ProcessContext context) {
        Preconditions.checkNotNull(context);
        return (Boolean) condition.eval(context);
    }

}
