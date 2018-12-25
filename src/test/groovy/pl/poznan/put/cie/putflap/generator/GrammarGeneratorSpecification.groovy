package pl.poznan.put.cie.putflap.generator

import jflap.grammar.reg.RegularGrammar
import spock.lang.Specification
import spock.lang.Unroll


class GrammarGeneratorSpecification extends Specification {
    def grammarGenerator = new GrammarGenerator(5, 2, ["a", "b", "c", "d", "e", "f"] as String[])

    def setup() {
    }

    @Unroll
    def "should return random regular grammar with specified parameters" () {
        given:
        def grammarGenerator = new GrammarGenerator(n, finals, alphabet as String[])

        when:
        def grammar = grammarGenerator.randomRegular()

        then:
        getNonterminals(grammar).size() == n
        getLambda(grammar) == finals

        where:
        n  | finals  | alphabet
        5  | 2       | ["a", "b", "c", "d", "e", "f"]
        5  | 2       | ["one", "two", "three", "four", "five", "six"]
        22 | 10      | ["a", "b", "c", "d", "e", "f", "g", "h"]
    }

    @Unroll
    def "should not accept capital letters in alphabet"() {
        def grammarGenerator = new GrammarGenerator(5, 2, alphabet as String[])

        when:
        grammarGenerator.randomRegular()

        then:
        thrown(IllegalArgumentException)

        where:
        alphabet << [["a", "B", "c", "D", "e", "f"], ["one", "TWo", "THREE", "four", "Five", "six"]]
    }

    private static def getNonterminals(RegularGrammar grammar) {
        def nonterminals = [] as Set
        grammar.productions.each { nonterminals.add(it.LHS) }
        return nonterminals
    }

    private static def getLambda(RegularGrammar grammar) {
        def lambda = 0
        grammar.productions.each { if (it.RHS == "") lambda++ }
        return lambda
    }

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
