package com.juntanjt.yutudsl.test.dsl

import com.google.common.collect.Lists
import com.juntanjt.yutudsl.dsl.ProcessEvaluator
import com.juntanjt.yutudsl.dsl.context.ProcessBindings
import com.juntanjt.yutudsl.dsl.context.ProcessContext
import com.juntanjt.yutudsl.dsl.utils.ProcessUtils
import com.meituan.mtest.MTest
import com.meituan.mtest.MTestBaseCase
import org.assertj.core.api.Assertions
import org.springframework.core.io.ClassPathResource
import spock.lang.Unroll

/**
 *
 * @author Jun Tan
 */
@MTest(testClass = ProcessEvaluator.class, method = "execute", overload = 1, location = "process-mtest-data")
class ProcessEvaluatorExecuteSpec extends MTestBaseCase {

    @Unroll
    def "#testCase, execute-0"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluator-execute-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))
        def processContext = new ProcessContext()
        if (context != null) {
            processContext.setBindings(new ProcessBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new ProcessBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = ProcessEvaluator.execute(dsl, processContext, cached)

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
    def "#testCase, execute-1"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluator-execute-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))
        def processContext = new ProcessContext()
        if (context != null) {
            processContext.setBindings(new ProcessBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new ProcessBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = ProcessEvaluator.execute(dsl, processContext)

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
    def "#testCase, execute-2"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluator-execute-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))
        def processContext = new ProcessContext()
        if (context != null) {
            processContext.setBindings(new ProcessBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new ProcessBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = null
        if (context==null || context.isEmpty()) {
            result = ProcessEvaluator.execute(dsl)
        }

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
    def "#testCase, execute-3"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluator-execute-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))
        def processContext = new ProcessContext()
        if (context != null) {
            processContext.setBindings(new ProcessBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new ProcessBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = ProcessEvaluator.execute(dsl, Lists.newArrayList(), processContext, cached)

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
    def "#testCase, execute-4"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluator-execute-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))
        def processContext = new ProcessContext()
        if (context != null) {
            processContext.setBindings(new ProcessBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new ProcessBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = ProcessEvaluator.execute(dsl, Lists.newArrayList(), processContext)

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
    def "#testCase, execute-5"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluator-execute-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))
        def processContext = new ProcessContext()
        if (context != null) {
            processContext.setBindings(new ProcessBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new ProcessBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = null
        if (context==null || context.isEmpty()) {
            result = ProcessEvaluator.execute(dsl, Lists.newArrayList())
        }

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
