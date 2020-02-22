package scas.base

import scas.{BigInteger, int2bigInt}
import scas.math.Ordering
import scas.structure.Ring
import scala.util.FromDigits

class BigIntegerImpl extends Ring[BigInteger] with Ordering[BigInteger] with FromDigits[BigInteger] with
  def fromDigits(digits: String) = new BigInteger(digits)
  def apply(x: BigInteger) = x
  def (x: BigInteger) + (y: BigInteger) = x.add(y)
  def (x: BigInteger) - (y: BigInteger) = x.subtract(y)
  def (x: BigInteger) * (y: BigInteger) = x.multiply(y)
  def compare(x: BigInteger, y: BigInteger) = x.compareTo(y)
  def (x: BigInteger) isZero = x >< 0
  def (x: BigInteger) isOne = x >< 1
  def zero = 0
  def one = 1
