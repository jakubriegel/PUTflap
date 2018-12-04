package pl.poznan.put.cie.putflap.generator

import jflap.automata.fsa.FSAStepByStateSimulator
import jflap.automata.fsa.FSATransition
import jflap.automata.fsa.FiniteStateAutomaton
import spock.lang.Specification

import java.awt.*

class WordGeneratorSpecification extends Specification {

    def fsaWordGenerator = new WordGenerator()

    FiniteStateAutomaton automaton = new FiniteStateAutomaton()

    void setup() {
        def states = []
        for (i in 0..5) states.add(automaton.createState(new Point()))

        automaton.initialState = states[0]
        automaton.addFinalState(states[3])
        automaton.addFinalState(states[4])

        automaton.addTransition(new FSATransition(states[0], states[4], "e"))
        automaton.addTransition(new FSATransition(states[1], states[0], "h"))
        automaton.addTransition(new FSATransition(states[2], states[3], "a"))
        automaton.addTransition(new FSATransition(states[4], states[1], "e"))
        automaton.addTransition(new FSATransition(states[4], states[2], "f"))
        automaton.addTransition(new FSATransition(states[4], states[4], "a"))
    }

    def "shouldGenerateRandomValidFSAWord"() {
        given:
        def words = []

        when:
        for (def i = 0; i < 20; i++)
            words.add(fsaWordGenerator.randomWord(automaton))

        then:
        checkAllWords()
    }

    private def checkAllWords(words) {
        def fsaStepByStepSimulator = new FSAStepByStateSimulator(automaton)
        words.each { word -> if (!fsaStepByStepSimulator.simulateInput(word)) return false }
        return true
    }
}
