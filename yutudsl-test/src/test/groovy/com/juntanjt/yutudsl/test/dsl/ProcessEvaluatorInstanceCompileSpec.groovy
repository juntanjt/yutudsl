package com.juntanjt.yutudsl.test.dsl

import com.google.common.collect.Lists
import com.juntanjt.yutudsl.dsl.ProcessEvaluatorInstance
import com.juntanjt.yutudsl.dsl.utils.ProcessUtils
import com.meituan.mtest.MTest
import com.meituan.mtest.MTestBaseCase
import org.springframework.core.io.ClassPathResource
import spock.lang.Unroll

/**
 *
 * @author Jun Tan
 */
@MTest(testClass = ProcessEvaluatorInstance.class, method = "compile", overload = 1, location = "process-mtest-data")
class ProcessEvaluatorInstanceCompileSpec extends MTestBaseCase {

    def instance = new ProcessEvaluatorInstance()

    @Unroll
    def "#testCase"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluatorInstance-compile-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))

        when: ""
        def engine = instance.compile(dsl, Lists.newArrayList(), cached)

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
