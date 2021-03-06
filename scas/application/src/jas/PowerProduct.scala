package jas

import scas.util.{Conversion, unary_~}
import scas.variable.Variable
import edu.jas.poly.ExpVector

class PowerProduct(val variables: Variable*) extends scas.power.conversion.PowerProduct[ExpVector] {
  def one = ExpVector.create(length)
  def generator(n: Int) = ExpVector.create(length, n, 1)
  def degree(x: ExpVector) = x.degree
  def gcd(x: ExpVector, y: ExpVector) = x.gcd(y)
  def lcm(x: ExpVector, y: ExpVector) = x.lcm(y)
  extension (x: ExpVector) {
    def multiply(y: ExpVector) = x.sum(y)
    def divide(y: ExpVector) = x.subtract(y)
    def factorOf(y: ExpVector) = x.divides(y)
  }
  def compare(x: ExpVector, y: ExpVector) = x.compareTo(y)
  def dependencyOnVariables(x: ExpVector) = x.dependencyOnVariables
  extension (x: ExpVector) {
    def projection(n: Int) = ???
    def toCode(level: Level) = x.toString(variables.map(_.toString).toArray)
    def toMathML = ???
    def convert(from: Variable*) = ???
  }
  def size(x: ExpVector) = x.dependentVariables
}

object PowerProduct {
  def apply[U: Conversion[Variable]](variables: U*) = new PowerProduct(variables.map(~_): _*)
}
