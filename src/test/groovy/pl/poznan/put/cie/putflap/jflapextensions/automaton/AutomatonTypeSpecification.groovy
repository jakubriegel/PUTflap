package pl.poznan.put.cie.putflap.jflapextensions.automaton


import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MooreMachine
import jflap.automata.pda.PushdownAutomaton
import spock.lang.Specification
import spock.lang.Unroll

class AutomatonTypeSpecification extends Specification {
    @Unroll
    def "should get automaton type" () {
        when:
        def type = AutomatonType.@Companion.get(automaton)

        then:
        type == result

        where:
        automaton                  || result
        new FiniteStateAutomaton() || AutomatonType.FA
        new MooreMachine()         || AutomatonType.MOORE
        new MealyMachine()         || AutomatonType.MEALY
        new PushdownAutomaton()    || AutomatonType.PDA
    }
}
