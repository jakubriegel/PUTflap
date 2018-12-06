package pl.poznan.put.cie.putflap.report

class ErrorReport private constructor(
    val code: Int,
    val message: String
) : Report() {

    companion object {
        enum class Error {
            NO_INITIAL_STATE,
            NO_FINAL_STATE,
            NON_DETERMINISM
        }

        fun generate(error: Error): ErrorReport {
            return ErrorReport(
                error.ordinal,
                error.name
            )
        }
    }

    override fun toString(): String {
        return "ErrorReport(code=$code, message='$message')"
    }

}