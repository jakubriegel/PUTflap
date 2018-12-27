package pl.poznan.put.cie.putflap.jflapextensions.automaton

import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import jflap.automata.fsa.FSATransition
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MealyTransition
import jflap.automata.mealy.MooreMachine
import jflap.automata.mealy.MooreTransition
import jflap.automata.pda.PDATransition
import jflap.automata.pda.PushdownAutomaton
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
        def finalStateX = 2
        def finalStateY = 3
        finalState.x >> finalStateX
        finalState.y >> finalStateY

        def states = [initialState, state, finalState]
        report.states >> states

        def transition1 = Mock(TransitionReport)
        transition1.from >> initialStateId
        transition1.to >> stateId
        transition1.read >> "a"

        def transition2 = Mock(TransitionReport)
        transition2.from >> stateId
        transition2.to >> finalStateId
        def transition2Label = "b"
        transition2.read >> transition2Label

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
        automaton.getFinalStates()[0].point.x == finalStateX
        automaton.getFinalStates()[0].point.y == finalStateY

        automaton.transitions.size() == transitions.size()
        automaton.transitions[0] instanceof FSATransition
        automaton.getTransitionsToState(automaton.getFinalStates()[0]).size() == 1
        automaton.getTransitionsToState(automaton.getFinalStates()[0])[0].fromState.ID == stateId
        ((FSATransition) automaton.getTransitionsToState(automaton.getFinalStates()[0])[0]).label == transition2Label
    }

    def "should create Mealy from report"() {
        given:
        def report = GroovyMock(AutomatonReport)
        report.type >> AutomatonType.MEALY

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
        def finalStateX = 2
        def finalStateY = 3
        finalState.x >> finalStateX
        finalState.y >> finalStateY

        def states = [initialState, state, finalState]
        report.states >> states

        def transition1 = Mock(TransitionReport)
        transition1.from >> initialStateId
        transition1.to >> stateId
        transition1.read >> "a"

        def transition2 = Mock(TransitionReport)
        transition2.from >> stateId
        transition2.to >> finalStateId
        def transition2Label = "b"
        transition2.read >> transition2Label
        def transition2Out = "x"
        transition2.value >> transition2Out

        def transitions = [transition1, transition2]
        report.transitions >> transitions

        when:
        def automaton = automatonCreator.fromReportMealy(report)

        then:
        automaton instanceof MealyMachine

        automaton.states.size() == states.size()
        automaton.getInitialState().ID == initialStateId
        automaton.getFinalStates().size() == 1
        automaton.getFinalStates()[0].ID == finalStateId
        automaton.getFinalStates()[0].point.x == finalStateX
        automaton.getFinalStates()[0].point.y == finalStateY

        automaton.transitions.size() == transitions.size()
        automaton.transitions[0] instanceof MealyTransition
        automaton.getTransitionsToState(automaton.getFinalStates()[0]).size() == 1
        automaton.getTransitionsToState(automaton.getFinalStates()[0])[0].fromState.ID == stateId
        ((MealyTransition) automaton.getTransitionsToState(automaton.getFinalStates()[0])[0]).label == transition2Label
        ((MealyTransition) automaton.getTransitionsToState(automaton.getFinalStates()[0])[0]).output == transition2Out
    }

    def "should create Moore from report"() {
        given:
        def report = GroovyMock(AutomatonReport)
        report.type >> AutomatonType.MOORE

        def initialState = Mock(StateReport)
        def initialStateId = 0
        initialState.id >> initialStateId
        initialState.name >> "q0"
        initialState.label >> ""
        initialState.type >> StateType.INITIAL
        initialState.x >> 1
        initialState.y >> 1
        initialState.value >> "x"

        def state = Mock(StateReport)
        def stateId = 1
        state.id >> stateId
        state.name >> "q1"
        state.label >> ""
        state.type >> StateType.STATE
        state.x >> 1
        state.y >> 1
        state.value >> "y"

        def finalState = Mock(StateReport)
        def finalStateId = 2
        finalState.id >> finalStateId
        finalState.name >> "q2"
        finalState.label >> ""
        finalState.type >> StateType.FINAL
        def finalStateX = 2
        def finalStateY = 3
        finalState.x >> finalStateX
        finalState.y >> finalStateY
        def finalStateValue = "z"
        finalState.value >> finalStateValue

        def states = [initialState, state, finalState]
        report.states >> states

        def transition1 = Mock(TransitionReport)
        transition1.from >> initialStateId
        transition1.to >> stateId
        transition1.read >> "a"

        def transition2 = Mock(TransitionReport)
        transition2.from >> stateId
        transition2.to >> finalStateId
        def transition2Label = "b"
        transition2.read >> transition2Label

        def transitions = [transition1, transition2]
        report.transitions >> transitions

        when:
        def automaton = automatonCreator.fromReportMoore(report)

        then:
        automaton instanceof MooreMachine

        automaton.states.size() == states.size()
        automaton.getInitialState().ID == initialStateId
        automaton.getFinalStates().size() == 1
        automaton.getFinalStates()[0].ID == finalStateId
        automaton.getFinalStates()[0].point.x == finalStateX
        automaton.getFinalStates()[0].point.y == finalStateY
        automaton.getOutput(automaton.getFinalStates()[0]) == finalStateValue

        automaton.transitions.size() == transitions.size()
        automaton.transitions[0] instanceof MooreTransition
        automaton.getTransitionsToState(automaton.getFinalStates()[0]).size() == 1
        automaton.getTransitionsToState(automaton.getFinalStates()[0])[0].fromState.ID == stateId
        ((MooreTransition) automaton.getTransitionsToState(automaton.getFinalStates()[0])[0]).label == transition2Label
    }

    def "should create PDA from report"() {
        given:
        def report = GroovyMock(AutomatonReport)
        report.type >> AutomatonType.PDA

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
        def finalStateX = 2
        def finalStateY = 3
        finalState.x >> finalStateX
        finalState.y >> finalStateY

        def states = [initialState, state, finalState]
        report.states >> states

        def transition1 = Mock(TransitionReport)
        transition1.from >> initialStateId
        transition1.to >> stateId
        transition1.read >> "a"
        transition1.pop >> "Z"
        transition1.push >> "xZ"

        def transition2 = Mock(TransitionReport)
        transition2.from >> stateId
        transition2.to >> finalStateId
        transition2.read >> ""
        def transition2Pop = "xZ"
        def transition2Push = "Z"
        transition2.pop >> transition2Pop
        transition2.push >> transition2Push

        def transitions = [transition1, transition2]
        report.transitions >> transitions

        when:
        def automaton = automatonCreator.fromReportPDA(report)

        then:
        automaton instanceof PushdownAutomaton

        automaton.states.size() == states.size()
        automaton.getInitialState().ID == initialStateId
        automaton.getFinalStates().size() == 1
        automaton.getFinalStates()[0].ID == finalStateId
        automaton.getFinalStates()[0].point.x == finalStateX
        automaton.getFinalStates()[0].point.y == finalStateY

        automaton.transitions.size() == transitions.size()
        automaton.transitions[0] instanceof PDATransition
        automaton.getTransitionsToState(automaton.getFinalStates()[0]).size() == 1
        automaton.getTransitionsToState(automaton.getFinalStates()[0])[0].fromState.ID == stateId
        ((PDATransition) automaton.getTransitionsToState(automaton.getFinalStates()[0])[0]).stringToPop == transition2Pop
        ((PDATransition) automaton.getTransitionsToState(automaton.getFinalStates()[0])[0]).stringToPush == transition2Push
    }
}
