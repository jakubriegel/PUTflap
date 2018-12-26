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
        def multipleWordsReport = new MultipleWordsReport(requestedNumber, averageGeneratedNumber, reports as WordsReport[])

        then:
        multipleWordsReport.requestedNumber == requestedNumber
        multipleWordsReport.averageGeneratedNumber == averageGeneratedNumber
        multipleWordsReport.reports == reports

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

        def report1 = GroovyMock(WordsReport)
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
        def multipleWordsReport = new MultipleWordsReport(reports as WordsReport[])

        then:
        multipleWordsReport.requestedNumber == requestedNumber
        multipleWordsReport.averageGeneratedNumber == averageGenerated
        multipleWordsReport.reports == reports
    }
}
