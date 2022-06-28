package com.juntanjt.yutudsl.test.dsl

import com.juntanjt.yutudsl.dsl.ProcessEvaluator
import com.juntanjt.yutudsl.dsl.context.ProcessContext
import com.juntanjt.yutudsl.dsl.utils.ProcessUtils
import com.meituan.mtest.MTest
import com.meituan.mtest.MTestBaseCase
import org.assertj.core.api.Assertions
import org.springframework.core.io.ClassPathResource
import spock.lang.Unroll

import javax.script.ScriptEngineManager
import javax.script.SimpleBindings
import javax.script.SimpleScriptContext

/**
 *
 * @author Jun Tan
 */
@MTest(testClass = ProcessEvaluator.class, method = "execute", overload = 1, location = "process-mtest-data")
class YutuDslScriptEngineEvalSpec extends MTestBaseCase {

    @Unroll
    def "#testCase, eval-0"() {
        given: ""
        final def sem = new ScriptEngineManager()
        def scriptEngine = sem.getEngineByName("YutuDsl")

        def dslPath = "process-mtest-data/YutuDslScriptEngine-eval-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))
        def processContext = new SimpleScriptContext()
        if (context != null) {
            processContext.setBindings(new SimpleBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new SimpleBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = scriptEngine.eval(dsl, processContext)

        then: ""
        if (expected != null) {
            Assertions.assertThat(result).isNotNull()
        }

        where: ""
        testCase << testCase()
        [dslFileName, context, cached] << request()
        expected << expected()

    }

    @Unroll
    def "#testCase, eval-1"() {
        given: ""
        final def sem = new ScriptEngineManager()
        def scriptEngine = sem.getEngineByName("YutuDsl")

        def dslPath = "process-mtest-data/YutuDslScriptEngine-eval-1/dsl/"+dslFileName
        def reader = new InputStreamReader(new ClassPathResource(dslPath).getInputStream())
        def processContext = new SimpleScriptContext()
        if (context != null) {
            processContext.setBindings(new SimpleBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new SimpleBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = scriptEngine.eval(reader, processContext)

        then: ""
        if (expected != null) {
            Assertions.assertThat(result).isNotNull()
        }

        where: ""
        testCase << testCase()
        [dslFileName, context, cached] << request()
        expected << expected()

    }

}
