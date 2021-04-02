package scas.base

import scas.structure.commutative.ordered.conversion.Quotient
import BigInteger.given

type Rational = Quotient.Element[BigInteger]

object Rational extends Quotient[BigInteger] {
  given Rational.type = this
  object Implicits {
    given bigInt2rational[U](using c: U => BigInteger): (U => Rational) = x => Rational(c(x))
  }
  def apply(n: String, d: String): Rational = this(BigInteger(n), BigInteger(d))
  extension (a: Long) def %%(b: Long) = this(long2bigInt(a), long2bigInt(b))
  extension (x: Rational) override def toCode(level: Level) = {
    val Rational(n, d) = x
    if (d.isOne) n.toCode(level) else {
      if (n.bitLength < 64 && d.bitLength < 64) {
        val s = n.toCode(Level.Multiplication) + "%%" + d.toCode(Level.Power)
        if (level > Level.Multiplication) fenced(s) else s
      } else s"Rational($n, $d)"
    }
  }
  override def toString = "Rational"
  extension (x: Rational) override def toMathML = {
    val Rational(n, d) = x
    if (d.isOne) n.toMathML else s"""<cn type="rational">$n<sep/>$d</cn>"""
  }
  override def toMathML = "<rationals/>"
  override val zero = { import Implicits.bigInt2rational; Rational(0) }
  override val one = { import Implicits.bigInt2rational; Rational(1) }
}