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
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonTester
import java.io.File

internal object TestCLI : CliktCommand(name = "test", help = "check specific characteristics of given automaton") {

    private enum class Type {
        NDET, EQ, AL
    }

    private val type by option("-t", "--type",  help = "type of test")
        .choice(*Array(Type.values().size) { Type.values()[it].name.toLowerCase() })
        .convert { Type.valueOf(it.toUpperCase()) }
        .required()

    private val inputFile by argument("input", help = "name of input files")
        .multiple()

    override fun run() {
        val testedItem = XMLCodec().decode(File(inputFile[0]), null)

        val report = when (type) {
            Type.NDET -> AutomatonTester.checkNondeterminism(testedItem as? Automaton
                ?: throw IncompatibleAutomatonException("Only automatons can be checked for non determinism"))
            Type.EQ -> {
                if (inputFile.size > 1) {
                    val automaton2 =  XMLCodec().decode(File(inputFile[1]), null)
                    AutomatonTester.checkEqualityOfFSAs(
                        testedItem as? FiniteStateAutomaton
                            ?: throw IncompatibleAutomatonException("Two FSAs expected"),
                        automaton2 as? FiniteStateAutomaton
                            ?: throw IncompatibleAutomatonException("Two FSAs expected"))
                }
                else throw IllegalArgumentException("Two FSAs expected")
            }
            Type.AL -> when (testedItem) {
                is Automaton -> AutomatonTester.getAlphabet(testedItem)
                is Grammar -> AutomatonTester.getAlphabet(testedItem)
                else -> throw IncompatibleAutomatonException("Only automatons and grammars can be tested for alphabet")
            }
        }

        CLI.saveFile(report, "test_report")
    }
}