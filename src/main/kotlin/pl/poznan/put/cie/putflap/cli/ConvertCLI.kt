package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import jflap.automata.Automaton
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.file.XMLCodec
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.converter.AutomatonConverter
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.exception.InvalidActionException
import pl.poznan.put.cie.putflap.report.MultipleConversionReport
import java.io.File

internal object ConvertCLI : CliktCommand(name = "convert", help = "perform various conversion tasks on automaton and grammars") {

    private val type by option("-t", "--type",  help = "type of conversion to perform")
        .choice(*Array(Types.ConvertType.values().size) { Types.ConvertType.values()[it].name.toLowerCase() })
        .convert { Types.ConvertType.valueOf(it.toUpperCase()) }
        .required()

    private val json by option("-j", "--json", help = "write answer as json")
        .flag(default = false)

    private val inputs by argument("inputs", help = "names of files with structures to convert")
        .multiple()

    override fun run() = Commands.convert(type, json, inputs.toTypedArray())



}