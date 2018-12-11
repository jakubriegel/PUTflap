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

internal object RandomCLI : CliktCommand(name = "random", help = "generation of random automatons and grammars") {

    private val type by option("-t", "--type",  help = "type of structure to generate")
        .choice(*Array(Types.RandomType.values().size) { Types.RandomType.values()[it].name.toLowerCase() })
        .convert { Types.RandomType.valueOf(it.toUpperCase()) }
        .required()

    private val n by option("-n", help = "number of states")
        .int()
        .required()
        .validate { require(it > 0) { "number of states must be greater than zero" } }

    private val finals by option("-f", "--finals", help = "number of final states[default=1]")
        .int()
        .default(1)
        .validate { require(it > 0) { "number of final states must be greater than zero" } }

    private val multiple by option("-m", "--multiple", help = "number of structures to generate [default=1]")
        .int()
        .default(1)
        .validate { require(it > 0) { "number of structures must be greater than zero" } }

    private val json by option("-j", "--json", help = "write answer as json file")
        .flag(default = false)

    private val alphabet by argument(help = "alphabet to generate automaton on")
        .multiple()
        .validate { require(it.isNotEmpty()) { "alphabet must be supplied"} }


    override fun run() = Commands.random(type, n, finals, multiple, json, alphabet.toTypedArray())
}