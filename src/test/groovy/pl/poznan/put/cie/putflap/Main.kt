package pl.poznan.put.cie.putflap

import pl.poznan.put.cie.putflap.cli.CLI


fun main(args: Array<String>) {
//    val fsa = FiniteStateAutomaton()
//
//    val states = mutableListOf<State>()
//    for (i in 0..5) states.add(fsa.createState(Point()))
//    fsa.initialState = states[0]
//    fsa.addFinalState(states[4])
//
//    fsa.addTransition(FSATransition(states[0], states[1], "a"))
//    fsa.addTransition(FSATransition(states[0], states[2], "a"))
//    fsa.addTransition(FSATransition(states[0], states[4], "a"))
//    fsa.addTransition(FSATransition(states[2], states[3], "d"))
//    fsa.addTransition(FSATransition(states[2], states[4], "e"))
//    fsa.addTransition(FSATransition(states[2], states[1], "f"))
//    fsa.addTransition(FSATransition(states[3], states[4], "g"))
//    fsa.addTransition(FSATransition(states[4], states[1], "h"))
//
//    println(fsa.toString())
//
//    val nds = FSANondeterminismDetector().getNondeterministicStates(fsa)
//    println(nds.size)
//    for (s in nds) println(s.toString())
//
    CLI.init(args)
}