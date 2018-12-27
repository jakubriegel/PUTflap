package pl.poznan.put.cie.putflap.generator

import jflap.automata.Automaton
import jflap.automata.fsa.FSAStepByStateSimulator
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MealyStepByStateSimulator
import jflap.automata.mealy.MooreMachine
import jflap.automata.mealy.MooreStepByStateSimulator
import jflap.automata.pda.PushdownAutomaton
import pl.poznan.put.cie.putflap.jflapextensions.automaton.PDAStepByStepSimulatorCustom
import pl.poznan.put.cie.putflap.testutils.AutomatonReader
import spock.lang.Specification
import spock.lang.Unroll

class WordGeneratorSpecification extends Specification {

    def wordGenerator = new WordGenerator()

    void setup() {
    }

    def "should generate random valid FSA word"() {
        given:
        def automaton = new AutomatonReader().get("fsa_simple.jff")

        when:
        def word = wordGenerator.randomSingle(automaton)

        then:
        new FSAStepByStateSimulator(automaton).simulateInput(word)
    }

    def "should generate random valid FSA words"() {
        given:
        def automaton = new AutomatonReader().get("fsa_simple.jff")
        def n = 50

        when:
        def words = wordGenerator.randomMultiple(automaton, n)

        then:
        words.size() <= n
        checkAllWords(automaton, words)
    }

    def "should generate random valid Mealy word"() {
        given:
        def automaton = new AutomatonReader().get("mealy.jff")

        when:
        def word = wordGenerator.randomSingle(automaton)

        then:
        new MealyStepByStateSimulator(automaton).simulateInput(word)
    }

    def "should generate random valid Mealy words"() {
        given:
        def automaton = new AutomatonReader().get("mealy.jff")
        def n = 50

        when:
        def words = wordGenerator.randomMultiple(automaton, n)

        then:
        words.size() <= n
        checkAllWords(automaton, words)
    }

    def "should generate random valid Moore word"() {
        given:
        def automaton = new AutomatonReader().get("moore.jff")

        when:
        def word = wordGenerator.randomSingle(automaton)

        then:
        new MooreStepByStateSimulator(automaton).simulateInput(word)
    }

    def "should generate random valid Moore words"() {
        given:
        def automaton = new AutomatonReader().get("moore.jff")
        def n = 50

        when:
        def words = wordGenerator.randomMultiple(automaton, n)

        then:
        words.size() <= n
        checkAllWords(automaton, words)
    }

    def "should generate random valid PDA word"() {
        given:
        def automaton = new AutomatonReader().get("pda.jff") as PushdownAutomaton

        when:
        def word = wordGenerator.randomSingle(automaton)

        then:
        new PDAStepByStepSimulatorCustom(automaton, PDAStepByStepSimulatorCustom.FINAL_STATE_ACCEPTANCE).simulateInput(word)
    }

    def "should generate random valid PDA words"() {
        given:
        def automaton = new AutomatonReader().get("pda.jff")
        def n = 50

        when:
        def words = wordGenerator.randomMultiple(automaton, n)

        then:
        words.size() <= n
        checkAllWords(automaton, words)
    }

    def "should get start state of word for FSA"() {
        given:
        def automaton = AutomatonReader.get("fsa_simple.jff")

        when:
        def startState = wordGenerator.getStartState(automaton)

        then:
        startState != automaton.getInitialState()
        automaton.finalStates.contains(startState)

    }

    @Unroll
    def "should get start state of word for Mealy/Moore"() {
        when:
        def startState = wordGenerator.getStartState(automaton)

        then:
        startState != automaton.getInitialState()

        where:
        automaton << [
                AutomatonReader.get("mealy.jff"),
                AutomatonReader.get("moore.jff"),
        ]
    }

    def "should get start state of word for PDA"() {
        given:
        def automaton = AutomatonReader.get("pda.jff")

        when:
        def startState = wordGenerator.getStartState(automaton)

        then:
        startState != automaton.getInitialState()
        automaton.finalStates.contains(startState)
    }

    private static def checkAllWords(Automaton automaton, String[] words) {
        def simulator
        if (automaton instanceof  FiniteStateAutomaton) simulator = new FSAStepByStateSimulator(automaton)
        else if (automaton instanceof  MooreMachine) simulator = new MooreStepByStateSimulator(automaton)
        else if (automaton instanceof  MealyMachine) simulator = new MealyStepByStateSimulator(automaton)
        else if (automaton instanceof  PushdownAutomaton) simulator = new PDAStepByStepSimulatorCustom(automaton, PDAStepByStepSimulatorCustom.FINAL_STATE_ACCEPTANCE)

        words.each { word -> if (!simulator.simulateInput(word)) return false }
        return true
    }
}
