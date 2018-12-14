package pl.poznan.put.cie.putflap.cli.smart

import pl.poznan.put.cie.putflap.cli.Types
import pl.poznan.put.cie.putflap.cli.smart.parameters.SmartParameters

/**
 * Configuration for smart tasks
 *
 * @see Smart
 */
data class SmartConfig (
    val instruction: Types.InstructionType,
    val parameters: SmartParameters
)