package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.report.structure.automaton.StepReport
import spock.lang.Specification
import spock.lang.Unroll

class RunReportSpecification extends Specification {

    @Unroll
    def "should make non-error run report"() {
        given:
        def input = "abc"
        def steps = [GroovyMock(StepReport), GroovyMock(StepReport), GroovyMock(StepReport)] as StepReport[]

        when:
        def runReport = new RunReport(automatonType, input, accepted, steps)

        then:
        runReport.succeed
        runReport.type == automatonType
        runReport.input == input
        runReport.accepted == accepted
        runReport.steps == steps
        runReport.error == null

        where:
        automatonType        | accepted
        AutomatonType.FA     | true
        AutomatonType.MOORE  | false
        AutomatonType.MEALY  | true
        AutomatonType.PDA    | false
    }

    @Unroll
    def "should make error run report"() {
        given:
        def input = "abc"
        def steps = [GroovyMock(StepReport), GroovyMock(StepReport), GroovyMock(StepReport)] as StepReport[]
        def errorReport = GroovyMock(ErrorReport)

        when:
        def runReport = new RunReport(automatonType, input, errorReport, steps)

        then:
        !runReport.succeed
        !runReport.accepted
        runReport.type == automatonType
        runReport.input == input
        runReport.steps == steps
        runReport.error == errorReport

        where:
        automatonType << [
                    AutomatonType.FA,
                    AutomatonType.MOORE,
                    AutomatonType.MEALY,
                    AutomatonType.PDA,
                ]
    }
}
