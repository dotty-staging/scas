package scas.structure

import scas.math.Equiv
import scas.prettyprint.Show

trait Structure[T] extends Equiv[T] with Show[T] {
  def convert(x: T) = x
  def random(numbits: Int)(using rnd: java.util.Random): T = ???
  def math = Show.math(toMathML)
  def toMathML: String
}
