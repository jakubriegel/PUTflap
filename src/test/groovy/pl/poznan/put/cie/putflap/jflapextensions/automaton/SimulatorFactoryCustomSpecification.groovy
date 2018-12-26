package pl.poznan.put.cie.putflap.jflapextensions.automaton


import jflap.automata.fsa.FSAStepWithClosureSimulator
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MealyStepByStateSimulator
import jflap.automata.mealy.MooreMachine
import jflap.automata.mealy.MooreStepByStateSimulator
import jflap.automata.pda.PushdownAutomaton
import spock.lang.Specification
import spock.lang.Unroll

class SimulatorFactoryCustomSpecification extends Specification {
    @Unroll
    def "should get proper simulator" () {
        when:
        def simulator = SimulatorFactoryCustom.INSTANCE.getSimulator(automaton)

        then:
        simulator.class == result

        where:
        automaton                  || result
        new FiniteStateAutomaton() || FSAStepWithClosureSimulator.class
        new PushdownAutomaton()    || PDAStepByStepSimulatorCustom.class
        new MooreMachine()         || MooreStepByStateSimulator.class
        new MealyMachine()         || MealyStepByStateSimulator.class
    }
}
