package com.juntanjt.yutudsl.dsl.parser;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juntanjt.yutudsl.dsl.BusinessAction;
import com.juntanjt.yutudsl.dsl.Engine;
import com.juntanjt.yutudsl.dsl.ProcessEngine;
import com.juntanjt.yutudsl.dsl.exception.EngineException;
import com.juntanjt.yutudsl.dsl.exception.ErrorCode;
import com.juntanjt.yutudsl.dsl.node.*;
import com.juntanjt.yutudsl.dsl.utils.ProcessConstants;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * dsl parser
 *
 * @author Jun Tan
 */
public class YutuDslParser {

    /**
     *
     */
    private YutuDslLexer lexer;
    /**
     *
     */
    private Map<String, BusinessAction> actions;
    /**
     *
     */
    private ExecutorService executorService;
    /**
     *
     */
    private static ScriptEngineManager sem = new ScriptEngineManager();
    /**
     *
     */
    private static ScriptEngine aviatorScriptEngine = sem.getEngineByName("AviatorScript");

    public YutuDslParser(YutuDslLexer lexer, List<BusinessAction> actions, ExecutorService executorService) {
        Preconditions.checkNotNull(lexer);
        Preconditions.checkNotNull(actions);
        Preconditions.checkNotNull(executorService);
        this.lexer = lexer;
        this.actions = Maps.newHashMap();
        if (actions != null && ! actions.isEmpty()) {
            for (BusinessAction action : actions) {
                this.actions.put(action.getName(), action);
            }
        }
        this.executorService = executorService;
    }

    /**
     * parse
     *
     * @return
     */
    public Engine parse() {
        try {
            List<Object> statements = lexer.scan();
            if (statements == null || statements.isEmpty()) {
                throw new EngineException(ErrorCode.DSL_COMPILE_ERROR, "dsl parser error, statements is null");
            }
            List<Node> dslNodes = parse(statements);
            return new ProcessEngine(dslNodes);
        } catch (Exception e) {
            Throwables.throwIfInstanceOf(e, EngineException.class);
            throw new EngineException(ErrorCode.DSL_COMPILE_ERROR, "dsl parser error", e);
        }
    }

    private List<Node> parse(List<Object> statements) {
        List<Node> dslNodes = Lists.newArrayList();
        for (int i=0; i<statements.size(); i++) {
            Object statement = statements.get(i);
            Node dslNode = parse(statement);
            dslNodes.add(dslNode);

            if (dslNode instanceof IfNode) {
                List<ElifNode> elifNodes = Lists.newArrayList();
                for (int j=i+1; j<statements.size(); j++) {
                    statement = statements.get(j);
                    if (!((statement instanceof Map) && ((Map) statement).containsKey(ProcessConstants.TOKEN_ELIF))) {
                        break;
                    }
                    ElifNode elifNode = (ElifNode) parse(statement);
                    elifNodes.add(elifNode);
                    i++;
                }
                if (! elifNodes.isEmpty()) {
                    ((IfNode) dslNode).setElifNodes(elifNodes);
                }
                if (i+1 < statements.size()) {
                    statement = statements.get(i+1);
                    if (!((statement instanceof Map) && ((Map) statement).containsKey(ProcessConstants.TOKEN_ELSE))) {
                        continue;
                    }
                    ElseNode elseNode = (ElseNode) parse(statement);
                    ((IfNode) dslNode).setElseNode(elseNode);
                    i++;
                }
            }
        }

        return dslNodes;
    }

    private Node parse(Object statement) {
        if (statement instanceof String) {
            if (actions.containsKey(statement)) {
                return parseActionNode(statement);
            } else {
                return parseScriptNode(statement);
            }
        } else if (statement instanceof Map) {
            if (((Map) statement).containsKey(ProcessConstants.TOKEN_IF)) {
                return parseIfNode(statement);
            } else if (((Map) statement).containsKey(ProcessConstants.TOKEN_ELIF)) {
                return parseElifNode(statement);
            } else if (((Map) statement).containsKey(ProcessConstants.TOKEN_ELSE)) {
                return parseElseNode(statement);
            } else if (((Map) statement).containsKey(ProcessConstants.TOKEN_FOR)) {
                return parseForNode(statement);
            } else if (((Map) statement).containsKey(ProcessConstants.TOKEN_WHILE)) {
                return parseWhileNode(statement);
            } else if (((Map) statement).containsKey(ProcessConstants.TOKEN_GO)) {
                return parseGoNode(statement);
            } else {
                throw new EngineException(ErrorCode.DSL_COMPILE_ERROR, "dsl parser error, statements ="+statement);
            }
        } else {
            throw new EngineException(ErrorCode.DSL_COMPILE_ERROR, "dsl parser error, statements ="+statement);
        }
    }

    private Node parseActionNode(Object statement) {
        statement = ((String) statement).trim();
        BusinessAction action = actions.get(statement);
        return new ActionNode(action);
    }

    private ElifNode parseElifNode(Object statement) {
        ScriptNode condition = parseConditionNode(((Map) statement).get(ProcessConstants.TOKEN_ELIF));
        List<Node> statements = Lists.newArrayList();
        for (Object st : (List) ((Map) statement).get(ProcessConstants.TOKEN_THEN)) {
            statements.add(parse(st));
        }
        return new ElifNode(condition, statements);
    }

    private ElseNode parseElseNode(Object statement) {
        List<Node> statements = Lists.newArrayList();
        for (Object st : (List) ((Map) statement).get(ProcessConstants.TOKEN_ELSE)) {
            statements.add(parse(st));
        }
        return new ElseNode(statements);
    }

    private ForNode parseForNode(Object statement) {
        String elementString = (String) ((Map) statement).get(ProcessConstants.TOKEN_FOR);
        String[] elementInfo = elementString.split(ProcessConstants.TOKEN_IN);
        String elementKey = elementInfo[0].trim();
        boolean isRange = false;
        String iterableKey = null;
        ScriptNode rangeScriptNode = null;
        if (elementInfo[1].trim().startsWith(ProcessConstants.TOKEN_RANGE)) {
            isRange = true;
            rangeScriptNode = parseRangeNode(elementInfo[1]);
        } else {
            iterableKey = elementInfo[1].trim();
        }

        List<Node> statements = Lists.newArrayList();
        for (Object st : (List) ((Map) statement).get(ProcessConstants.TOKEN_DO)) {
            statements.add(parse(st));
        }
        if (isRange) {
            return new ForNode(elementKey, rangeScriptNode, statements);
        } else {
            return new ForNode(elementKey, iterableKey, statements);
        }
    }

    private GoNode parseGoNode(Object statement) {
        List<Node> statements = Lists.newArrayList();
        for (Object st : (List) ((Map) statement).get(ProcessConstants.TOKEN_GO)) {
            statements.add(parse(st));
        }
        return new GoNode(statements, executorService);
    }

    private IfNode parseIfNode(Object statement) {
        ScriptNode condition = parseConditionNode(((Map) statement).get(ProcessConstants.TOKEN_IF));
        List<Node> statements = Lists.newArrayList();
        for (Object st : (List) ((Map) statement).get(ProcessConstants.TOKEN_THEN)) {
            statements.add(parse(st));
        }
        return new IfNode(condition, statements);
    }


    private WhileNode parseWhileNode(Object statement) {
        ScriptNode condition = parseConditionNode(((Map) statement).get(ProcessConstants.TOKEN_WHILE));
        List<Node> statements = Lists.newArrayList();
        for (Object st : (List) ((Map) statement).get(ProcessConstants.TOKEN_DO)) {
            statements.add(parse(st));
        }
        return new WhileNode(condition, statements);
    }

    private ScriptNode parseScriptNode(Object statement) {
        try {
            Compilable compilable = (Compilable) aviatorScriptEngine;
            String expression = ((String) statement).trim();
            String lineEnd = ";";
            if (! expression.endsWith(lineEnd)) {
                expression += lineEnd;
            }
            CompiledScript script = compilable.compile(expression);
            return new ScriptNode(script);
        } catch (Exception e) {
            Throwables.throwIfInstanceOf(e, EngineException.class);
            throw new EngineException(ErrorCode.SYSTEM_ERROR, e);
        }
    }


    private ScriptNode parseConditionNode(Object statement) {
        try {
            Compilable compilable = (Compilable) aviatorScriptEngine;
            String expression = ((String) statement).trim();
            CompiledScript script = compilable.compile(expression);
            return new ScriptNode(script);
        } catch (Exception e) {
            Throwables.throwIfInstanceOf(e, EngineException.class);
            throw new EngineException(ErrorCode.SYSTEM_ERROR, e);
        }
    }


    private ScriptNode parseRangeNode(Object statement) {
        try {
            Compilable compilable = (Compilable) aviatorScriptEngine;
            String expression = ((String) statement).trim();
            CompiledScript script = compilable.compile(expression);
            return new ScriptNode(script);
        } catch (Exception e) {
            Throwables.throwIfInstanceOf(e, EngineException.class);
            throw new EngineException(ErrorCode.SYSTEM_ERROR, e);
        }
    }

}
