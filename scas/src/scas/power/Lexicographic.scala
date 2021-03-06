package scas.power

import scala.reflect.ClassTag
import scas.math.Numeric
import scas.util.ClassTagArray
import scas.variable.Variable
import Variable.string2variable
import Lexicographic.Impl

class Lexicographic[N : Numeric : ClassTag : ClassTagArray](val variables: Variable*) extends ArrayPowerProductWithDegree[N] {
  def compare(x: Array[N], y: Array[N]) = {
    var i = length
    while (i > 0) {
      i -= 1
      if (x(i) < y(i)) return -1
      else if (x(i) > y(i)) return 1
    }
    0
  }
}

object Lexicographic extends Impl {
  class Impl {
    inline def apply[N : Numeric : ClassTag : ClassTagArray](variables: String*) = new Lexicographic[N](variables.map(string2variable): _*) {
      override def compare(x: Array[N], y: Array[N]) = {
        var i = length
        while (i > 0) {
          i -= 1
          if (x(i) < y(i)) return -1
          else if (x(i) > y(i)) return 1
        }
        0
      }
      extension (x: Array[N]) override def multiply(y: Array[N]) = {
        val r = one
        var i = 0
        while (i <= length) {
          r(i) = x(i) + y(i)
          i += 1
        }
        r
      }
    }
  }
}
