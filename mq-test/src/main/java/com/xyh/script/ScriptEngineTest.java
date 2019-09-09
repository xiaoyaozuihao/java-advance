package com.xyh.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 解析字符串表达式
 * @author: xuyh
 * @create: 2019/9/9
 **/
public class ScriptEngineTest {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");
        engine.put("a",7);
        engine.put("b",2);
        engine.put("c",1);
        String expression = "(a>4 && b<3)|| c>6 ";
        Object eval = engine.eval(expression);
        System.out.println(eval);
    }
}
