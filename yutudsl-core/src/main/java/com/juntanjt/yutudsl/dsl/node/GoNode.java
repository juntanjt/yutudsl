package com.juntanjt.yutudsl.dsl.node;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.*;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * go node
 *
 * @author Jun Tan
 */
public class GoNode implements Node {

    /**
     *
     */
    private ListeningExecutorService listeningExecutor;
    /**
     *
     */
    private List<Node> statements;

    public GoNode(List<Node> statements, ExecutorService executorService) {
        Preconditions.checkNotNull(statements);
        Preconditions.checkNotNull(executorService);
        this.statements = statements;
        this.listeningExecutor = MoreExecutors.listeningDecorator(executorService);
    }

    @Override
    public Object eval(ProcessContext context) {
        Preconditions.checkNotNull(context);
        for (Node statement : statements) {
            ListenableFuture<Object> listenableFuture = listeningExecutor.submit(() -> statement.eval(context));
            Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
                @Override
                public void onSuccess(Object result) {
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
        }
        return null;
    }
}
