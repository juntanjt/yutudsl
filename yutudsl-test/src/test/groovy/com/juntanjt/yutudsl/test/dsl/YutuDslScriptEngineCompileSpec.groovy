package com.juntanjt.yutudsl.test.dsl

import com.juntanjt.yutudsl.dsl.ProcessEvaluator
import com.juntanjt.yutudsl.dsl.utils.ProcessUtils
import com.meituan.mtest.MTest
import com.meituan.mtest.MTestBaseCase
import org.springframework.core.io.ClassPathResource
import spock.lang.Unroll

import javax.script.Compilable
import javax.script.ScriptEngineManager

/**
 *
 * @author Jun Tan
 */
@MTest(testClass = ProcessEvaluator.class, method = "compile", overload = 1, location = "process-mtest-data")
class YutuDslScriptEngineCompileSpec extends MTestBaseCase {

    @Unroll
    def "#testCase, compile-0"() {
        given: ""
        final def sem = new ScriptEngineManager()
        def scriptEngine = sem.getEngineByName("YutuDsl")
        def compilable = (Compilable) scriptEngine

        def dslPath = "process-mtest-data/YutuDslScriptEngine-compile-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))

        when: ""
        def engine = compilable.compile(dsl)

        then: ""
        with(engine) {
            engine != null
        }

        where: ""
        testCase << testCase()
        [dslFileName, cached] << request()
        expected << expected()

    }

    @Unroll
    def "#testCase, compile-1"() {
        given: ""
        final def sem = new ScriptEngineManager()
        def scriptEngine = sem.getEngineByName("YutuDsl")
        def compilable = (Compilable) scriptEngine

        def dslPath = "process-mtest-data/YutuDslScriptEngine-compile-1/dsl/"+dslFileName
        def reader = new InputStreamReader(new ClassPathResource(dslPath).getInputStream())

        when: ""
        def engine = compilable.compile(reader)

        then: ""
        with(engine) {
            engine != null
        }

        where: ""
        testCase << testCase()
        [dslFileName, cached] << request()
        expected << expected()

    }

}
