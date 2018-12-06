package pl.poznan.put.cie.putflap.report

import jflap.automata.Automaton
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonTester
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.report.structure.StructureReport
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport
import pl.poznan.put.cie.putflap.report.structure.grammar.GrammarReport
import pl.poznan.put.cie.putflap.report.test.LambdaTransitionsReport
import pl.poznan.put.cie.putflap.report.test.NondeterminismReport

class GenerationReport private constructor(
    val type: String,
    val alphabet: AlphabetReport,
    val nondeterminism: NondeterminismReport?,
    val lambdaTransitions: LambdaTransitionsReport?,
    val production: StructureReport
) : Report() {

    constructor(automaton: Automaton) : this(
        AutomatonType.get(automaton).toString(),
        AutomatonTester.getAlphabet(automaton),
        AutomatonTester.checkNondeterminism(automaton),
        AutomatonTester.checkLambdaTransitions(automaton),
        AutomatonReport(automaton)
    )

    constructor(grammar: Grammar) : this(
        "grammar",
        AutomatonTester.getAlphabet(grammar),
        null,
        null,
        GrammarReport(grammar)
    )

    override fun toString(): String {
        return "GenerationReport(type='$type', alphabet=$alphabet, nondeterminism=$nondeterminism, lambdaTransitions=$lambdaTransitions, production=$production)"
    }
}