package scas.scripting

object Parsers extends scala.util.parsing.combinator.RegexParsers {
  def obj: Parser[Object] = {
    val poly = PolyParsers()
    ComplexParsers.obj ||| poly.obj
  }

  def apply(input: String) = {
    val result = parseAll(obj, input) match {
      case Success(result, _) => Right(result)
      case NoSuccess.I(msg, _) => Left(msg)
    }
    result
  }
}
