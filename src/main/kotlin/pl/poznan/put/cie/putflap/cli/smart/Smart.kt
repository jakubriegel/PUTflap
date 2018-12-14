package pl.poznan.put.cie.putflap.cli.smart

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import pl.poznan.put.cie.putflap.cli.Commands
import pl.poznan.put.cie.putflap.cli.Types
import pl.poznan.put.cie.putflap.cli.smart.parameters.ConvertParameters
import pl.poznan.put.cie.putflap.cli.smart.parameters.RandomParameters
import pl.poznan.put.cie.putflap.cli.smart.parameters.RunParameters
import pl.poznan.put.cie.putflap.cli.smart.parameters.SmartParameters
import pl.poznan.put.cie.putflap.cli.smart.parameters.TestParameters
import pl.poznan.put.cie.putflap.cli.smart.parameters.WordParameters
import java.io.File

/**
 * Implementation of smart tasks
 *
 * @param configName name of file with [configuration][SmartConfig] to run
 */
class Smart (
    configName: String
) {

    /**
     * JSON mapper
     */
    private val mapper = ObjectMapper()
    /**
     * [Configuration][SmartConfig] to run
     */
    private val config: JsonNode

    init {
        mapper.registerModule(KotlinModule())

        val configJSON = File(configName).readText()
        config = mapper.readTree(configJSON)
    }

    /**
     * Performs smart tasks
     */
    fun command() {
        when (getInstruction()) {
            Types.InstructionType.RANDOM -> {
                val parameters = getParameters<RandomParameters>()
                Commands.random(
                    parameters.type,
                    parameters.n,
                    parameters.finals,
                    parameters.multiple,
                    parameters.json,
                    parameters.alphabet
                )
            }
            Types.InstructionType.RUN -> {
                val parameters =  getParameters<RunParameters>()
                for (i in parameters.input.indices)
                    Commands.run(
                        parameters.input[i],
                        parameters.words,
                        i+1
                    )
            }
            Types.InstructionType.TEST -> {
                val parameters = getParameters<TestParameters>()
                Commands.test(
                    parameters.type,
                    parameters.inputs
                )
            }
            Types.InstructionType.CONVERT -> {
                val parameters = getParameters<ConvertParameters>()
                Commands.convert(
                    parameters.type,
                    parameters.json,
                    parameters.input
                )
            }
            Types.InstructionType.WORD -> {
                val parameters = getParameters<WordParameters>()
                Commands.word(
                    parameters.multiple,
                    parameters.inputs
                )
            }
        }

    }

    /**
     * Retrieves instruction from [config]
     */
    private fun getInstruction(): Types.InstructionType = Types.InstructionType.valueOf(
        config.get("instruction").textValue().toUpperCase()
    )

    /**
     * Retrieves [parameters][SmartParameters] from [config]
     *
     * @param T class of [parameters][SmartParameters] to retrieve
     */
    private inline fun <reified T : SmartParameters> getParameters(): T = mapper.readValue(
        config.get("parameters").toString()
    )
}