package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice

internal object TestCLI : CliktCommand(name = "test", help = "check of specific characteristics of given automatons and grammars") {
    
    private val type by option("-t", "--type",  help = "type of test to perform")
        .choice(*Array(Types.TestType.values().size) { Types.TestType.values()[it].name.toLowerCase() })
        .convert { Types.TestType.valueOf(it.toUpperCase()) }
        .required()

    private val inputs by argument("input", help = "names of files with structures to test")
        .multiple()

    override fun run() = Commands.test(type, inputs.toTypedArray())
}