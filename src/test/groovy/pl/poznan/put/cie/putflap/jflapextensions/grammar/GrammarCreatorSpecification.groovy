package pl.poznan.put.cie.putflap.jflapextensions.grammar

import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import jflap.grammar.Production
import jflap.grammar.reg.RegularGrammar
import org.junit.runner.RunWith
import pl.poznan.put.cie.putflap.report.structure.grammar.GrammarReport
import pl.poznan.put.cie.putflap.report.structure.grammar.ProductionReport
import spock.lang.Specification

@RunWith(SpotlinTestRunner)
@OpenedClasses([GrammarCreator, GrammarReport, ProductionReport])
class GrammarCreatorSpecification extends Specification {
    def grammarCreator = GrammarCreator.INSTANCE

    def "should create RegularGrammar from GrammarReport"() {
        given:
        def report = GroovyMock(GrammarReport)

        def production1 = GroovyMock(ProductionReport)
        def p1lhs = "A"
        def p1rhs = "abC"
        production1.left >> p1lhs
        production1.right >> p1rhs
        def production2 = GroovyMock(ProductionReport)
        def p2lhs = "C"
        def p2rhs = "dE"
        production2.left >> p2lhs
        production2.right >> p2rhs
        def production3 = GroovyMock(ProductionReport)
        def p3lhs = "E"
        def p3rhs = ""
        production3.left >> p3lhs
        production3.right >> p3rhs
        report.productions >> [production1, production2, production3]

        when:
        def grammar = grammarCreator.fromReport((GrammarReport) report)

        then:
        grammar instanceof RegularGrammar
        grammar.productions == [
                new Production(p1lhs, p1rhs),
                new Production(p2lhs, p2rhs),
                new Production(p3lhs, p3rhs)
        ]
    }
}
