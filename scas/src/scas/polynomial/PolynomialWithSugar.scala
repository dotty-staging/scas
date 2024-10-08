package scas.polynomial

import scala.annotation.targetName
import scas.power.PowerProduct
import scas.structure.Ring
import scas.polynomial.gb.SugarEngine
import PolynomialWithSugar.Element
import scas.base.BigInteger
import BigInteger.{max, given}

class PolynomialWithSugar[T, C, M](using factory: Polynomial[T, C, M]) extends Polynomial[Element[T], C, M] {
  given ring: Ring[C] = factory.ring
  given pp: PowerProduct[M] = factory.pp
  def apply(s: (M, C)*) = this(factory(s*))
  @targetName("fromSelf") def apply(p: T) = (p, p.degree)
  override def normalize(x: Element[T]) = {
    val (p, e) = x
    (factory.normalize(p), e)
  }
  extension (x: Element[T]) {
    def iterator = {
      val (p, _) = x
      p.iterator
    }
    def size = {
      val (p, _) = x
      p.size
    }
    def head = {
      val (p, _) = x
      p.head
    }
    def last = {
      val (p, _) = x
      p.last
    }
    def add(y: Element[T]) = {
      val (p, e) = x
      val (q, f) = y
      (p + q, max(e, f))
    }
    override def ppMultiplyRight(m: M) = {
      val (p, e) = x
      (p.ppMultiplyRight(m), e + m.degree)
    }
    override def multiply(m: M, c: C) = {
      val (p, e) = x
      (p.multiply(m, c), e + m.degree)
    }
    def map(f: (M, C) => (M, C)) = {
      val (p, e) = x
      (p.map(f), e)
    }
  }
  override def gb(xs: Element[T]*) = new SugarEngine(using this).process(xs)
}

object PolynomialWithSugar {
  type Element[T] = (T, BigInteger)
}
