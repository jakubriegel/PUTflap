package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
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
import pl.poznan.put.cie.putflap.report.ConversionReport
import java.io.File
import java.io.Serializable

internal object ConvertCLI : CliktCommand(name = "convert", help = "perform various JFLAP convert tasks") {

    private enum class Type {
        DFA, MINI, GR, RE, PDA, FSA, JSON
    }

    private val type by option("-t", "--type",  help = "type of conversion to perform")
        .choice(*Array(Type.values().size) { Type.values()[it].name.toLowerCase() })
        .convert { Type.valueOf(it.toUpperCase()) }
        .required()

    private val json by option("-j", "--json", help = "write answer as json")
        .flag(default = false)

    private val inputFile by argument("inputFile", help = "name of inputFile file")

    override fun run() {
        val source = XMLCodec().decode(File(inputFile), null)
        val conversion: Pair<ConversionReport, Serializable> = when (type) {
            Type.DFA -> AutomatonConverter.toDeterministicFSA(source as? FiniteStateAutomaton
                ?: throw IncompatibleAutomatonException("Only FSA can be converted to deterministic FSA"))

            Type.MINI -> AutomatonConverter.toMinimalFSA(source as? FiniteStateAutomaton
                ?: throw IncompatibleAutomatonException("Only deterministic FSA can be minimized"))

            Type.GR -> AutomatonConverter.toGrammar(source as? Automaton
                ?: throw IncompatibleAutomatonException("Only automatons can be converted to grammar"))

            Type.RE -> AutomatonConverter.toRegularExpression(source as? FiniteStateAutomaton
                ?: throw IncompatibleAutomatonException("Only FSA can be converted to Regular Expression"))

            Type.PDA -> TODO("implement conversion from grammar to PDA")

            Type.FSA -> AutomatonConverter.toFSA(source as? Grammar
                ?: throw IncompatibleAutomatonException("Only regular, right linear grammar can be converted to FSA"))

            Type.JSON -> when (source) {
                is Automaton -> AutomatonConverter.toJSON(source)
                is Grammar -> AutomatonConverter.toJSON(source)
                else -> throw IncompatibleAutomatonException("Only automatons and grammars can be converted to JSON")
            }
        }

        if (type == Type.RE) echo(conversion.second)
        else CLI.saveFile(conversion, "converted_${type.name.toLowerCase()}", json)
    }



}