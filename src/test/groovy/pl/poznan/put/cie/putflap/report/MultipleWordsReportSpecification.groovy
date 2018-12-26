package pl.poznan.put.cie.putflap.report

import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import org.junit.runner.RunWith
import spock.lang.Specification
import spock.lang.Unroll


@RunWith(SpotlinTestRunner)
@OpenedClasses(WordsReport)
class MultipleWordsReportSpecification extends Specification {
    @Unroll
    def "should make multiple words report"() {
        when:
        def runReport = new MultipleWordsReport(requestedNumber, averageGeneratedNumber, reports as WordsReport[])

        then:
        runReport.requestedNumber == requestedNumber
        runReport.averageGeneratedNumber == averageGeneratedNumber
        runReport.reports == reports

        where:
        requestedNumber | averageGeneratedNumber | reports
        1               | 1                      | [GroovyMock(WordsReport), GroovyMock(WordsReport), GroovyMock(WordsReport)]
        100             | 3                      | [GroovyMock(WordsReport), GroovyMock(WordsReport)]
        1               | 0                      | [GroovyMock(WordsReport)]
    }

    def "should make multiple words report from list of reports"() {
        given:
        def requestedNumber = 10
        def averageGenerated = 3

        def report1 = GroovyMock(WordsReport)//, constructorArgs: [requestedNumber, 4, []])
        report1.requestedNumber >> requestedNumber
        report1.generatedNumber >> 4
        def report2 = GroovyMock(WordsReport)
        report2.requestedNumber >> requestedNumber
        report2.generatedNumber >> 3
        def report3 = GroovyMock(WordsReport)
        report3.requestedNumber >> requestedNumber
        report3.generatedNumber >> 2
        def reports = [report1, report2, report3]

        when:
        def runReport = new MultipleWordsReport(reports as WordsReport[])

        then:
        runReport.requestedNumber == requestedNumber
        runReport.averageGeneratedNumber == averageGenerated
        runReport.reports == reports
    }
}
