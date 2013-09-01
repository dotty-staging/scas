package scas.power

import scala.reflect.ClassTag
import scas.{Variable, BigInteger}
import scas.math.{Ordering, Numeric}
import Ordering.Implicits.infixOrderingOps
import Numeric.Implicits.infixNumericOps

trait PowerProductWithDegree[@specialized(Byte, Short, Int, Long) N] extends PowerProduct[N] {
  implicit val m: ClassTag[N]
  import nm.{fromInt, toLong}
  import variables.length
  override def one = new Array[N](length + 1)
  def generator(n: Int) = (for (i <- 0 until length + 1) yield fromInt(if (i == n || i == length) 1 else 0)).toArray
  def degree(x: Array[N]) = toLong(x(length))
  override def pow(x: Array[N], exp: BigInteger) = {
    assert (exp.signum() >= 0)
    val n = fromBigInteger(exp)
    (for (i <- 0 until x.length) yield x(i) * n).toArray
  }
  def gcd(x: Array[N], y: Array[N]): Array[N] = {
    val r = one
    for (i <- 0 until length) r(i) = nm.min(x(i), y(i))
    r(length) = (fromInt(0) /: r) { (s, l) => s + l }
    r
  }
  def scm(x: Array[N], y: Array[N]): Array[N] = {
    val r = one
    for (i <- 0 until length) r(i) = nm.max(x(i), y(i))
    r(length) = (fromInt(0) /: r) { (s, l) => s + l }
    r
  }
  def multiply(x: Array[N], y: Array[N]) = {
    var i = 0
    while (i < x.length) {
      x(i) += y(i)
      i += 1
    }
    x
  }
  def divide(x: Array[N], y: Array[N]) = (for (i <- 0 until x.length) yield {
    assert (x(i) >= y(i))
    x(i) - y(i)
  }).toArray
  def factorOf(x: Array[N], y: Array[N]): Boolean = {
    for (i <- 0 until x.length) if (x(i) > y(i)) return false
    true
  }
  override def isOne(x: Array[N]) = x(length) equiv fromInt(0)
  def projection(x: Array[N], n: Int) = (for (i <- 0 until x.length) yield if (i == n || i == length) x(n) else fromInt(0)).toArray

  def converter(from: Array[Variable]): Array[N] => Array[N] = { x =>
    val r = one
    val index = from map { a => variables.indexOf(a) }
    for (i <- 0 until from.length if (x(i) > fromInt(0))) {
      val c = index(i)
      assert (c > -1)
      r(c) = x(i)
    }
    r(length) = x(from.length)
    r
  }
}
