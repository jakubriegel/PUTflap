package pl.poznan.put.cie.putflap.report.structure.automaton

import jflap.automata.Automaton
import jflap.automata.State
import jflap.automata.fsa.FSAConfiguration
import jflap.automata.mealy.MealyConfiguration
import jflap.automata.pda.CharacterStack
import jflap.automata.pda.PDAConfiguration
import pl.poznan.put.cie.putflap.jflapextensions.automaton.PDAStepByStepSimulatorCustom
import spock.lang.Specification

import java.awt.*

class StepReportSpecification extends Specification {

    def automaton = Mock(Automaton)
    def stateId = 1
    def state = new State(stateId, new Point(), automaton)
    def input = "abcdef"
    def read = "bc"
    def unprocessed = "def"
    def unprocessedParent = "bcdef"

    def "should create step report from FSAConfiguration"() {
        given:
        def parent = Mock(FSAConfiguration)
        parent.unprocessedInput >> unprocessedParent
        def configuration = new FSAConfiguration(state, parent, input, unprocessed)

        when:
        def stepReport = new StepReport(configuration)

        then:
        stepReport.state == stateId
        stepReport.read == read
        stepReport.toProcess == unprocessed
        stepReport.currentOutput == null
        stepReport.stack == null
    }

    def "should create step report from MealyConfiguration"() {
        given:
        def parent = Mock(MealyConfiguration)
        parent.unprocessedInput >> unprocessedParent

        def output = "x"
        def configuration = new MealyConfiguration(state, parent, input, unprocessed, output)

        when:
        def stepReport = new StepReport(configuration)

        then:
        stepReport.state == stateId
        stepReport.read == read
        stepReport.toProcess == unprocessed
        stepReport.currentOutput == output
        stepReport.stack == null
    }

    def "should create step report from PDAConfiguration"() {
        given:
        def parent = Mock(PDAConfiguration)
        parent.unprocessedInput >> unprocessedParent

        def stack = new CharacterStack()
        stack.push("xyZ")
        def configuration = new PDAConfiguration(
                state,
                parent,
                input,
                unprocessed,
                stack,
                PDAStepByStepSimulatorCustom.FINAL_STATE_ACCEPTANCE
        )

        when:
        def stepReport = new StepReport(configuration)

        then:
        stepReport.state == stateId
        stepReport.read == read
        stepReport.toProcess == unprocessed
        stepReport.currentOutput == null
        stepReport.stack == stack.toString()
    }
}
