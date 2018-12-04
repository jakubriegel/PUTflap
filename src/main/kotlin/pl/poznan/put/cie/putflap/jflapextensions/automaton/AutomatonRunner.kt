package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.Automaton
import pl.poznan.put.cie.putflap.report.ErrorReport
import pl.poznan.put.cie.putflap.report.RunReport
import pl.poznan.put.cie.putflap.report.structure.automaton.StepReport

object AutomatonRunner {
    fun runAutomaton(automaton: Automaton, input: String): RunReport {
        if (automaton.initialState == null) return RunReport.generateWithError(
            AutomatonType.get(automaton), input, ErrorReport.generate(ErrorReport.Companion.Error.NO_INITIAL_STATE)
        )
        if (!AutomatonTester.checkNondeterminism(automaton).deterministic) return RunReport.generateWithError(
            AutomatonType.get(automaton), input, ErrorReport.generate(ErrorReport.Companion.Error.NON_DETERMINISM)
        )

        val simulator = SimulatorFactoryCustom.getSimulator(automaton)!!
        val initialConfiguration = simulator.getInitialConfigurations(input)[0]
        val possibleConfigurations = simulator.stepConfiguration(initialConfiguration)

        var accepted = false
        val steps = mutableListOf<StepReport>()
        // TODO: implement adding initial step to steps

        while (possibleConfigurations.isNotEmpty()) {
            val configuration = possibleConfigurations.removeAt(0)
            steps.add(StepReport.generate(configuration))

            if(configuration.isAccept) {
                accepted = true
                break
            }

            possibleConfigurations.addAll(simulator.stepConfiguration(configuration))
        }

        return RunReport.generate(AutomatonType.get(automaton), input, accepted, steps.toTypedArray())
    }
}