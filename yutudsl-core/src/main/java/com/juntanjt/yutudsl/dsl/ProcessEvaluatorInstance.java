package com.juntanjt.yutudsl.dsl;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;
import com.juntanjt.yutudsl.dsl.exception.EngineException;
import com.juntanjt.yutudsl.dsl.exception.ErrorCode;
import com.juntanjt.yutudsl.dsl.parser.YutuDslLexer;
import com.juntanjt.yutudsl.dsl.parser.YutuDslParser;
import com.juntanjt.yutudsl.dsl.utils.LruMap;

import java.util.List;
import java.util.concurrent.*;

/**
 * Yutu DSL engine
 *
 * @author Jun Tan
 */
public class ProcessEvaluatorInstance {

    /**
     * Compiled Expression cache
     */
    private final ConcurrentHashMap<String, FutureTask<Engine>> expressionCache = new ConcurrentHashMap<>();

    private final LruMap<String, FutureTask<Engine>> expressionLRUCache =  new LruMap<>(100);

    private final ExecutorService executorService = new ThreadPoolExecutor(10, 20, 10,TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(100),
                    new ThreadPoolExecutor.DiscardPolicy());

    /**
     * Compile a text expression to Expression object
     *
     * @param expression text expression
     * @param actions
     * @param cached Whether to cache the compiled result,make true to cache it.
     * @return
     */
    public Engine compile(final String expression, final List<BusinessAction> actions, final boolean cached) {
        return compile(expression, expression, actions, cached);
    }

    /**
     *
     * @param cacheKey
     * @param expression
     * @param actions
     * @param cached
     * @return
     */
    public Engine compile(final String cacheKey, final String expression,
                           final List<BusinessAction> actions, final boolean cached) {
        Preconditions.checkNotNull(cacheKey);
        Preconditions.checkNotNull(expression);

        if (cached) {
            FutureTask<Engine> existedTask;
            if (this.expressionLRUCache != null) {
                boolean runTask = false;
                synchronized (this.expressionLRUCache) {
                    existedTask = this.expressionLRUCache.get(cacheKey);
                    if (existedTask == null) {
                        existedTask = newCompileTask(expression, actions);
                        runTask = true;
                        this.expressionLRUCache.put(cacheKey, existedTask);
                    }
                }
                if (runTask) {
                    existedTask.run();
                }
            } else {
                FutureTask<Engine> task = this.expressionCache.get(cacheKey);
                if (task != null) {
                    return getCompiledExpression(expression, task);
                }
                task = newCompileTask(expression, actions);
                existedTask = this.expressionCache.putIfAbsent(cacheKey, task);
                if (existedTask == null) {
                    existedTask = task;
                    existedTask.run();
                }
            }
            return getCompiledExpression(cacheKey, existedTask);

        } else {
            return innerCompile(expression, actions);
        }

    }

    private FutureTask<Engine> newCompileTask(final String expression, final List<BusinessAction> actions) {
        return new FutureTask<>(() -> innerCompile(expression, actions));
    }

    private Engine getCompiledExpression(final String cacheKey,
                                             final FutureTask<Engine> task) {
        try {
            return task.get();
        } catch (Throwable t) {
            invalidateCacheByKey(cacheKey);
            Throwables.throwIfInstanceOf(t, EngineException.class);
            throw new EngineException(ErrorCode.DSL_COMPILE_ERROR, "Compile expression failure", t);
        }
    }

    /**
     *
     * @param expression
     * @param actions
     * @return
     */
    private Engine innerCompile(final String expression, final List<BusinessAction> actions) {
        try {
            YutuDslLexer lexer = new YutuDslLexer(expression);
            YutuDslParser parser = new YutuDslParser(lexer, actions, executorService);
            Engine exp = parser.parse();
            return exp;
        } catch (Throwable t) {
            Throwables.throwIfInstanceOf(t, EngineException.class);
            throw new EngineException(ErrorCode.DSL_COMPILE_ERROR, "Compile expression failure, expression="+expression, t);
        }
    }

    /**
     * Execute a text expression with environment
     *
     * @param expression text expression
     * @param actions
     * @param context
     * @param cached Whether to cache the compiled result,make true to cache it.
     */
    public Object execute(final String expression, final List<BusinessAction> actions, final ProcessContext context,
                          final boolean cached) {
        try {
            Preconditions.checkNotNull(expression);
            Engine compiledExpression = compile(expression, actions, cached);
            if (compiledExpression != null) {
                return compiledExpression.execute(context);
            } else {
                throw new EngineException(ErrorCode.DSL_EXECUTE_ERROR, "Null compiled expression for " + expression);
            }
        } catch (Throwable t) {
            Throwables.throwIfInstanceOf(t, EngineException.class);
            throw new EngineException(ErrorCode.DSL_EXECUTE_ERROR, "Execute expression failure, expression="+expression, t);
        }
    }

    /**
     * Invalidate expression cache by cacheKey
     *
     * @param cacheKey
     */
    public void invalidateCacheByKey(final String cacheKey) {
        if (this.expressionLRUCache != null) {
            synchronized (this.expressionLRUCache) {
                this.expressionLRUCache.remove(cacheKey);
            }
        } else {
            this.expressionCache.remove(cacheKey);
        }
    }

}
