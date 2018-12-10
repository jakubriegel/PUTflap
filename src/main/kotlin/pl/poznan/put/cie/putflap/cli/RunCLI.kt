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

internal object RunCLI : CliktCommand(name = "run", help = "running automatons for given inputs") {

    private val inputFile by option("-i", "--input", help = "name of file with automaton to run")
        .required()

    private val words by argument(help = "words to run given automaton on")
        .multiple()

    override fun run() = Commands.run(inputFile, words.toTypedArray())
}