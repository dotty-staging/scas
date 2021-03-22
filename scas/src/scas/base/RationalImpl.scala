package scas.base

import scas.structure.commutative.Quotient
import scas.structure.commutative.ordered.Field
import scas.{BigInteger, Rational}
import scala.util.FromDigits
import BigInteger.given
import Rational.given

class RationalImpl extends Quotient[BigInteger] with Field[Rational] with FromDigits[Rational] {
  def fromDigits(digits: String) = fromRing(BigInteger.fromDigits(digits))
  def compare(x: Rational, y: Rational) = {
    val Rational(a, b) = x
    val Rational(c, d) = y
    BigInteger.compare(a * d, c * b)
  }
  extension (x: Rational) override def signum = super[Quotient].signum(x)
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
  override lazy val zero = Rational(0)
  override lazy val one = Rational(1)
}
