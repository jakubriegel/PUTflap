package pl.poznan.put.cie.putflap.exception

abstract class PUTflapException(message: String) : Exception(message)

class IncompatibleAutomatonException(message: String = "") : PUTflapException(
    "Given automaton cannot be used in current context. $message"
)

class TooManyNonterminalsException() : PUTflapException(
    "Maximal supported number of nonterminals is 25"
)