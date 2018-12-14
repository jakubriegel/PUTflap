package pl.poznan.put.cie.putflap.cli.smart.parameters

import pl.poznan.put.cie.putflap.cli.Types

data class RandomParameters (
    val type: Types.RandomType,
    val n: Int,
    val finals: Int,
    val multiple: Int,
    val json: Boolean,
    val alphabet: Array<String>
) : SmartParameters() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RandomParameters

        if (type != other.type) return false
        if (n != other.n) return false
        if (finals != other.finals) return false
        if (multiple != other.multiple) return false
        if (!alphabet.contentEquals(other.alphabet)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + n
        result = 31 * result + finals
        result = 31 * result + multiple
        result = 31 * result + alphabet.contentHashCode()
        return result
    }
}