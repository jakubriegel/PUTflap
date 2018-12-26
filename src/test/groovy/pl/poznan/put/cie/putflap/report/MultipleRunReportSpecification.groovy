package pl.poznan.put.cie.putflap.report

import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import org.junit.runner.RunWith
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport
import spock.lang.Specification
import spock.lang.Unroll

@RunWith(SpotlinTestRunner)
@OpenedClasses(RunReport)
class MultipleRunReportSpecification extends Specification {

    @Unroll
    def "should make multiple run report from list of runs"() {
        given:
        def structureReport = GroovyMock(AutomatonReport)

        def report1 = GroovyMock(RunReport)
        report1.succeed >> report1Succeed
        report1.accepted >> report1Accepted
        def report2 = GroovyMock(RunReport)
        report2.succeed >> true
        report2.accepted >> true
        def report3 = GroovyMock(RunReport)
        report3.succeed >> true
        report3.accepted >> true
        def results = [report1, report2, report3]

        when:
        def multipleRunReport = new MultipleRunReport(structureReport, results as RunReport[])

        then:
        multipleRunReport.structure == structureReport
        multipleRunReport.allSucceed == report1Succeed
        multipleRunReport.allAccepted == report1Accepted
        multipleRunReport.results == results

        where:
        report1Succeed << [true, true, true, false]
        report1Accepted << [true, false, false, false]
    }
}
