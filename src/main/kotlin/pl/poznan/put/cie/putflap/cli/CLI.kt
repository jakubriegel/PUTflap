package pl.poznan.put.cie.putflap.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import jflap.file.XMLCodec
import pl.poznan.put.cie.putflap.report.Report
import java.io.File
import java.io.Serializable

object CLI : CliktCommand(
    name = "putflap",
    help = "PUTflap is an extension of JFLAP, which makes JFLAP features available from the command line. It can report results as JSON and is capable of performing operations on many arguments at once." +
            "\n\n Detailed help is available with putflap COMMAND -h" +
            "\n\nFor source and documentation check: github.com/jakubriegel/PUTflap" +
            "\n\nTHIS SOFTWARE IS PROVIDED ''AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE."
) {
    override fun run() = Unit

    private val subs = listOf(RandomCLI, RunCLI, TestCLI, WordCLI, ConvertCLI)
    fun init(args: Array<String>) = subcommands(subs).main(args)


    internal fun saveFile(result: Pair<Report, Serializable>, filename: String, json: Boolean) {
        val file = File("$filename${if (json) ".json" else ".jff"}")
        if (json) file.writeText(Report.getJSON(result.first))
        else {
            if (result.second is Array<*>) {
                for (i in (result.second as Array<*>).indices)
                    XMLCodec().encode(
                        (result.second as Array<*>)[i] as Serializable,
                        File("${filename}_${i+1}.jff"),
                        null
                    )
            }
            else XMLCodec().encode(result.second, file, null)
        }
    }

    internal fun saveFile(report: Report, filename: String) {
        val file = File("$filename.json")
        file.writeText(Report.getJSON(report))
    }
}
