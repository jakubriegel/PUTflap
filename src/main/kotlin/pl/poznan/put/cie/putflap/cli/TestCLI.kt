package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import jflap.automata.Automaton
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.file.XMLCodec
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.exception.InvalidActionException
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonTester
import pl.poznan.put.cie.putflap.report.Report
import java.io.File

internal object TestCLI : CliktCommand(name = "test", help = "check of specific characteristics of given automatons and grammars") {
    
    private val type by option("-t", "--type",  help = "type of test to perform")
        .choice(*Array(Types.TestType.values().size) { Types.TestType.values()[it].name.toLowerCase() })
        .convert { Types.TestType.valueOf(it.toUpperCase()) }
        .required()

    private val inputs by argument("input", help = "names of files with structures to test")
        .multiple()

    override fun run() = Commands.test(type, inputs.toTypedArray())
}