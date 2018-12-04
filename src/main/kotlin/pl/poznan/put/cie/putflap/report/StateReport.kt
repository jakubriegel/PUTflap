package pl.poznan.put.cie.putflap.report

class StateReport (
    val id: Int,
    val name: String,
    val label: String,
    val x: Int,
    val y: Int
) : Report() {
    override fun toInfoText(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}