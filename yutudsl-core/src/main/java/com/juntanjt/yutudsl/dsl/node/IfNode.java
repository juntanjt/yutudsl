package com.juntanjt.yutudsl.dsl.node;

import com.google.common.base.Preconditions;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

import java.util.List;

/**
 * if node
 *
 * @author Jun Tan
 */
public class IfNode implements Node {

    /**
     *
     */
    private ScriptNode condition;
    /**
     *
     */
    private List<Node> statements;
    /**
     *
     */
    private List<ElifNode> elifNodes;
    /**
     *
     */
    private ElseNode elseNode;

    public IfNode(ScriptNode condition, List<Node> statements) {
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
        } else {
            if (elifNodes != null &&  ! elifNodes.isEmpty()) {
                for (ElifNode elifNode : elifNodes) {
                    if (elifNode.getCondition(context)) {
                        elifNode.eval(context);
                        return null;
                    }
                }
            }
            if (elseNode != null) {
                elseNode.eval(context);
            }
        }
        return null;
    }

    public void setElifNodes(List<ElifNode> elifNodes) {
        Preconditions.checkNotNull(elifNodes);
        this.elifNodes = elifNodes;
    }

    public void setElseNode(ElseNode elseNode) {
        Preconditions.checkNotNull(elseNode);
        this.elseNode = elseNode;
    }
}
