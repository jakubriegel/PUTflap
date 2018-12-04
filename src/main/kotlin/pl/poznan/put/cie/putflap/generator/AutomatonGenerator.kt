package pl.poznan.put.cie.putflap.generator

import jflap.automata.Automaton
import jflap.automata.State
import jflap.automata.Transition
import jflap.automata.fsa.FSATransition
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MealyTransition
import jflap.automata.mealy.MooreMachine
import jflap.automata.mealy.MooreTransition
import pl.poznan.put.cie.putflap.jflapextensions.automaton.StateType
import pl.poznan.put.cie.putflap.report.GenerationReport
import java.awt.Point

class AutomatonGenerator(
    private val n: Int,
    private val alphabet: Array<String>,
    finalStates: Int = 0,
    private val outputAlphabet: Array<String> = emptyArray()
) {

    private val p = .7

    private var transitions = 0
    private val states = mutableListOf<Pair<Int, StateType>>()
    private val matrix = Array(n) { Array(alphabet.size) { -1 } }

    init {
        states.add(0, Pair(0, StateType.INITIAL))
        for(i in 1 until (n - finalStates)) states.add(i, Pair(i, StateType.STATE))
        for(i in (n - finalStates) until n) states.add(i, Pair(i, StateType.FINAL))
    }

    /**
     * Generates random Finite State Automaton
     */
    fun randomFSA(): Pair<GenerationReport, FiniteStateAutomaton> {
        randomUndirectedConnectedGraph()
        oneInToEachNonInitialState()
        oneOutFromEachNonFinalState()
        randomTransitions()

        val automaton = FiniteStateAutomaton()
        val addedStates = addStates(automaton)

        addTransitions(automaton, addedStates) {
                inState, outState, letter -> FSATransition(inState, outState, letter)
        }

        return Pair(GenerationReport(-1, automaton), automaton)
    }

    /**
     * Generates random Mealy Machine
     */
    fun randomMealy():  Pair<GenerationReport, MealyMachine> {
        randomUndirectedConnectedGraph()
        oneInToEachNonInitialState()
        randomTransitions()

        val automaton = MealyMachine()
        val addedStates = addStates(automaton)

        addTransitions(automaton, addedStates) {
                inState, outState, letter -> MealyTransition(inState, outState, letter, getRandomOutputLetter())
        }

        return Pair(GenerationReport(-1, automaton), automaton)
    }

    /**
     * Generates random Moore Machine
     */
    fun randomMoore():  Pair<GenerationReport, MooreMachine> {
        randomUndirectedConnectedGraph()
        oneInToEachNonInitialState()
        randomTransitions()

        val automaton = MooreMachine()
        val addedStates = addStates(automaton)

        for(state in addedStates) automaton.setOutput(state, getRandomOutputLetter())

        addTransitions(automaton, addedStates) {
                inState, outState, letter -> MooreTransition(inState, outState, letter)
        }

        return Pair(GenerationReport(-1, automaton), automaton)
    }

    /**
     * Generates random connected graph from scratch, whose undirected sub-graph is connected
     */
    private fun randomUndirectedConnectedGraph() {
        val notConnectedStates = states.toMutableList()
        val stack = mutableListOf(notConnectedStates.removeAt(0))

        while (notConnectedStates.size > 0) {
            // check for step back
            if ((Math.random() < p && stack.size > 1)) stack.removeAt(0)

            val letter = chooseLetter(stack[0].first)

            val next = notConnectedStates.removeAt(Math.floor(Math.random() * notConnectedStates.size).toInt())
            matrix[stack[0].first][letter] = next.first
            transitions++
            stack.add(0, next)
        }
    }

    /**
     * Generates at least one IN to each non-initial state
     */
    private fun oneInToEachNonInitialState() {
        for(state in states) if(state.second != StateType.INITIAL) {
            val ins = run {
                var count = 0
                for(i in 0 until states.size)
                    count += matrix[i].count { it == state.first }
                count
            }

            if(ins == 0) {
                val outState = getRandomState(states.size, state.first)
                val letter = chooseLetter(outState)
                matrix[outState][letter] = state.first
                transitions++
            }
        }
    }

    /**
     * Generates at least one OUT from each non-final state
     */
    private fun oneOutFromEachNonFinalState() {
        for(state in states) if(state.second != StateType.FINAL) {
            val outs = matrix[state.first].count { it != -1 }
            if(outs == 0) {
                val letter = chooseLetter(state.first)
                val inState = getRandomState(states.size, state.first)
                matrix[state.first][letter] = inState
                transitions++
            }
        }
    }

    /**
     *  Generates some random transitions.
     *  The number of transitions in the graph is assured not to exceed maximal possible
     */
    private fun randomTransitions() {
        val maxTransitions = states.size * (states.size - 1)
        while (Math.random() < (p + .1) && transitions < maxTransitions) {
            val outState: Int = run {
                var state: Int
                do {
                    state = getRandomState(states.size)
                } while(getOutNumber(state) == alphabet.size)
                state
            }

            val letter = chooseLetter(outState)
            val inState = getRandomState(states.size)

            matrix[outState][letter] = inState
            transitions++
        }
    }

    private fun chooseLetter(state: Int): Int {
        var letter: Int
        do {
            letter = Math.floor(Math.random() * alphabet.size).toInt()
        } while (hasOut(state, letter))
        return letter
    }

    private fun hasOut(state: Int, letter: Int): Boolean {
        return matrix[state][letter] != -1
    }

    private fun getRandomState(length: Int, notEqualTo: Int = -1): Int {
        var state: Int
        do {
            state = (Math.floor(Math.random() * length)).toInt()
        } while(state == notEqualTo)
        return state
    }

    private fun getOutNumber(state: Int): Int {
        return matrix[state].count { it != -1 }
    }

    private fun getRandomOutputLetter(): String {
        return outputAlphabet[Math.floor(Math.random() * outputAlphabet.size).toInt()].toString()
    }

    private fun addStates(automaton: Automaton): List<State> {
        val angle = 2 * Math.PI / n
        val radius = 20 * n
        val offset = radius + 50

        val addedStates = mutableListOf<State>()
        states.forEachIndexed { index, state ->
            val x = (radius * Math.cos(((index + 1) * angle) - Math.PI) + offset).toInt()
            val y = (radius * Math.sin(((index + 1) * angle) - Math.PI) + offset).toInt()
            addedStates.add(State(state.first, Point(x, y), automaton))
            automaton.addState(addedStates.last())
            when (state.second) {
                StateType.INITIAL -> automaton.initialState = addedStates.last()
                StateType.STATE -> {}
                StateType.FINAL -> automaton.addFinalState(addedStates.last())
            }
        }

        return addedStates
    }

    private fun addTransitions(automaton: Automaton, addedStates: List<State>, transitionClass: (State, State, String) -> Transition) {
        for (state in 0 until states.size) for (letter in 0 until alphabet.size)
            if (matrix[state][letter] != -1)
                automaton.addTransition(transitionClass(
                    addedStates[state],
                    addedStates[matrix[state][letter]],
                    alphabet[letter].toString()
                ))
    }
}