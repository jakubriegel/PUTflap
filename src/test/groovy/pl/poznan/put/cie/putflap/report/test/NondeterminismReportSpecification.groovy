package pl.poznan.put.cie.putflap.report.test

import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import jflap.automata.State
import org.junit.runner.RunWith
import spock.lang.Specification
import spock.lang.Unroll

@RunWith(SpotlinTestRunner)
@OpenedClasses(LambdaTransitionsReport)
class NondeterminismReportSpecification extends Specification {

    @Unroll
    def "should make nondeterminism report"() {
        given:
        def lambdaTransitionsReport = GroovyMock(LambdaTransitionsReport)

        when:
        def nondeterminismReport = new NondeterminismReport(
                deterministic,
                nonDeterministicStates as State[],
                lambdaTransitionsReport
        )

        then:
        nondeterminismReport.deterministic == deterministic
        nondeterminismReport.nonDeterministicStates == nonDeterministicStatesReport
        nondeterminismReport.lambdaTransitions == lambdaTransitionsReport

        where:
        deterministic | nonDeterministicStates      || nonDeterministicStatesReport
        true          | []                          || null
        false         | [Mock(State), Mock(State)]  || [0, 0]
        false         | [Mock(State)]               || [0]
    }
}
