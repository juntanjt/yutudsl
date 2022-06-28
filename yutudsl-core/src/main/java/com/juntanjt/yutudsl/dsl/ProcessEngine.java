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
import com.google.common.base.Throwables;
import com.juntanjt.yutudsl.dsl.context.ProcessContext;
import com.juntanjt.yutudsl.dsl.exception.EngineException;
import com.juntanjt.yutudsl.dsl.exception.ErrorCode;
import com.juntanjt.yutudsl.dsl.node.Node;
import com.juntanjt.yutudsl.dsl.utils.ProcessConstants;

import java.util.List;

/**
 * Compiled expression,all generated class inherit this class
 *
 * @author Jun Tan
 *
 */
public class ProcessEngine implements Engine {

  /**
   *
   */
  private List<Node> nodes;

  public ProcessEngine(List<Node> nodes) {
    Preconditions.checkNotNull(nodes);
    this.nodes = nodes;
  }

  @Override
  public Object execute() {
    return execute(new ProcessContext());
  }

  @Override
  public Object execute(ProcessContext context) {
    Preconditions.checkNotNull(context);
    try {
      for (Node node : nodes) {
        node.eval(context);
      }
      return context.getAttribute(ProcessConstants.CONTEXT_OUTPUT);
    } catch (Exception e) {
      Throwables.throwIfInstanceOf(e, EngineException.class);
      throw new EngineException(ErrorCode.DSL_EXECUTE_ERROR, e);
    }
  }
}
