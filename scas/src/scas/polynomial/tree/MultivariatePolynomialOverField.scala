package scas.polynomial.tree

import scas.power.PowerProduct
import scas.structure.commutative.{UniqueFactorizationDomain, Field}
import scas.variable.Variable
import scas.polynomial.TreePolynomial.Element

class MultivariatePolynomialOverField[C](using val ring: Field[C])(val s: Variable*) extends MultivariatePolynomial[C] with scas.polynomial.ufd.MultivariatePolynomialOverField[Element, C, Array[Int]] {
  def newInstance = [C] => (ring: UniqueFactorizationDomain[C], pp: PowerProduct[Array[Int]]) => new PolynomialWithSubresGCD(using ring)(pp.variables*)
}
