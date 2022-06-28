package com.juntanjt.yutudsl.test.dsl

import com.google.common.collect.Lists
import com.juntanjt.yutudsl.dsl.ProcessEvaluator
import com.juntanjt.yutudsl.dsl.utils.ProcessUtils
import com.meituan.mtest.MTest
import com.meituan.mtest.MTestBaseCase
import org.springframework.core.io.ClassPathResource
import spock.lang.Unroll

/**
 *
 * @author Jun Tan
 */
@MTest(testClass = ProcessEvaluator.class, method = "compile", overload = 1, location = "process-mtest-data")
class ProcessEvaluatorCompileSpec extends MTestBaseCase {

    @Unroll
    def "#testCase, compile-0"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluator-compile-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))

        when: ""
        def engine = ProcessEvaluator.compile(dsl, Lists.newArrayList(), cached)

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
        def dslPath = "process-mtest-data/ProcessEvaluator-compile-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))

        when: ""
        def engine = ProcessEvaluator.compile(dsl)

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
    def "#testCase, compile-2"() {
        given: ""
        def dslPath = "process-mtest-data/ProcessEvaluator-compile-1/dsl/"+dslFileName
        def dsl = ProcessUtils.readFully(new InputStreamReader(new ClassPathResource(dslPath).getInputStream()))

        when: ""
        def engine = ProcessEvaluator.compile(dsl, Lists.newArrayList())

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
