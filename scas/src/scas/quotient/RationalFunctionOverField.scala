package scas.quotient

import scas.structure.commutative.Field
import scas.structure.commutative.Quotient.{Element => Quotient_Element}
import scas.polynomial.tree.MultivariatePolynomialOverField
import scas.polynomial.ufd.PolynomialOverField
import scas.polynomial.TreePolynomial.Element
import scas.variable.Variable

class RationalFunctionOverField[C](using PolynomialOverField[Element[C, Array[Int]], C, Array[Int]]) extends QuotientOverField[Element[C, Array[Int]], C, Array[Int]] {
  def this(ring: Field[C])(variables: Variable*) = this(using new MultivariatePolynomialOverField(using ring)(variables*))
}

object RationalFunctionOverField {
  class Conv[C](ring: Field[C])(variables: Variable*) extends RationalFunctionOverField(ring)(variables*) with Field.Conv[Quotient_Element[Element[C, Array[Int]]]] {
    given instance: Conv[C] = this
  }
}
