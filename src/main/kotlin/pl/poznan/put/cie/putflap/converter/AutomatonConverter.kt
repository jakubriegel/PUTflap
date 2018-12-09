package pl.poznan.put.cie.putflap.converter

import jflap.automata.Automaton
import jflap.automata.Transition
import jflap.automata.fsa.FSAToRegularExpressionConverter
import jflap.automata.fsa.FSAToRegularGrammarConverter
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.fsa.Minimizer
import jflap.automata.fsa.NFAToDFA
import jflap.automata.pda.PDAToCFGConverter
import jflap.automata.pda.PushdownAutomaton
import jflap.grammar.Grammar
import jflap.grammar.GrammarChecker
import jflap.grammar.Production
import jflap.grammar.cfg.ContextFreeGrammar
import jflap.grammar.reg.RightLinearGrammarToFSAConverter
import jflap.gui.grammar.GrammarTableModel
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.report.ConversionReport
import pl.poznan.put.cie.putflap.report.MultipleConversionReport
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport
import pl.poznan.put.cie.putflap.report.structure.grammar.GrammarReport
import pl.poznan.put.cie.putflap.report.structure.regexp.RegExpReport

object AutomatonConverter {

    fun toDeterministicFSA(automatons: Array<FiniteStateAutomaton>): Pair<MultipleConversionReport, Array<FiniteStateAutomaton>> {
        val conversions = Array(automatons.size) { toDeterministicFSA(automatons[it]) }
        val reports = Array(conversions.size) { conversions[it].first }
        return Pair(
            MultipleConversionReport(
                "DFSA",
                reports.all { it.success },
                reports
            ),
            Array(conversions.size) { conversions[it].second }
        )
    }

    fun toDeterministicFSA(automaton: FiniteStateAutomaton): Pair<ConversionReport, FiniteStateAutomaton> {
        val result = NFAToDFA().convertToDFA(automaton)
        return Pair(ConversionReport(
            AutomatonType.get(automaton).toString(), AutomatonType.get(result).toString(), true, AutomatonReport(result)),
            result
        )
    }

    fun toMinimalFSA(automatons: Array<FiniteStateAutomaton>): Pair<MultipleConversionReport, Array<FiniteStateAutomaton>> {
        val conversions = Array(automatons.size) { toMinimalFSA(automatons[it]) }
        val reports = Array(conversions.size) { conversions[it].first }
        return Pair(
            MultipleConversionReport(
                "MFSA",
                reports.all { it.success },
                reports
            ),
            Array(conversions.size) { conversions[it].second }
        )
    }

    fun toMinimalFSA(automaton: FiniteStateAutomaton):  Pair<ConversionReport, FiniteStateAutomaton> {
        val minimizer = Minimizer()
        minimizer.initializeMinimizer()
        minimizer.addTrapState(automaton)
        val result = minimizer.getMinimumDfa(automaton, minimizer.getDistinguishableGroupsTree(automaton))
        return Pair(ConversionReport(
            AutomatonType.get(automaton).toString(), AutomatonType.get(result).toString(), true, AutomatonReport(result)),
            result
        )
    }

    fun toGrammar(automatons: Array<Automaton>): Pair<MultipleConversionReport, Array<Grammar>> {
        val conversions = Array(automatons.size) { toGrammar(automatons[it]) }
        val reports = Array(conversions.size) { conversions[it].first }
        return Pair(
            MultipleConversionReport(
                "GR",
                reports.all { it.success },
                reports
            ),
            Array(conversions.size) { conversions[it].second }
        )
    }

    fun toGrammar(automaton: Automaton): Pair<ConversionReport, Grammar> {
        val grammar =  when (automaton) {
            is FiniteStateAutomaton -> FSAToRegularGrammarConverter().convertToRegularGrammar(automaton)
            is PushdownAutomaton -> {
                val converter = PDAToCFGConverter()
                if (!converter.isInCorrectFormForConversion(automaton)) throw IncompatibleAutomatonException()

                val model = GrammarTableModel()
                val transitionToProduction = mutableMapOf<Transition, Array<Production>>()
                val productionToTransition = mutableMapOf<Production, Transition>()

                automaton.transitions.forEach { transition ->
                    val productions = converter.createProductionsForTransition(transition, automaton).toTypedArray()
                    transitionToProduction[transition] = productions
                    transitionToProduction[transition]?.forEach { productionToTransition[it] = transition }
                }

                transitionToProduction.values.forEach { productions ->
                    productions.forEach { model.addProduction(it) }
                }

                converter.purgeProductions(automaton, model)
                val ready = Array(model.productions.size) { converter.getSimplifiedProduction(model.productions[it]) }
                val grammar = ContextFreeGrammar()
                grammar.startVariable = "S"
                grammar.addProductions(ready)

                grammar
            }
            else -> throw IncompatibleAutomatonException("Only FSA and PDA to grammar conversion is currently supported")
        }

        return Pair(ConversionReport(
            AutomatonType.get(automaton).toString(), "grammar", true, GrammarReport(grammar)),
            grammar
        )
    }

    fun toRegularExpression(automatons: Array<FiniteStateAutomaton>): Pair<MultipleConversionReport, Array<String>> {
        val conversions = Array(automatons.size) { toRegularExpression(automatons[it]) }
        val reports = Array(conversions.size) { conversions[it].first }
        return Pair(
            MultipleConversionReport(
                "REGEX",
                reports.all { it.success },
                reports
            ),
            Array(conversions.size) { conversions[it].second }
        )
    }

    fun toRegularExpression(automaton: FiniteStateAutomaton): Pair<ConversionReport, String> {
        if (!FSAToRegularExpressionConverter.isConvertable(automaton))
            throw IncompatibleAutomatonException("Incorrect form of FSA to perform conversion")

        FSAToRegularExpressionConverter.convertToSimpleAutomaton(automaton)
        val regexp = FSAToRegularExpressionConverter.convertToRegularExpression(automaton)
        return Pair(ConversionReport(
            AutomatonType.get(automaton).toString(), "grammar", true, RegExpReport(regexp)),
            regexp
        )
    }

    fun toFSA(grammars: Array<Grammar>): Pair<MultipleConversionReport, Array<FiniteStateAutomaton>> {
        val conversions = Array(grammars.size) { toFSA(grammars[it]) }
        val reports = Array(conversions.size) { conversions[it].first }
        return Pair(
            MultipleConversionReport(
                "FSA",
                reports.all { it.success },
                reports
            ),
            Array(conversions.size) { conversions[it].second }
        )
    }

    fun toFSA(grammar: Grammar): Pair<ConversionReport, FiniteStateAutomaton> {
        val automaton = if (GrammarChecker.isRightLinearGrammar(grammar)) {
            val prototype = RightLinearGrammarToFSAConverter().convertToAutomaton(grammar)
            val automaton = FiniteStateAutomaton()
            prototype.states.forEach {
                it.automaton = automaton
                automaton.addState(it)
            }
            prototype.transitions.forEach { automaton.addTransition(it) }
            automaton.finalStates = prototype.finalStates
            automaton.initialState = automaton.states.find { it.id == 0 }
            automaton
        }
        else
            throw IncompatibleAutomatonException("Only regular, right linear grammar can be converted to FSA")

        return Pair(ConversionReport(
            AutomatonType.get(automaton).toString(), "grammar", true, AutomatonReport(automaton)),
            automaton
        )
    }

    fun toJSON(automatons: Array<Automaton>): Pair<MultipleConversionReport, Array<Automaton>> {
        val conversions = Array(automatons.size) { toJSON(automatons[it]) }
        val reports = Array(conversions.size) { conversions[it].first }
        return Pair(
            MultipleConversionReport(
                "JSON",
                reports.all { it.success },
                reports
            ),
            Array(conversions.size) { conversions[it].second }
        )
    }

    fun toJSON(automaton: Automaton): Pair<ConversionReport, Automaton> {
        return Pair(ConversionReport(
            AutomatonType.get(automaton).toString(), "grammar", true, AutomatonReport(automaton)),
            automaton
        )
    }

    fun toJSON(grammars: Array<Grammar>): Pair<MultipleConversionReport, Array<Grammar>> {
        val conversions = Array(grammars.size) { toJSON(grammars[it]) }
        val reports = Array(conversions.size) { conversions[it].first }
        return Pair(
            MultipleConversionReport(
                "JSON",
                reports.all { it.success },
                reports
            ),
            Array(conversions.size) { conversions[it].second }
        )
    }

    fun toJSON(grammar: Grammar): Pair<ConversionReport, Grammar> {
        return Pair(ConversionReport(
            "grammar", "grammar", true, GrammarReport(grammar)),
            grammar
        )
    }
}
