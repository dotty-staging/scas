package scas.base

import scas.{BigInteger, int2bigInt}
import scas.structure.ordered.EuclidianDomain
import scala.util.FromDigits

class BigIntegerImpl extends EuclidianDomain[BigInteger] with FromDigits[BigInteger] with
  def fromDigits(digits: String) = new BigInteger(digits)
  def apply(x: BigInteger) = x
  def (x: BigInteger) + (y: BigInteger) = x.add(y)
  def (x: BigInteger) - (y: BigInteger) = x.subtract(y)
  def (x: BigInteger) * (y: BigInteger) = x.multiply(y)
  def compare(x: BigInteger, y: BigInteger) = x.compareTo(y)
  def norm(x: BigInteger) = abs(x).shiftLeft(1).add(if (signum(x) < 0) 1 else 0)
  def gcd(x: BigInteger, y: BigInteger) = x.gcd(y)
  override def (x: BigInteger) / (y: BigInteger) = x.divide(y)
  override def (x: BigInteger) % (y: BigInteger) = x.remainder(y)
  def (x: BigInteger) /%(y: BigInteger) = {
    val Array(q, r) = x.divideAndRemainder(y)
    (q, r)
  }
  def (x: BigInteger).isUnit = abs(x) >< 1
  def zero = 0
  def one = 1
