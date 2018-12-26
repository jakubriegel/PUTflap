package pl.poznan.put.cie.putflap.report.structure.grammar

import jflap.grammar.Grammar
import spock.lang.Specification

class GrammarReportSpecification extends Specification {

    def "should create grammar report from Grammar"() {
        given:
        def grammar = Mock(Grammar)
        grammar.productions >> []

        when:
        def grammarReport = new GrammarReport(grammar)

        then:
        grammarReport.type == 'grammar'
        grammarReport.productions == []

    }
}
