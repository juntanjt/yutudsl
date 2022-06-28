package com.juntanjt.yutudsl.dsl.node;

import com.google.common.base.Preconditions;
import com.juntanjt.yutudsl.dsl.BusinessAction;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

/**
 * action node
 *
 * @author Jun Tan
 */
public class ActionNode implements Node {

    /**
     *
     */
    private BusinessAction action;

    public ActionNode(BusinessAction action) {
        Preconditions.checkNotNull(action);
        this.action = action;
    }

    @Override
    public Object eval(ProcessContext context) {
        Preconditions.checkNotNull(context);
        return action.eval(context);
    }

}
