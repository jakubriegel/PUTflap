package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.Automaton
import jflap.automata.LambdaCheckerFactory
import jflap.automata.NondeterminismDetectorFactory
import jflap.automata.Transition
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.graph.FSAEqualityChecker
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MealyTransition
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.report.test.AlphabetReport
import pl.poznan.put.cie.putflap.report.test.EquivalenceReport
import pl.poznan.put.cie.putflap.report.test.LambdaTransitionsReport
import pl.poznan.put.cie.putflap.report.test.MultipleAlphabetReport
import pl.poznan.put.cie.putflap.report.test.MultipleNondeterminismReport
import pl.poznan.put.cie.putflap.report.test.NondeterminismReport

/**
 * Implements JFLAP tests
 */
object AutomatonTester {

    fun checkNondeterminism(automatons: Array<Automaton>): MultipleNondeterminismReport {
        val reports = Array(automatons.size) { checkNondeterminism(automatons[it]) }

        return MultipleNondeterminismReport(
            reports.all { it.deterministic },
            reports
        )
    }

    fun checkNondeterminism(automaton: Automaton): NondeterminismReport {
        val detector = NondeterminismDetectorFactory.getDetector(automaton)
        val states = detector.getNondeterministicStates(automaton)

        return NondeterminismReport(
            states.isEmpty(),
            states,
            checkLambdaTransitions(automaton)
        )
    }

    private fun checkLambdaTransitions(automaton: Automaton): LambdaTransitionsReport {
        val checker = LambdaCheckerFactory.getLambdaChecker(automaton)
        val transitions = automaton.transitions
        val lambdaTransitions = mutableListOf<Transition>()
        transitions.forEach { if (checker.isLambdaTransition(it)) lambdaTransitions.add(it) }

        return LambdaTransitionsReport(
            lambdaTransitions.isNotEmpty()
        )
    }

    fun checkEquivalenceOfManyFSAs(automatons: Array<FiniteStateAutomaton>): EquivalenceReport {
        var allEquivalent = true
        for (i in 1 until automatons.size) if (!checkEquivalenceOfTwoFSAs(automatons[0], automatons[i]).equivalent) {
            allEquivalent = false
            break
        }

        return EquivalenceReport(allEquivalent)
    }

    private fun checkEquivalenceOfTwoFSAs(a1: FiniteStateAutomaton, a2: FiniteStateAutomaton): EquivalenceReport {
        val equal = FSAEqualityChecker().equals(a1, a2)
        return EquivalenceReport(equal)
    }

    fun getAlphabets(automatons: Array<Automaton>): MultipleAlphabetReport {
        return MultipleAlphabetReport(
            Array(automatons.size) { getAlphabet(automatons[it]) }
        )
    }

    fun getAlphabet(automaton: Automaton): AlphabetReport {
        val inAlphabet = mutableSetOf<String>()
        automaton.transitions.forEach { inAlphabet.add(it.labelValue()) }
        val outAlphabet = when (automaton) {
            is MealyMachine -> {
                val outAlphabet = mutableSetOf<String>()
                automaton.transitions.forEach { outAlphabet.add((it as MealyTransition).output) }
                outAlphabet
            }
            else -> null
        }
        return AlphabetReport(
            inAlphabet.sorted().toTypedArray(),
            outAlphabet?.sorted()?.toTypedArray()
        )
    }

    private val grammarAlphabetRegex = Regex("[a-z]+")

    fun getAlphabets(grammars: Array<Grammar>): MultipleAlphabetReport {
        return MultipleAlphabetReport(
            Array(grammars.size) { getAlphabet(grammars[it]) }
        )
    }

    fun getAlphabet(grammar: Grammar): AlphabetReport {
        val alphabet = mutableSetOf<String>()
        grammar.productions.forEach { production ->
            grammarAlphabetRegex.findAll(production.lhs).forEach { alphabet.add(it.value) }
            grammarAlphabetRegex.findAll(production.rhs).forEach { alphabet.add(it.value) }
        }
        return AlphabetReport(alphabet.sorted().toTypedArray())
    }
}