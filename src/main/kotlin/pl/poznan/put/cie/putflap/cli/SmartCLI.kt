package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default

object SmartCLI : CliktCommand(name = "smart", help = "perform tasks from other sections with parameters written in .json file") {

    private val configName by argument(help = "config file to run [default=config.json]")
        .default("config.json")

    override fun run() = Commands.smart(configName)
}