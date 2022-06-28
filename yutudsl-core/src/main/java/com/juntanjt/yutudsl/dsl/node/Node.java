package com.juntanjt.yutudsl.dsl.node;

import com.juntanjt.yutudsl.dsl.context.ProcessContext;

/**
 * process node
 *
 * @author Jun Tan
 */
public interface Node {

    /**
     * execute
     *
     * @param context
     * @return
     */
    Object eval(ProcessContext context);

}
