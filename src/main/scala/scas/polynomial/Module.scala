package scas.polynomial

import scas.structure.Ring
import Module.Element

class Module[R <: Polynomial.Element[R, C, N], C, @specialized(Int, Long) N](val dimension: Int, val name: Option[String], override val ring: Polynomial[R, C, N])(implicit val cm: ClassManifest[R]) extends scas.module.Module[R] {
  def multiply(w: Element[R], x: Array[N], y: C) = w * ring.fromPowerProduct(x) * ring(y)
  def multiply(w: Element[R], y: C) = w * ring(y)
}

object Module {
  def apply[R <: Polynomial.Element[R, C, N], C, @specialized(Int, Long) N](name: String, dimension: Int, ring: Polynomial[R, C, N])(implicit cm: ClassManifest[R]) = new Module(dimension, Some(name), ring)
  def apply[R <: Polynomial.Element[R, C, N], C, @specialized(Int, Long) N](dimension: Int, ring: Polynomial[R, C, N])(implicit cm: ClassManifest[R]) = new Module(dimension, None, ring)

  type Element[R] = scas.module.Module.Element[R]
}
