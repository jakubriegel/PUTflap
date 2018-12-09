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

internal object ConvertCLI : CliktCommand(name = "convert", help = "perform various JFLAP conversion tasks") {

    private enum class Type {
        DFA, MINI, GR, RE, PDA, FSA, JSON
    }

    private val type by option("-t", "--type",  help = "type of conversion to perform")
        .choice(*Array(Type.values().size) { Type.values()[it].name.toLowerCase() })
        .convert { Type.valueOf(it.toUpperCase()) }
        .required()

    private val json by option("-j", "--json", help = "write answer as json")
        .flag(default = false)

    private val inputs by argument("inputs", help = "names of input files")
        .multiple()

    override fun run() {
        val structures = Array(inputs.size) { XMLCodec().decode(File(inputs[it]), null) }
        val conversion: Pair<MultipleConversionReport, Array<*>> = when {
            structures.all { it is Automaton } -> {
                val automatons = Array(structures.size) { structures[it] as Automaton }
                when (type) {
                    Type.GR -> AutomatonConverter.toGrammar(automatons)
                    Type.JSON ->  AutomatonConverter.toJSON(automatons)
                    else -> when {
                        automatons.all { it is FiniteStateAutomaton } -> {
                            val fsa = automatons.filterIsInstance<FiniteStateAutomaton>().toTypedArray()
                            when (type) {
                                Type.DFA -> AutomatonConverter.toDeterministicFSA(fsa)
                                Type.MINI -> AutomatonConverter.toMinimalFSA(fsa)
                                Type.RE -> AutomatonConverter.toRegularExpression(fsa)
                                else -> throw Exception()
                            }
                        }
                        else -> throw IncompatibleAutomatonException()
                    }
                }
            }
            structures.all { it is Grammar } -> {
                val grammars = Array(structures.size) { structures[it] as Grammar }
                when (type) {
                    Type.PDA -> TODO("implement conversion from grammar to PDA")
                    Type.FSA -> AutomatonConverter.toFSA(grammars)
                    Type.JSON -> AutomatonConverter.toJSON(grammars)
                    else -> throw InvalidActionException("Grammars can only be converted to PDA, FSA or JSON")
                }
            }
            else -> throw IllegalArgumentException("Tests can only be performed on ")
        }

        if (type == Type.RE) for (regex in conversion.second) echo(regex)
        else CLI.saveFile(conversion, "converted_${type.name.toLowerCase()}", json)
    }



}