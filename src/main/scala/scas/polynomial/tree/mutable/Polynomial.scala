package scas.polynomial
package tree
package mutable

import java.util.SortedMap
import scas.Variable
import scas.polynomial.mutable.TreePolynomial
import scas.structure.Ring
import Polynomial.Element

trait Polynomial[C, @specialized(Int, Long) N] extends TreePolynomial[Element[C, N], C, N] {
  def apply(value: SortedMap[Array[N], C]) = new Element(value)(this)
}

object Polynomial {
  def apply[C](ring: Ring[C], variables: Variable*): Polynomial[C, Int] = apply(ring, PowerProduct(variables: _*))
  def apply[C, @specialized(Int, Long) N](ring: Ring[C], pp: PowerProduct[N]) = new PolynomialImpl(ring, pp)
  def parallel[C](ring: Ring[C], variables: Variable*): Polynomial[C, Int] = parallel(ring, PowerProduct(variables: _*))
  def parallel[C, @specialized(Int, Long) N](ring: Ring[C], pp: PowerProduct[N]) = new ParallelPolynomial(ring, pp)

  class Element[C, @specialized(Int, Long) N](val value: SortedMap[Array[N], C])(val factory: Polynomial[C, N]) extends TreePolynomial.Element[Element[C, N], C, N]
  object Element extends ExtraImplicits

  trait ExtraImplicits {
    implicit def coef2polynomial[D, C, @specialized(Int, Long) N](value: D)(implicit f: D => C, factory: Polynomial[C, N]) = factory(value)
  }
}
