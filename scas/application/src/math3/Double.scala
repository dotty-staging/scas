package math3

import java.lang.Math
import scas.structure.Field
import scas.base.BigInteger
import BigInteger.given
import Double.Impl

object Double extends Impl {
  given Double.type = this
  def characteristic = BigInteger("0")
  abstract class Impl extends Field[Double] {
    extension (x: Double) {
      def add(y: Double) = x + y
      def subtract(y: Double) = x - y
      def multiply(y: Double) = x * y
      override def divide(y: Double) = x / y
    }
    def inverse(x: Double) = 1 / x
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
}
