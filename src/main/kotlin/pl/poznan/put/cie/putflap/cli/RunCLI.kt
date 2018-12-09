package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import jflap.automata.Automaton
import jflap.file.XMLCodec
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonRunner
import java.io.File

internal object RunCLI : CliktCommand(name = "run", help = "run given automaton for given value") {

    private val inputFile by option("-i", "--input", help = "name of value file")
        .required()

    private val words by argument(help = "value to validate")
        .multiple()

    override fun run() {
        val automaton = XMLCodec().decode(File(inputFile), null) as Automaton
        val report = AutomatonRunner.runAutomaton(automaton, words.toTypedArray())
        CLI.saveFile(report, "run_report")
    }

}