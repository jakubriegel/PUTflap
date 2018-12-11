package pl.poznan.put.cie.putflap.report

import jflap.automata.Automaton
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonTester
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.report.structure.StructureReport
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport
import pl.poznan.put.cie.putflap.report.structure.grammar.GrammarReport
import pl.poznan.put.cie.putflap.report.test.AlphabetReport
import pl.poznan.put.cie.putflap.report.test.NondeterminismReport

data class GenerationReport internal constructor(
    val type: String,
    val alphabet: AlphabetReport,
    val nondeterminism: NondeterminismReport?,
    val production: StructureReport
) : Report() {

    constructor(automaton: Automaton) : this(
        AutomatonType.get(automaton).toString(),
        AutomatonTester.getAlphabet(automaton),
        AutomatonTester.checkNondeterminism(automaton),
        AutomatonReport.generate(automaton)
    )

    constructor(grammar: Grammar) : this(
        "grammar",
        AutomatonTester.getAlphabet(grammar),
        null,
        GrammarReport(grammar)
    )
}