package scas.polynomial.stream

import scas.structure.Ring
import scas.power.PowerProduct
import scas.polynomial.StreamPolynomial
import StreamPolynomial.Element

class Polynomial[C, M](using val ring: Ring[C], val pp: PowerProduct[M]) extends StreamPolynomial[C, M] with Ring.Conv[Element[C, M]] {
  given instance: Polynomial[C, M] = this
}
