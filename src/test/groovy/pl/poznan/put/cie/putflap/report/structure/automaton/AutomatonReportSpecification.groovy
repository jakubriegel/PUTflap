package pl.poznan.put.cie.putflap.report.structure.automaton

import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MooreMachine
import jflap.automata.pda.PushdownAutomaton
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.testutils.AutomatonReader
import spock.lang.Specification

class AutomatonReportSpecification extends Specification {
    def "should create automaton report from FSA"() {
        given:
        def automaton = AutomatonReader.get("fsa_simple.jff") as FiniteStateAutomaton

        when:
        def automatonReport = new AutomatonReport(automaton)

        then:
        automatonReport.type == AutomatonType.FA
        automatonReport.states.size() == automaton.states.size()
        automatonReport.transitions.size() == automaton.transitions.size()
    }

    def "should create automaton report from Mealy"() {
        given:
        def automaton = AutomatonReader.get("mealy.jff") as MealyMachine

        when:
        def automatonReport = new AutomatonReport(automaton)

        then:
        automatonReport.type == AutomatonType.MEALY
        automatonReport.states.size() == automaton.states.size()
        automatonReport.transitions.size() == automaton.transitions.size()
    }

    def "should create automaton report from Moore"() {
        given:
        def automaton = AutomatonReader.get("moore.jff") as MooreMachine

        when:
        def automatonReport = new AutomatonReport(automaton)

        then:
        automatonReport.type == AutomatonType.MOORE
        automatonReport.states.size() == automaton.states.size()
        automatonReport.transitions.size() == automaton.transitions.size()
    }

    def "should create automaton report from PDA"() {
        given:
        def automaton = AutomatonReader.get("pda.jff") as PushdownAutomaton

        when:
        def automatonReport = new AutomatonReport(automaton)

        then:
        automatonReport.type == AutomatonType.PDA
        automatonReport.states.size() == automaton.states.size()
        automatonReport.transitions.size() == automaton.transitions.size()
    }
}
