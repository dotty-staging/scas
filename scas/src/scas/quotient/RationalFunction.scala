package scas.quotient

import scas.structure.commutative.Field
import scas.structure.commutative.Quotient.{Element => Quotient_Element}
import scas.polynomial.tree.PolynomialWithSubresGCD
import scas.polynomial.TreePolynomial.Element
import scas.polynomial.ufd.PolynomialOverUFD
import scas.util.{Conversion, unary_~}
import scas.variable.Variable
import scas.base.BigInteger

class RationalFunction(using val ring: PolynomialOverUFD[Element[BigInteger, Array[Int]], BigInteger, Array[Int]]) extends QuotientOverInteger[Element[BigInteger, Array[Int]], Array[Int]] {
  def this(s: Variable*) = this(using new PolynomialWithSubresGCD(using BigInteger)(s*))
}

object RationalFunction {
  def apply[C, S : Conversion[Variable]](ring: Field[C])(s: S*) = new RationalFunctionOverField.Conv(ring)(s*)
  def integral[S : Conversion[Variable]](s: S*) = new Conv(s*)

  class Conv[S : Conversion[Variable]](s: S*) extends RationalFunction(s.map(~_)*) with Field.Conv[Quotient_Element[Element[BigInteger, Array[Int]]]] {
    given instance: Conv[S] = this
    extension[U: Conversion[BigInteger]] (a: U) {
      def %%[V: Conversion[BigInteger]](b: V) = this(ring(~a), ring(~b))
    }
  }
}
