package com.juntanjt.yutudsl.dsl.node;

import com.google.common.base.Preconditions;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

import java.util.List;

/**
 * else node
 *
 * @author Jun Tan
 */
public class ElseNode implements Node {

    /**
     *
     */
    private List<Node> statements;

    public ElseNode(List<Node> statements) {
        Preconditions.checkNotNull(statements);
        this.statements = statements;
    }

    @Override
    public Object eval(ProcessContext context) {
        Preconditions.checkNotNull(context);
        for (Node statement : statements) {
            statement.eval(context);
        }
        return null;
    }

}
