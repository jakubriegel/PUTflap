package pl.poznan.put.cie.putflap.report


import spock.lang.Specification
import spock.lang.Unroll

class WordsReportSpecification extends Specification {

    @Unroll
    def "should make words report"() {
        when:
        def runReport = new WordsReport(requestedNumber, generatedNumber, words as String[])

        then:
        runReport.requestedNumber == requestedNumber
        runReport.generatedNumber == generatedNumber
        runReport.words == words

        where:
        requestedNumber | generatedNumber | words
        1               | 1               | ["abc"]
        100             | 3               | ["abc", "def", "ghi"]
        1               | 0               | []
    }
}
