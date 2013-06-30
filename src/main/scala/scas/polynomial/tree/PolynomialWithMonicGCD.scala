package scas.polynomial
package tree

import scala.reflect.ClassTag
import scas.power.PowerProduct
import scas.structure.Field
import MultivariatePolynomial.Element

class PolynomialWithMonicGCD[C, N](val ring: Field[C], val pp: PowerProduct[N])(implicit val cm: ClassTag[Element[C, N]]) extends MultivariatePolynomial[C, N] with scas.polynomial.PolynomialOverField[Element[C, N], C, N] {
  def split = MultivariatePolynomial(MultivariatePolynomial(ring, pp.take(location)), pp.drop(location))
}
