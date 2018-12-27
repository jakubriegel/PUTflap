package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.Automaton
import jflap.automata.State
import spock.lang.Specification
import spock.lang.Unroll

import java.awt.*

class StateTypeSpecification extends Specification {
    @Unroll
    def "should return type of state" () {
        given:
        def automaton = Mock(Automaton)
        def state = new State(1, new Point(), automaton)
        automaton.isInitialState(state) >> isInitial
        automaton.isFinalState(state) >> isFinal

        when:
        def type = StateType.@Companion.get(state, automaton)

        then:
        type == result

        where:
        isInitial | isFinal || result
        true      | false   || StateType.INITIAL
        false     | true    || StateType.FINAL
        false     | false   || StateType.STATE
    }
}
