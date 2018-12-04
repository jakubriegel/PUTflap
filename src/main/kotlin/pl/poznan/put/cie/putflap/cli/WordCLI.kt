package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import jflap.automata.Automaton
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.pda.PushdownAutomaton
import jflap.file.XMLCodec
import pl.poznan.put.cie.putflap.generator.WordGenerator
import java.io.File

internal object WordCLI : CliktCommand(name = "word", help = "generate valid word for given automaton") {

    private val automatonFile by argument(help = "name of file with automaton")

    override fun run() {
        val automaton = XMLCodec().decode(File(automatonFile), null) as Automaton
        val word = when(automaton) {
            is FiniteStateAutomaton -> WordGenerator.randomWord(automaton)
            is MealyMachine /* also Moore */ -> WordGenerator.randomWord(automaton)
            is PushdownAutomaton -> WordGenerator.randomWord(automaton)
            else -> TODO("implement word generation for all automatons")
        }
        echo(word)
    }
}