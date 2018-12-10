package pl.poznan.put.cie.putflap.cli

import jflap.automata.Automaton
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.pda.PushdownAutomaton
import jflap.file.XMLCodec
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.converter.AutomatonConverter
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.exception.InvalidActionException
import pl.poznan.put.cie.putflap.generator.AutomatonGenerator
import pl.poznan.put.cie.putflap.generator.GrammarGenerator
import pl.poznan.put.cie.putflap.generator.WordGenerator
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonRunner
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonTester
import pl.poznan.put.cie.putflap.report.GenerationReport
import pl.poznan.put.cie.putflap.report.MultipleConversionReport
import pl.poznan.put.cie.putflap.report.MultipleGenerationReport
import pl.poznan.put.cie.putflap.report.Report
import pl.poznan.put.cie.putflap.report.WordsReport
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
        val report = AutomatonRunner.runAutomaton(automaton, words)
        CLI.saveFile(report, "run_report")
    }
    
    fun test(type: Types.TestType, inputs: Array<String>) {
        val structures = Array(inputs.size) { XMLCodec().decode(File(inputs[it]), null) }

        val report: Report = when {
            structures.all { it is Automaton } -> {
                val automatons = Array(structures.size) { structures[it] as Automaton }
                when (type) {
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
            }
            structures.all { it is Grammar } -> {
                val grammars = Array(structures.size) { structures[it] as Grammar }
                when (type) {
                    Types.TestType.AL -> AutomatonTester.getAlphabets(grammars)
                    else -> throw InvalidActionException("Grammars can only be tested for alphabet")
                }
            }
            else -> throw IllegalArgumentException("Tests can only be performed on ")
        }

        CLI.saveFile(report, "test_${type.toString().toLowerCase()}_report")
    }
    
    fun convert(type: Types.ConvertType, json: Boolean, inputs: Array<String>) {
        val structures = Array(inputs.size) { XMLCodec().decode(File(inputs[it]), null) }
        val conversion: Pair<MultipleConversionReport, Array<*>> = when {
            structures.all { it is Automaton } -> {
                val automatons = Array(structures.size) { structures[it] as Automaton }
                when (type) {
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
            }
            structures.all { it is Grammar } -> {
                val grammars = Array(structures.size) { structures[it] as Grammar }
                when (type) {
                    Types.ConvertType.PDA -> TODO("implement conversion from grammar to PDA")
                    Types.ConvertType.FSA -> AutomatonConverter.toFSA(grammars)
                    Types.ConvertType.JSON -> AutomatonConverter.toJSON(grammars)
                    else -> throw InvalidActionException("Grammars can only be converted to PDA, FSA or JSON")
                }
            }
            else -> throw IllegalArgumentException("Tests can only be performed on ")
        }

        if (type == Types.ConvertType.RE && !json) for (regex in conversion.second) println(
            regex
        )
        else CLI.saveFile(
            conversion,
            "converted_${type.name.toLowerCase()}",
            if (type == Types.ConvertType.JSON) true else json
        )
    }

    fun word(multiple: Int, json: Boolean, inputFileName: String) {
        val automaton = XMLCodec().decode(File(inputFileName), null) as Automaton
        val report: WordsReport = when(automaton) {
            is FiniteStateAutomaton -> WordGenerator.randomWords(automaton, multiple)
            is MealyMachine /* also Moore */ -> WordGenerator.randomWords(automaton, multiple)
            is PushdownAutomaton -> WordGenerator.randomWords(automaton, multiple)
            else -> TODO("implement word generation for all automatons")
        }

        if (json) CLI.saveFile(report, "words")
        else for (word in report.words) println(word)
    }
}