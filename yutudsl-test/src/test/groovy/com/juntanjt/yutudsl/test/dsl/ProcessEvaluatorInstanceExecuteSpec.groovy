package com.juntanjt.yutudsl.test.dsl

import com.google.common.collect.Lists
import com.juntanjt.yutudsl.dsl.ProcessEvaluatorInstance
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
@MTest(testClass = ProcessEvaluatorInstance.class, method = "execute", overload = 1, location = "process-mtest-data")
class ProcessEvaluatorInstanceExecuteSpec extends MTestBaseCase {

    def instance = new ProcessEvaluatorInstance()

    @Unroll
    def "#testCase"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluatorInstance-execute-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))
        def processContext = new ProcessContext()
        if (context != null) {
            processContext.setBindings(new ProcessBindings(context), ProcessContext.ENGINE_SCOPE)
        } else {
            processContext.setBindings(new ProcessBindings(), ProcessContext.ENGINE_SCOPE)
        }

        when: ""
        def result = instance.execute(dsl, Lists.newArrayList(), processContext, cached)

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
