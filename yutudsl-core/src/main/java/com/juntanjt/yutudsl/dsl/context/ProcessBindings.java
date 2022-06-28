package com.juntanjt.yutudsl.dsl.context;

import com.google.common.collect.Maps;

import javax.script.SimpleBindings;
import java.util.Map;

/**
 *
 * @author Jun Tan
 */
public class ProcessBindings extends SimpleBindings {

 private Map<String,Object> map;

 public ProcessBindings(Map<String, Object> map) {
  super(map);
  this.map = map;
 }

 public ProcessBindings() {
  this(Maps.newHashMap());
 }

 public Map<String, Object> getMap() {
  return map;
 }
}
