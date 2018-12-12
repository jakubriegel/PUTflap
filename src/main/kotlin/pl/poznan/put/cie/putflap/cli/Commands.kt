package pl.poznan.put.cie.putflap.cli

import jflap.automata.Automaton
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.file.XMLCodec
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.cli.smart.Smart
import pl.poznan.put.cie.putflap.converter.AutomatonConverter
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.exception.InvalidActionException
import pl.poznan.put.cie.putflap.generator.AutomatonGenerator
import pl.poznan.put.cie.putflap.generator.GrammarGenerator
import pl.poznan.put.cie.putflap.generator.WordGenerator
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonCreator
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonRunner
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonTester
import pl.poznan.put.cie.putflap.jflapextensions.automaton.GrammarCreator
import pl.poznan.put.cie.putflap.report.GenerationReport
import pl.poznan.put.cie.putflap.report.MultipleConversionReport
import pl.poznan.put.cie.putflap.report.MultipleGenerationReport
import pl.poznan.put.cie.putflap.report.MultipleWordsReport
import pl.poznan.put.cie.putflap.report.Report
import pl.poznan.put.cie.putflap.report.WordsReport
import pl.poznan.put.cie.putflap.report.structure.StructureReport
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport
import pl.poznan.put.cie.putflap.report.structure.grammar.GrammarReport
import java.io.File
import java.io.Serializable

object Commands {
       
    fun random(type: Types.RandomType, n: Int, finals: Int, multiple: Int, json: Boolean, alphabet: Array<String>) {
        val result: Pair<Report, Serializable> = if (multiple > 1) {
            val results = mutableListOf<Pair<GenerationReport, Serializable>>()
            for (i in 0 until multiple) {
                results.add(when (type) {
                    Types.RandomType.FSA -> AutomatonGenerator(n, alphabet, finalStates = finals).randomFSA()
                    Types.RandomType.MEALY -> AutomatonGenerator(n, alphabet, outputAlphabet = alphabet).randomMealy()
                    Types.RandomType.MOORE -> AutomatonGenerator(n, alphabet, outputAlphabet = alphabet).randomMoore()
                    Types.RandomType.REGR -> GrammarGenerator(n, finals, alphabet).randomRegular()
                })
            }

            Pair(
                MultipleGenerationReport(
                    type.toString(), n, finals, alphabet, Array(
                        multiple
                    ) { results[it].first }),
                Array(multiple) { results[it].second }
            )
        }
        else when (type) {
            Types.RandomType.FSA -> AutomatonGenerator(n, alphabet, finalStates = finals).randomFSA()
            Types.RandomType.MEALY -> AutomatonGenerator(n, alphabet, outputAlphabet = alphabet).randomMealy()
            Types.RandomType.MOORE -> AutomatonGenerator(n, alphabet, outputAlphabet = alphabet).randomMoore()
            Types.RandomType.REGR -> GrammarGenerator(n, finals, alphabet).randomRegular()
        }

        CLI.saveFile(result, "new_${type.name.toLowerCase()}", json)
    }

    fun run(automatonFileName: String, words: Array<String>) {
        val automaton = XMLCodec().decode(File(automatonFileName), null) as Automaton
        run(automaton, words)
    }

    fun run(automatonReport: AutomatonReport, words: Array<String>, n: Int = -1) {
        val automaton = AutomatonCreator.fromReport(automatonReport)
        run(automaton, words, n)
    }

    private fun run(automaton: Automaton, words: Array<String>, n: Int = -1) {
        val report = AutomatonRunner.runAutomaton(automaton, words)
        CLI.saveFile(report, "run_report${if (n != -1) "_$n" else ""}")
    }

    fun test(type: Types.TestType, inputs: Array<StructureReport>) {
        when {
            inputs.all { it is AutomatonReport } -> test(type, Array(inputs.size) { inputs[it] as AutomatonReport })
            inputs.all { it is GrammarReport } -> test(type, Array(inputs.size) { inputs[it] as GrammarReport })
            else -> throw IllegalArgumentException("Tests can only be performed on automatons or grammars")
        }
    }

    fun test(type: Types.TestType, inputs: Array<AutomatonReport>) {
        val automatons = automatonsFromReports(inputs)
        test(type, automatons)
    }

    fun test(type: Types.TestType, inputs: Array<GrammarReport>) {
        val grammars = grammarsFromReports(inputs)
        test(type, grammars)
    }

    fun test(type: Types.TestType, inputs: Array<String>) {
        val structures = Array(inputs.size) { XMLCodec().decode(File(inputs[it]), null) }

        when {
            structures.all { it is Automaton } -> test(type, Array(structures.size) { structures[it] as Automaton })
            structures.all { it is Grammar } -> test(type, Array(structures.size) { structures[it] as Grammar })
            else -> throw IllegalArgumentException("Tests can only be performed on automatons or grammars")
        }
    }

    private fun test(type: Types.TestType, automatons: Array<Automaton>) {
        val report: Report = when (type) {
            Types.TestType.NDET -> AutomatonTester.checkNondeterminism(automatons)
            Types.TestType.EQ -> {
                val fsa = automatons.filterIsInstance<FiniteStateAutomaton>().toTypedArray()
                if (fsa.size == automatons.size) {
                    if (automatons.size > 1) AutomatonTester.checkEquivalenceOfManyFSAs(fsa)
                    else throw IllegalArgumentException("More than one FSA expected")
                }
                else throw IncompatibleAutomatonException("Only FSAs can be tested for equivalence")
            }
            Types.TestType.AL -> AutomatonTester.getAlphabets(automatons)
        }

        CLI.saveFile(report, "test_${type.toString().toLowerCase()}_report")
    }

    private fun test(type: Types.TestType, grammars: Array<Grammar>) {
        val report: Report = when (type) {
            Types.TestType.AL -> AutomatonTester.getAlphabets(grammars)
            else -> throw InvalidActionException("Grammars can only be tested for alphabet")
        }

        CLI.saveFile(report, "test_${type.toString().toLowerCase()}_report")
    }

    fun convert(type: Types.ConvertType, json: Boolean, inputs: Array<StructureReport>) {
        when {
            inputs.all { it is AutomatonReport } -> convert(type, json, Array(inputs.size) { inputs[it] as AutomatonReport })
            inputs.all { it is GrammarReport } -> convert(type, json, Array(inputs.size) { inputs[it] as GrammarReport })
            else -> throw IllegalArgumentException("Convert can only be performed on automatons or grammars")
        }
    }

    fun convert(type: Types.ConvertType, json: Boolean, inputs: Array<AutomatonReport>) {
        val automatons = automatonsFromReports(inputs)
        convert(type, json, automatons)
    }

    fun convert(type: Types.ConvertType, json: Boolean, inputs: Array<GrammarReport>) {
        val grammars = grammarsFromReports(inputs)
        convert(type, json, grammars)
    }

    fun convert(type: Types.ConvertType, json: Boolean, inputs: Array<String>) {
        val structures = Array(inputs.size) { XMLCodec().decode(File(inputs[it]), null) }

        when {
            structures.all { it is Automaton } -> convert(type, json, Array(structures.size) { structures[it] as Automaton })
            structures.all { it is Grammar } -> convert(type, json, Array(structures.size) { structures[it] as Grammar })
            else -> throw IllegalArgumentException("Tests can only be performed on automatons or grammars")
        }

    }

    private fun convert(type: Types.ConvertType, json: Boolean, grammars: Array<Grammar>) {
        val conversion: Pair<MultipleConversionReport, Array<*>> = when (type) {
            Types.ConvertType.PDA -> TODO("implement conversion from grammar to PDA")
            Types.ConvertType.FSA -> AutomatonConverter.toFSA(grammars)
            Types.ConvertType.JSON -> AutomatonConverter.toJSON(grammars)
            else -> throw InvalidActionException("Grammars can only be converted to PDA, FSA or JSON")
        }

        if (type == Types.ConvertType.RE && !json) for (regex in conversion.second) println(regex)
        else CLI.saveFile(
            conversion,
            "converted_${type.name.toLowerCase()}",
            if (type == Types.ConvertType.JSON) true else json
        )
    }

    private fun convert(type: Types.ConvertType, json: Boolean, automatons: Array<Automaton>) {
        val conversion: Pair<MultipleConversionReport, Array<*>> = when  (type) {
            Types.ConvertType.GR -> AutomatonConverter.toGrammar(automatons)
            Types.ConvertType.JSON ->  AutomatonConverter.toJSON(automatons)
            else -> when {
                automatons.all { it is FiniteStateAutomaton } -> {
                    val fsa = automatons.filterIsInstance<FiniteStateAutomaton>().toTypedArray()
                    when (type) {
                        Types.ConvertType.DFA -> AutomatonConverter.toDeterministicFSA(fsa)
                        Types.ConvertType.MINI -> AutomatonConverter.toMinimalFSA(fsa)
                        Types.ConvertType.RE -> AutomatonConverter.toRegularExpression(fsa)
                        else -> throw Exception()
                    }
                }
                else -> throw IncompatibleAutomatonException()
            }
        }

        CLI.saveFile(
            conversion,
            "converted_${type.name.toLowerCase()}",
            if (type == Types.ConvertType.JSON) true else json
        )
    }

    fun word(multiple: Int, inputs: Array<AutomatonReport>) {
        val automatons = Array(inputs.size) { AutomatonCreator.fromReport(inputs[it]) }
        word(multiple, automatons)
    }

    fun word(multiple: Int, json: Boolean, inputFileName: String) {
        val automaton = XMLCodec().decode(File(inputFileName), null) as Automaton
        word(multiple, json, automaton)
    }

    private fun word(multiple: Int, json: Boolean, automaton: Automaton) {
        val report = WordGenerator.randomWords(automaton, multiple)

        if (json) CLI.saveFile(report, "words")
        else for (word in report.words) println(word)
    }

    private fun word(multiple: Int, automatons: Array<Automaton>) {
        val reports: Array<WordsReport> = Array(automatons.size) {
            WordGenerator.randomWords(automatons[it], multiple)
        }

        CLI.saveFile(MultipleWordsReport(reports), "words")
    }

    fun smart(configName: String) = Smart(configName).command()

    private fun automatonsFromReports(inputs: Array<AutomatonReport>) = Array(inputs.size) { AutomatonCreator.fromReport(inputs[it]) }

    private fun grammarsFromReports(inputs: Array<GrammarReport>) = Array(inputs.size) { GrammarCreator.fromReport(inputs[it]) }

}