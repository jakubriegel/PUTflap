package pl.poznan.put.cie.putflap.jflapextensions.automaton

import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import jflap.automata.fsa.FiniteStateAutomaton
import org.junit.runner.RunWith
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport
import pl.poznan.put.cie.putflap.report.structure.automaton.StateReport
import pl.poznan.put.cie.putflap.report.structure.automaton.TransitionReport
import spock.lang.Specification

@RunWith(SpotlinTestRunner)
@OpenedClasses([AutomatonCreator, AutomatonReport, StateReport, TransitionReport])
class AutomatonCreatorSpecification extends Specification {
    def automatonCreator = AutomatonCreator.INSTANCE

    def "should create FSA from report"() {
        given:
        def report = GroovyMock(AutomatonReport)
        report.type >> AutomatonType.FA

        def initialState = Mock(StateReport)
        def initialStateId = 0
        initialState.id >> initialStateId
        initialState.name >> "q0"
        initialState.label >> ""
        initialState.type >> StateType.INITIAL
        initialState.x >> 1
        initialState.y >> 1

        def state = Mock(StateReport)
        def stateId = 1
        state.id >> stateId
        state.name >> "q1"
        state.label >> ""
        state.type >> StateType.STATE
        state.x >> 1
        state.y >> 1

        def finalState = Mock(StateReport)
        def finalStateId = 2
        finalState.id >> finalStateId
        finalState.name >> "q2"
        finalState.label >> ""
        finalState.type >> StateType.FINAL
        finalState.x >> 1
        finalState.y >> 1

        def states = [initialState, state, finalState]
        report.states >> states

        def transition1 = Mock(TransitionReport)
        transition1.from >> initialStateId
        transition1.to >> stateId
        transition1.read >> "a"

        def transition2 = Mock(TransitionReport)
        transition2.from >> stateId
        transition2.to >> finalStateId
        transition2.read >> "b"

        def transitions = [transition1, transition2]
        report.transitions >> transitions

        when:
        def automaton = automatonCreator.fromReportFSA(report)

        then:
        automaton instanceof FiniteStateAutomaton

        automaton.states.size() == states.size()
        automaton.getInitialState().ID == initialStateId
        automaton.getFinalStates().size() == 1
        automaton.getFinalStates()[0].ID == finalStateId

        automaton.transitions.size() == transitions.size()
        automaton.getTransitionsToState(automaton.getFinalStates()[0]).size() == 1
        automaton.getTransitionsToState(automaton.getFinalStates()[0])[0].fromState.ID == stateId
    }
}
