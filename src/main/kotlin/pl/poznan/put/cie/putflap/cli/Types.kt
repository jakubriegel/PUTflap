package pl.poznan.put.cie.putflap.cli

/**
 * Types of tasks performed by CLI
 *
 * @see Commands
 * @see CLI
 */
abstract class Types {
    enum class InstructionType {
        RANDOM, RUN, TEST, WORD, CONVERT
    }

    enum class RandomType {
        FSA, MOORE, MEALY, REGR
    }

    enum class TestType {
        NDET, EQ, AL
    }

    enum class ConvertType {
        DFA, MINI, GR, RE, PDA, FSA, JSON
    }
}