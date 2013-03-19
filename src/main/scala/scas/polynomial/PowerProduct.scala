package scas.polynomial

import scala.reflect.ClassTag
import scas.{Variable, BigInteger}
import scas.ordering.Ordering
import scas.structure.Monoid

class PowerProduct[@specialized(Int, Long) N](val variables: Array[Variable], val ordering: Ordering[N])(implicit nm: Numeric[N], m: Manifest[N], cm: ClassTag[Array[N]], cmm: ClassTag[Array[Array[N]]]) extends Monoid[Array[N]] {
  import scala.math.Ordering.Implicits.infixOrderingOps
  import Numeric.Implicits.infixNumericOps
  import nm.{fromInt, toLong}
  def take(n: Int) = new PowerProduct[N](variables.take(n), ordering)
  def drop(n: Int) = new PowerProduct[N](variables.drop(n), ordering)
  override def one = new Array[N](length + 1)
  def generator(variable: Variable): Array[N] = generator(variables.indexOf(variable))
  def generator(n: Int) = (for (i <- 0 until length + 1) yield fromInt(if (i == n || i == length) 1 else 0)).toArray
  def generators = (for (i <- 0 until length) yield generator(i)).toArray
  def generatorsBy(n: Int) = {
    val m = length/n
    (for (i <- 0 until m) yield (for (j <- 0 until n) yield generator(i * n + j)).toArray).toArray
  }
  def degree(x: Array[N]) = toLong(x(length))
  override def pow(x: Array[N], exp: BigInteger) = {
    assert (exp.signum() >= 0)
    val n = fromBigInteger(exp)
    (for (i <- 0 until x.length) yield x(i) * n).toArray
  }
  def fromBigInteger(value: BigInteger) = {
    (fromInt(0) /: value.toByteArray()) { (s, b) => s * fromInt(0x100) + fromInt(b & 0xff) }
  }
  def apply(l: Long) = {
    assert (l == 1)
    one
  }
  def convert(x: Array[N]) = x
  def random(numbits: Int)(implicit rnd: java.util.Random) = one
  def gcd(x: Array[N], y: Array[N]): Array[N] = {
    val r = new Array[N](length + 1)
    for (i <- 0 until length) r(i) = nm.min(x(i), y(i))
    r(length) = (fromInt(0) /: r) { (s, l) => s + l }
    r
  }
  def scm(x: Array[N], y: Array[N]): Array[N] = {
    val r = new Array[N](length + 1)
    for (i <- 0 until length) r(i) = nm.max(x(i), y(i))
    r(length) = (fromInt(0) /: r) { (s, l) => s + l }
    r
  }
  def coprime(x: Array[N], y: Array[N]) = gcd(x, y).isOne
  def compare(x: Array[N], y: Array[N]) = ordering.compare(x, y)
  def times(x: Array[N], y: Array[N]) = times(x, y, one)
  def times(x: Array[N], y: Array[N], r: Array[N]) = {
    var i = 0
    while (i < r.length) {
      r(i) = x(i) + y(i)
      i += 1
    }
    r
  }
  def divide(x: Array[N], y: Array[N]) = (for (i <- 0 until x.length) yield {
    assert (x(i) >= y(i))
    x(i) - y(i)
  }).toArray
  def factorOf(x: Array[N], y: Array[N]): Boolean = {
    for (i <- 0 until x.length) if (x(i) > y(i)) return false
    true
  }
  def isUnit(x: Array[N]) = x.isOne
  override def isOne(x: Array[N]) = x(length) equiv fromInt(0)
  def dependencyOnVariables(x: Array[N]) = (for (i <- 0 until length if (x(i) > fromInt(0))) yield i).toArray
  def projection(x: Array[N], n: Int) = (for (i <- 0 until x.length) yield if (i == n || i == length) x(n) else fromInt(0)).toArray
  override def toCode(x: Array[N], precedence: Int) = {
    var s = "1"
    var m = 0
    for (i <- 0 until length) if (x(i) > fromInt(0)) {
      val t = {
        if (x(i) equiv fromInt(1)) variables(i).toString
        else "pow(" + variables(i).toString + ", " + x(i) + ")"
      }
      s = if (m == 0) t else s + "*" + t
      m += 1
    }
    s
  }
  override def toString = "["+variables.mkString(", ")+"]"
  def toMathML(x: Array[N]) = {
    var s = <cn>1</cn>
    var m = 0
    for (i <- 0 until length) if (x(i) > fromInt(0)) {
      val t = {
        if (x(i) equiv fromInt(1)) variables(i).toMathML
        else <apply><power/>{variables(i).toMathML}<cn>{x(i)}</cn></apply>
      }
      s = if (m == 0) t else <apply><times/>{s}{t}</apply>
      m += 1
    }
    s
  }
  def toMathML = <list>{variables.map(_.toMathML)}</list>

  def converter(from: Array[Variable]): Array[N] => Array[N] = { x =>
    val r = new Array[N](length + 1)
    val index = from map { a => variables.indexOf(a) }
    for (i <- 0 until from.length if (x(i) > fromInt(0))) {
      val c = index(i)
      assert (c > -1)
      r(c) = x(i)
    }
    r(length) = x(from.length)
    r
  }

  def length = variables.length

  def size(x: Array[N]) = {
    var m = 0
    for (i <- 0 until length) if (x(i) > fromInt(0)) {
      m += 1
    }
    m
  }

  override implicit def mkOps(value: Array[N]) = new PowerProduct.Ops(value)(this)
}

object PowerProduct {
  trait ExtraImplicits extends Monoid.ExtraImplicits {
    implicit def infixPowerProductOps[@specialized(Int, Long) N: PowerProduct](lhs: Array[N]) = implicitly[PowerProduct[N]].mkOps(lhs)
  }
  object Implicits extends ExtraImplicits

  def apply(variables: Variable*): PowerProduct[Int] = apply(variables.toArray, Ordering.lexicographic[Int])
  def apply[@specialized(Int, Long) N](variables: Array[Variable], ordering: Ordering[N])(implicit nm: Numeric[N], m: Manifest[N], cm: ClassTag[Array[N]]) = new PowerProduct[N](variables, ordering)
  def apply[@specialized(Int, Long) N](sss: Array[Array[Variable]], ordering: Ordering[N])(implicit nm: Numeric[N], m: Manifest[N], cm: ClassTag[Array[N]]): PowerProduct[N] = apply(for (ss <- sss ; s <- ss) yield s, ordering)

  class Ops[@specialized(Int, Long) N](val lhs: Array[N])(val factory: PowerProduct[N]) extends Monoid.Ops[Array[N]] {
    def /(rhs: Array[N]) = factory.divide(lhs, rhs)
    def |(rhs: Array[N]) = factory.factorOf(lhs, rhs)
  }
}
