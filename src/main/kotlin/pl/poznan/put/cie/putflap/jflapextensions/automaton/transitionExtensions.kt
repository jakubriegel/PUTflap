package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.Transition
import jflap.automata.fsa.FSATransition
import jflap.automata.mealy.MealyTransition
import jflap.automata.pda.PDATransition

fun Transition.labelValue(): String {
    return when (this) {
        is FSATransition -> label
        is MealyTransition -> label
        is PDATransition -> inputToRead
        else -> TODO("implement labelValue() for all automatons")
    }
}
