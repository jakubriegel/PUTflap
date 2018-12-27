package pl.poznan.put.cie.putflap.generator

import jflap.grammar.Production
import jflap.grammar.reg.RegularGrammar
import pl.poznan.put.cie.putflap.exception.TooManyNonterminalsException
import pl.poznan.put.cie.putflap.report.GenerationReport
import java.util.*

/**
 * Implements generation of random regular grammar
 *
 * @param n number of states
 * @param alphabet alphabet to use (generated structure may but does not have to use all symbols)
 * @param finals number of final states
 */
class GrammarGenerator (
    private val n: Int,
    private val finals: Int,
    private val alphabet: Array<String>
) {

    init {
        if (n > 25) throw TooManyNonterminalsException()
    }

    companion object {
        private const val p = .5
    }

    fun regular(): Pair<GenerationReport, RegularGrammar> {
        val grammar = randomRegular()
        return Pair(GenerationReport(grammar), grammar)
    }

    private fun randomRegular(): RegularGrammar {
        val nonterminals = getNonterminals()
        val productions = mutableSetOf<Production>()

        // set end states
        for (i in (n-finals until n)) productions.add(Production("${nonterminals[i]}", ""))

        // add path
        val unusedNonterminals = LinkedList<Char>()
        var last = productions.random().lhs.single()
        for (i in 1 until n) unusedNonterminals.push(nonterminals[i])
        while (unusedNonterminals.isNotEmpty()) {
            val nonterminal = unusedNonterminals.random()
            unusedNonterminals.remove(nonterminal)
            productions.add(Production("$nonterminal", "${getRandomTerminalProduction()}$last"))
            last = nonterminal
        }

        // add start production
        productions.add(Production("${nonterminals[0]}", "${getRandomTerminalProduction()}$last"))

        // add some random productions
        while (Math.random() > p) productions.add(
            Production(
                "${nonterminals.random()}",
                "${getRandomTerminalProduction()}${nonterminals.random()}"
            ))

        // add productions to the grammar
        val grammar = RegularGrammar()
        grammar.addProductions(productions.sortedBy { it.lhs }.toTypedArray())

        return grammar
    }

    private fun getNonterminals(): Array<Char> {
        return Array(n) { (it + 65).toChar() }
    }

    private fun getRandomTerminalProduction(): String {
        var production = ""
        while (Math.random() > p || production.isEmpty()) production += alphabet.random()
        return production
    }
}