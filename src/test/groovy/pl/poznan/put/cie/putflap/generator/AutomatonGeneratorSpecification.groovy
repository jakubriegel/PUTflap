package pl.poznan.put.cie.putflap.generator

import jflap.automata.Automaton
import jflap.automata.State
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MooreMachine
import spock.lang.Specification
import spock.lang.Unroll

class AutomatonGeneratorSpecification extends Specification {

    def n = 5
    def alphabet = ["a", "b", "c", "d", "e", "f"] as String[]
    def finals = 2
    def automatonGenerator = new AutomatonGenerator(n ,alphabet, finals, alphabet)

    @Unroll
    def "should return random FSA with given parameters"() {
        given:
        def generator = new AutomatonGenerator(n, alphabet as String[], finals)

        when:
        def automaton = generator.randomFSA()

        then:
        automaton instanceof FiniteStateAutomaton
        automaton.states.size() == n
        automaton.finalStates.size() == finals
        automaton.transitions.size() > 0

        where:
        n | alphabet                              | finals
        5 | ["a", "b", "c", "d", "e", "f"]        | 1
        5 | ["A", "b", "C", "D", "e", "f"]        | 3
        5 | ["abc", "AbC", "cccc", "D", "e", "f"] | 1
    }

    @Unroll
    def "should return random Moore with given parameters"() {
        given:
        def generator = new AutomatonGenerator(n, alphabet as String[], finals, alphabet as String[])

        when:
        def automaton = generator.randomMoore()

        then:
        automaton instanceof MooreMachine
        automaton.states.size() == n
        automaton.finalStates.size() == finals
        automaton.transitions.size() > 0

        where:
        n | alphabet                              | finals
        5 | ["a", "b", "c", "d", "e", "f"]        | 1
        5 | ["A", "b", "C", "D", "e", "f"]        | 3
        5 | ["abc", "AbC", "cccc", "D", "e", "f"] | 1
    }

    @Unroll
    def "should return random Mealy with given parameters"() {
        given:
        def generator = new AutomatonGenerator(n, alphabet as String[], finals, alphabet as String[])

        when:
        def automaton = generator.randomMealy()

        then:
        automaton instanceof MealyMachine
        automaton.states.size() == n
        automaton.finalStates.size() == finals
        automaton.transitions.size() > 0

        where:
        n | alphabet                              | finals
        5 | ["a", "b", "c", "d", "e", "f"]        | 1
        5 | ["A", "b", "C", "D", "e", "f"]        | 3
        5 | ["abc", "AbC", "cccc", "D", "e", "f"] | 1
    }

    def "should choose letter that is not present in out transitions from given state"() {
        given:
        automatonGenerator.matrix[1][0] = 2
        automatonGenerator.matrix[1][1] = 3
        automatonGenerator.matrix[1][2] = 4

        when:
        def letter = automatonGenerator.chooseLetter(1)

        then:
        [3, 4, 5].contains(letter)
    }

    @Unroll
    def "should specify if state has out transition with given letter"() {
        given:
        automatonGenerator.matrix[1][0] = 2
        automatonGenerator.matrix[1][1] = 3
        automatonGenerator.matrix[1][2] = 4

        when:
        def hasOut = automatonGenerator.hasOut(state, letter)

        then:
        hasOut == result

        where:
        letter | state || result
        1      | 1     || true
        3      | 1     || false
        1      | 0     || false
    }

    def "should get random state not equal to specified"() {
        when:
        def state = automatonGenerator.getRandomState(5, 3)

        then:
        state >= 0
        state < 5
        state != 3
    }

    @Unroll
    def "should get number of out transitions from given state"() {
        given:
        automatonGenerator.matrix[1][0] = 2
        automatonGenerator.matrix[1][1] = 3
        automatonGenerator.matrix[1][2] = 4

        when:
        def out = automatonGenerator.getOutNumber(state)

        then:
        out == result

        where:
        state || result
        1     || 3
        2     || 0
    }

    def "should get random letter from alphabet"() {
        when:
        def letter = automatonGenerator.getRandomOutputLetter()

        then:
        alphabet.contains(letter)
    }

    def "should add states to automaton and return list of them"() {
        given:
        Automaton automaton = Spy()

        when:
        def addedStates = automatonGenerator.addStates(automaton)

        then:
        1 * automaton.setInitialState(_ as State)
        2 * automaton.addFinalState(_ as State)
        addedStates.size() == 5
    }

    def "should add transitions to automaton"() {
        given:
        automatonGenerator.matrix[1][0] = 2
        automatonGenerator.matrix[1][1] = 3
        automatonGenerator.matrix[1][2] = 4

        Automaton automaton = Mock()
        def addedStates = [Mock(State), Mock(State), Mock(State), Mock(State), Mock(State)]
        def transitionClass = { a, b, c -> return null }

        when:
        automatonGenerator.addTransitions(automaton, addedStates, transitionClass)

        then:
        3 * automaton.addTransition(_)
    }

}
