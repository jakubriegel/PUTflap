package pl.poznan.put.cie.putflap.testutils

import jflap.automata.Automaton
import jflap.file.XMLCodec
import jflap.grammar.Grammar

class AutomatonReader {
    static def get(String filename) {
        return new XMLCodec().decode(new File("src/test/resources/$filename"), null) as Automaton
    }

    static def getGrammar(String filename) {
        return new XMLCodec().decode(new File("src/test/resources/$filename"), null) as Grammar
    }
}
