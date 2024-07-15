package scas.quotient

import scas.structure.commutative.Field
import scas.structure.commutative.Quotient.Element
import scas.polynomial.tree.{UnivariatePolynomial, PolynomialWithSimpleGCD}
import scas.polynomial.PolynomialOverUFD
import scas.base.{BigInteger, Rational}
import scas.variable.Variable
import scas.util.Conversion
import BigInteger.given
import Rational.given

trait RationalFunction[T, M](using ring: PolynomialOverUFD[T, BigInteger, M]) extends Quotient[T, BigInteger, M] {
  import ring.{degree, headCoefficient}
  extension (x: Element[T]) override def toCode(level: Level) = {
    val Element(n, d) = x
    if(degree(n) >< 0 && degree(d) >< 0) Rational(headCoefficient(n), headCoefficient(d)).toCode(level) else super.toCode(x)(level)
  }
  extension (x: Element[T]) override def toMathML = {
    val Element(n, d) = x
    if(degree(n) >< 0 && degree(d) >< 0) Rational(headCoefficient(n), headCoefficient(d)).toMathML else super.toMathML(x)
  }
}

object RationalFunction {
  def apply[C, S : Conversion[Variable]](ring: Field[C])(s: S*) = new conversion.RationalFunctionOverField(using UnivariatePolynomial(using ring)(s*))
  def integral(s: String*) = new conversion.RationalFunction(using PolynomialWithSimpleGCD(using BigInteger)(s*))
}
