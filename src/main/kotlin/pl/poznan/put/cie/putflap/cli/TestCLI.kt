package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import jflap.automata.Automaton
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.file.XMLCodec
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.exception.InvalidActionException
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonTester
import pl.poznan.put.cie.putflap.report.Report
import java.io.File

internal object TestCLI : CliktCommand(name = "test", help = "check specific characteristics of given automaton") {

    private enum class Type {
        NDET, EQ, AL
    }

    private val type by option("-t", "--type",  help = "type of test")
        .choice(*Array(Type.values().size) { Type.values()[it].name.toLowerCase() })
        .convert { Type.valueOf(it.toUpperCase()) }
        .required()

    val multiple by option("-m", "--multiple", help = "generate many structures")
        .int()
        .default(1)
        .validate { require(it > 0) { "number of structures must be greater than zero" } }

    private val inputs by argument("input", help = "names of input files")
        .multiple()

    override fun run() {
        val structures = Array(inputs.size) { XMLCodec().decode(File(inputs[it]), null) }

        val report: Report = when {
            structures.all { it is Automaton } -> {
                val automatons = Array(structures.size) { structures[it] as Automaton }
                when (type) {
                    Type.NDET -> AutomatonTester.checkNondeterminism(automatons)
                    Type.EQ -> {
                        val fsa = automatons.filterIsInstance<FiniteStateAutomaton>().toTypedArray()
                        if (fsa.size == automatons.size) {
                            if (automatons.size > 1) AutomatonTester.checkEquivalenceOfManyFSAs(fsa)
                            else throw IllegalArgumentException("More than one FSA expected")
                        }
                        else throw IncompatibleAutomatonException("Only FSAs can be tested for equivalence")
                    }
                    Type.AL -> AutomatonTester.getAlphabets(automatons)
                }
            }
            structures.all { it is Grammar } -> {
                val grammars = Array(structures.size) { structures[it] as Grammar }
                when (type) {
                    Type.AL -> AutomatonTester.getAlphabets(grammars)
                    else -> throw InvalidActionException("Grammars can only be tested for alphabet")
                }
            }
            else -> throw IllegalArgumentException("Tests can only be performed on ")
        }

        CLI.saveFile(report, "test_${type.toString().toLowerCase()}_report")
    }
}