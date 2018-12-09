package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import pl.poznan.put.cie.putflap.generator.AutomatonGenerator
import pl.poznan.put.cie.putflap.generator.GrammarGenerator
import pl.poznan.put.cie.putflap.report.GenerationReport
import pl.poznan.put.cie.putflap.report.MultipleGenerationReport
import pl.poznan.put.cie.putflap.report.Report
import java.io.Serializable

internal object RandomCLI : CliktCommand(name = "random", help = "generate random automaton") {

    private enum class Type {
        FSA, MOORE, MEALY, REGR
    }

    private val type by option("-t", "--type",  help = "type of automaton to generate")
        .choice(*Array(Type.values().size) { Type.values()[it].name.toLowerCase() })
        .convert { Type.valueOf(it.toUpperCase()) }
        .required()

    private val n by option("-n", help = "number of states")
        .int()
        .required()

    private val finals by option("-f", "--finals", help = "number of final states[default=1]")
        .int()
        .default(1)

    val output by option("-o", "--output", help = "name of output file")

    val multiple by option("-m", "--multiple", help = "generate many structures")
        .int()
        .default(1)
        .validate { require(it > 0) { "number of structures must be greater than zero" } }

    private val json by option("-j", "--json", help = "write answer as json")
        .flag(default = false)

    private val alphabet by argument(help = "alphabet to generate automaton on") // TODO: add possibility to specify output alphabet
        .multiple()
        .validate { require(it.isNotEmpty()) { "alphabet must be supplied"} }


    override fun run() {

        val result: Pair<Report, Serializable> = if (multiple > 1) {
            val results = mutableListOf<Pair<GenerationReport, Serializable>>()
            for (i in 0 until multiple) {
                results.add(when (type) {
                    Type.FSA -> AutomatonGenerator(n, alphabet.toTypedArray(), finalStates = finals).randomFSA()
                    Type.MEALY -> AutomatonGenerator(n, alphabet.toTypedArray(), outputAlphabet = alphabet.toTypedArray()).randomMealy()
                    Type.MOORE -> AutomatonGenerator(n, alphabet.toTypedArray(), outputAlphabet = alphabet.toTypedArray()).randomMoore()
                    Type.REGR -> GrammarGenerator(n, finals, alphabet.toTypedArray()).randomRegular()
                })
            }

            Pair(
                MultipleGenerationReport(type.toString(), n, finals, alphabet.toTypedArray(), Array(multiple) { results[it].first }),
                Array(multiple) { results[it].second }
            )
        }
        else when (type) {
            Type.FSA -> AutomatonGenerator(n, alphabet.toTypedArray(), finalStates = finals).randomFSA()
            Type.MEALY -> AutomatonGenerator(n, alphabet.toTypedArray(), outputAlphabet = alphabet.toTypedArray()).randomMealy()
            Type.MOORE -> AutomatonGenerator(n, alphabet.toTypedArray(), outputAlphabet = alphabet.toTypedArray()).randomMoore()
            Type.REGR -> GrammarGenerator(n, finals, alphabet.toTypedArray()).randomRegular()
        }

        CLI.saveFile(result, output ?: "new_${type.name.toLowerCase()}", json)
    }
}