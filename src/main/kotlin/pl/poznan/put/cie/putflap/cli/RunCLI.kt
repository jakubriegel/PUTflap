package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import jflap.automata.Automaton
import jflap.file.XMLCodec
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonRunner
import java.io.File

internal object RunCLI : CliktCommand(name = "run", help = "run given automaton for given value") {

    private val inputFile by option("-i", "--input", help = "name of value file")
        .required()

    val multiple by option("-m", "--multiple", help = "generate many structures")
        .int()
        .default(1)
        .validate { require(it > 0) { "number of structures must be greater than zero" } }

    private val words by argument(help = "value to validate")
        .multiple()

    override fun run() {
        val automaton = XMLCodec().decode(File(inputFile), null) as Automaton
        val report = AutomatonRunner.runAutomaton(automaton, words.toTypedArray())
        CLI.saveFile(report, "run_report")
    }

}