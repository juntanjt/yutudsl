/**
 * Copyright (C) 2010 dennis zhuang (killme2008@gmail.com)
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program;
 * if not, write to the Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 **/
package com.juntanjt.yutudsl.dsl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;

import java.util.List;

/**
 * Yutu DSL Expression evaluator
 *
 * @author Jun Tan
 *
 */
public final class ProcessEvaluator {

  /**
   * Create a aviator script engine instance.
   *
   * @return the script engine
   */
  public static ProcessEvaluatorInstance newInstance() {
    return new ProcessEvaluatorInstance();
  }

  private static class StaticHolder {
    private static ProcessEvaluatorInstance INSTANCE = new ProcessEvaluatorInstance();
  }

  /**
   * Get the default evaluator instance
   *
   * @since 4.0.0
   * @return
   */
  public static ProcessEvaluatorInstance getInstance() {
    return StaticHolder.INSTANCE;
  }

  /**
   * Execute a text expression with environment
   *
   * @param expression text expression
   * @param context
   * @param cached Whether to cache the compiled result,make true to cache it.
   */
  public static Object execute(final String expression, final ProcessContext context,
      final boolean cached) {
    Preconditions.checkNotNull(expression);
    Preconditions.checkNotNull(context);
    return getInstance().execute(expression, Lists.newArrayList(), context, cached);
  }


  /**
   * Execute a text expression without caching
   *
   * @param expression
   * @param context
   * @return
   */
  public static Object execute(final String expression, final ProcessContext context) {
    Preconditions.checkNotNull(expression);
    Preconditions.checkNotNull(context);
    return getInstance().execute(expression, Lists.newArrayList(), context, false);
  }

  /**
   * Execute a text expression without caching and env map.
   *
   * @param expression
   * @return
   */
  public static Object execute(final String expression) {
    Preconditions.checkNotNull(expression);
    return getInstance().execute(expression, Lists.newArrayList(), new ProcessContext(), false);
  }

  /**
   * Execute a text expression with environment
   *
   * @param expression text expression
   * @param actions
   * @param context
   * @param cached Whether to cache the compiled result,make true to cache it.
   */
  public static Object execute(final String expression, final List<BusinessAction> actions, final ProcessContext context,
                               final boolean cached) {
    Preconditions.checkNotNull(expression);
    Preconditions.checkNotNull(actions);
    Preconditions.checkNotNull(context);
    return getInstance().execute(expression, actions, context, cached);
  }


  /**
   * Execute a text expression without caching
   *
   * @param expression
   * @param actions
   * @param context
   * @return
   */
  public static Object execute(final String expression, final List<BusinessAction> actions, final ProcessContext context) {
    Preconditions.checkNotNull(expression);
    Preconditions.checkNotNull(actions);
    Preconditions.checkNotNull(context);
    return getInstance().execute(expression, actions, context, false);
  }

  /**
   * Execute a text expression without caching and env map.
   *
   * @param expression
   * @param actions
   * @return
   */
  public static Object execute(final String expression, final List<BusinessAction> actions) {
    Preconditions.checkNotNull(expression);
    Preconditions.checkNotNull(actions);
    return getInstance().execute(expression, actions, new ProcessContext(), false);
  }

  /**
   * Compile a text expression to Expression Object without caching
   *
   * @param expression
   * @param actions
   * @param cached
   * @return
   */
  public static Engine compile(final String expression, final List<BusinessAction> actions, final boolean cached) {
    Preconditions.checkNotNull(expression);
    Preconditions.checkNotNull(actions);
    return getInstance().compile(expression, actions, cached);
  }

  /**
   * Compile a text expression to Expression Object without caching
   *
   * @param expression
   * @return
   */
  public static Engine compile(final String expression) {
    Preconditions.checkNotNull(expression);
    return getInstance().compile(expression, Lists.newArrayList(), false);
  }

  /**
   * Compile a text expression to Expression Object without caching
   *
   * @param expression
   * @param actions
   * @return
   */
  public static Engine compile(final String expression, final List<BusinessAction> actions) {
    Preconditions.checkNotNull(expression);
    Preconditions.checkNotNull(actions);
    return getInstance().compile(expression, actions, false);
  }
}
