package math3

import java.lang.Math
import scas.structure.conversion.Field
import scas.base.BigInteger
import BigInteger.given

object Double extends Field[Double] {
  given Double.type = this
  extension (x: Double) {
    def add(y: Double) = x + y
    def subtract(y: Double) = x - y
    def multiply(y: Double) = x * y
    override def divide(y: Double) = x / y
  }
  def inverse(x: Double) = 1 / x
  def characteristic = BigInteger(0)
  def equiv(x: Double, y: Double) = x == y
  extension (x: Double) def signum = Math.signum(x).toInt
  def zero = 0
  def one = 1
  extension (x: Double) {
    def toCode(level: Level) = x.toString
    def toMathML = ???
  }
  def toMathML = ???
}