package pl.poznan.put.cie.putflap.generator

import spock.lang.Specification

class GrammarGeneratorSpecification extends Specification {
    def grammarGenerator = new GrammarGenerator(5, 2, ["a", "b", "c", "d", "e", "f"] as String[])
    def grammarGeneratorCompanion = grammarGenerator.@Companion

    def "should return array of nonterminals for requested grammar"() {
        when:
        def nonterminals = grammarGenerator.getNonterminals()

        then:
        nonterminals == ['A', 'B', 'C', 'D', 'E'] as Character[]
    }

    def "should return random string of small letters"() {
        when:
        def text = grammarGenerator.getRandomTerminalProduction()

        then:
        text.length() > 0
        text == text.toLowerCase()
    }
}
