package pl.poznan.put.cie.putflap.report.structure.grammar

import jflap.grammar.Production
import spock.lang.Specification
import spock.lang.Unroll

class ProductionReportSpecification extends Specification {

    @Unroll
    def "should create production report from Production('#production.LHS', '#production.RHS')"() {
        when:
        def productionReport = new ProductionReport(production)

        then:
        productionReport.left == production.LHS
        productionReport.right == production.RHS

        where:
        production << [
                new Production("A", "abC"),
                new Production("abC", "A"),
                new Production("A", "")
        ]
    }
}
