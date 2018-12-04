package pl.poznan.put.cie.putflap.report

class AlphabetReport (
    val inAlphabet: Array<String>,
    val outAlphabet: Array<String>? = null
) : Report() {
    override fun toInfoText(): String {
        return "in  alphabet: ${ run {
            var abc = ""
            inAlphabet.forEach { abc += "$it " }
            abc
        }}\nout alphabet: ${ run {
            var abc = ""
            outAlphabet?.forEach { abc += "$it " }
            abc
        }}"
    }
}