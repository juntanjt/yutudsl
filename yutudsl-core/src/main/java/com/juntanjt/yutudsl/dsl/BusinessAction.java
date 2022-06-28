package com.juntanjt.yutudsl.dsl;

import com.juntanjt.yutudsl.dsl.context.ProcessContext;

/**
 * @author Jun Tan
 */
public interface BusinessAction {

    /**
     * @param context
     * @return
     */
    Object eval(ProcessContext context);

    /**
     * @return
     */
    String getName();

    /**
     * 获取type的类型
     *
     * @return
     */
    Integer getType();


    String getExpression();
}
