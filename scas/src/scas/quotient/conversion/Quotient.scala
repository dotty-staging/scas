package scas.quotient.conversion

import scas.polynomial.PolynomialOverUFD
import scas.structure.commutative.conversion.Field
import scas.structure.commutative.Quotient.Element

class Quotient[T, C, M](using val ring: PolynomialOverUFD[T, C, M]) extends scas.quotient.Quotient[T, C, M] with Field[Element[T]] {
  given instance: Quotient[T, C, M] = this
}
