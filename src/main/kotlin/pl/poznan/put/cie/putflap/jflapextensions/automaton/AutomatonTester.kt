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
import pl.poznan.put.cie.putflap.report.AlphabetReport
import pl.poznan.put.cie.putflap.report.test.EqualityReport
import pl.poznan.put.cie.putflap.report.test.LambdaTransitionsReport
import pl.poznan.put.cie.putflap.report.test.NondeterminismReport

object AutomatonTester {
    fun checkNondeterminism(automaton: Automaton): NondeterminismReport {
        val detector = NondeterminismDetectorFactory.getDetector(automaton)
        val states = detector.getNondeterministicStates(automaton)

        return NondeterminismReport(
            states.isEmpty(),
            states,
            checkLambdaTransitions(automaton)
        )
    }

    fun checkLambdaTransitions(automaton: Automaton): LambdaTransitionsReport {
        val checker = LambdaCheckerFactory.getLambdaChecker(automaton)
        val transitions = automaton.transitions
        val lambdaTransitions = mutableListOf<Transition>()
        transitions.forEach { if (checker.isLambdaTransition(it)) lambdaTransitions.add(it) }

        return LambdaTransitionsReport.generate(
            lambdaTransitions.isNotEmpty()
        )
    }

    fun checkEqualityOfFSAs(a1: FiniteStateAutomaton, a2: FiniteStateAutomaton): EqualityReport {
        val equal = FSAEqualityChecker().equals(a1, a2)
        return EqualityReport(equal)
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
        return AlphabetReport(inAlphabet.sorted().toTypedArray(), outAlphabet?.sorted()?.toTypedArray())
    }

    private val grammarAlphabetRegex = Regex("[a-z]+")

    fun getAlphabet(grammar: Grammar): AlphabetReport {
        val alphabet = mutableSetOf<String>()
        grammar.productions.forEach { production ->
            grammarAlphabetRegex.findAll(production.lhs).forEach { alphabet.add(it.value) }
            grammarAlphabetRegex.findAll(production.rhs).forEach { alphabet.add(it.value) }
        }
        return AlphabetReport(alphabet.sorted().toTypedArray())
    }
}