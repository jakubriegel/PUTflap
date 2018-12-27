package pl.poznan.put.cie.putflap.jflapextensions.automaton

import pl.poznan.put.cie.putflap.testutils.AutomatonReader
import spock.lang.Specification
import spock.lang.Unroll

class AutomatonTesterSpecification extends Specification {
    def automatonTester = AutomatonTester.INSTANCE

    @Unroll
    def "should get nondeterministic states of automaton" () {
        when:
        def states = automatonTester.getNondeterministicStates(automaton)

        then:
        states == result

        where:
        automaton                             || result
        AutomatonReader.get("fsa.jff")        || []
        AutomatonReader.get("fsa_nondet.jff") || [automaton.states[0], automaton.states[1], automaton.states[4] ]
    }

    @Unroll
    def "should get lambda transitions of automaton" () {
        when:
        def transitions = automatonTester.getLambdaTransitions(automaton)

        then:
        transitions.size() == result.size()
        (transitions as Set) == result.toSet()

        where:
        automaton                             || result
        AutomatonReader.get("fsa.jff")        || []
        AutomatonReader.get("fsa_nondet.jff") || [
                                            automaton.transitions.find({i -> i.fromState.ID == 1 && i.toState.ID == 1}),
                                            automaton.transitions.find({i -> i.fromState.ID == 0 && i.toState.ID == 1})
                                        ]
    }

    @Unroll
    def "should check equivalence of two FSA" () {
        when:
        def equivalent = automatonTester.areEquivalent(fsa1, fsa2)

        then:
        equivalent == result

        where:
        fsa1                                  | fsa2                           || result
        AutomatonReader.get("fsa.jff")        | AutomatonReader.get("fsa.jff") || true
        AutomatonReader.get("fsa_nondet.jff") | AutomatonReader.get("fsa.jff") || false
    }

    @Unroll
    def "should retrieve in alphabet of given automaton"() {
        when:
        def alphabet = automatonTester.retrieveInAlphabet(automaton)

        then:
        alphabet.size() == result.size()
        (alphabet as Set) == result.toSet()

        where:
        automaton                             || result
        AutomatonReader.get("fsa_simple.jff") || ["a", "c", "b"]
        AutomatonReader.get("mealy.jff")      || ["i", "e", "f", "c", "d", "l", "j"]
    }

    def "should retrieve out alphabet of given automaton"() {
        given:
        def automaton = AutomatonReader.get("mealy.jff")
        def result = ["d", "e", "l", "g", "h", "j", "i", "k"]

        when:
        def alphabet = automatonTester.retrieveOutAlphabet(automaton)

        then:
        alphabet.size() == result.size()
        (alphabet as Set) == result.toSet()
    }

    def "should retrieve in alphabet of given grammar"() {
        given:
        def grammar = AutomatonReader.getGrammar("gram_reg.jff")
        def result = ["a", "b", "c"]

        when:
        def alphabet = automatonTester.retrieveGrammarAlphabet(grammar)

        then:
        alphabet.size() == result.size()
        (alphabet as Set) == result.toSet()
    }
}
