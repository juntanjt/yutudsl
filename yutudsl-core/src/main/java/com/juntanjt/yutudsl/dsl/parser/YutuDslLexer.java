package com.juntanjt.yutudsl.dsl.parser;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.juntanjt.yutudsl.dsl.exception.EngineException;
import com.juntanjt.yutudsl.dsl.exception.ErrorCode;
import org.yaml.snakeyaml.Yaml;

import java.io.StringReader;
import java.util.List;

/**
 * dsl lexer
 *
 * @author Jun Tan
 */
public class YutuDslLexer {

    /**
     *
     */
    private String expression;

    public YutuDslLexer(String expression) {
        Preconditions.checkNotNull(expression);
        this.expression = expression;
    }

    /**
     * scan
     *
     * @return
     */
    public List<Object> scan() {
        try {
            Yaml yaml = new Yaml();
            Object statements = yaml.load(new StringReader(expression));
            if (statements==null || !(statements instanceof List) || (((List<?>) statements).isEmpty())) {
                throw new EngineException(ErrorCode.DSL_COMPILE_ERROR, "dsl lexer error, expression="+expression);
            }
            return (List<Object>) statements;
        } catch (Exception e) {
            Throwables.throwIfInstanceOf(e, EngineException.class);
            throw new EngineException(ErrorCode.DSL_COMPILE_ERROR, "dsl lexer error, expression="+expression, e);
        }
    }

}
