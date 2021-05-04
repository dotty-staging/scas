package scas.polynomial

import java.util.TreeMap
import scala.annotation.targetName
import scala.collection.JavaConverters.mapAsScalaMapConverter
import scala.reflect.ClassTag
import scala.math.Ordering
import scas.structure.Ring
import scas.power.PowerProduct

trait SolvablePolynomial[T : ClassTag, C : Ring, M](using pp: PowerProduct[M]) extends Polynomial[T, C, M] {
  import pp.dependencyOnVariables
  type Key = (Int, Int)
  type Relation = (M, M, T)
  val table = new TreeMap[Key, List[Relation]](Ordering[Key])
  def update(e: T, f: T, p: T): Unit = update(headPowerProduct(e), headPowerProduct(f), p)
  def update(e: M, f: M, p: T) = {
    val key = makeKey(e, f)
    val list = table.asScala.getOrElse(key, Nil)
    table.put(key, insert(list, (e, f, p)))
  }
  def insert(list: List[Relation], relation: Relation): List[Relation] = list match {
    case head::tail if (factorOf(relation, head)) => head::insert(tail, relation)
    case _ => relation::list
  }
  def lookup(e: M, f: M): Relation = {
    val key = makeKey(e, f)
    val list = table.asScala.getOrElse(key, Nil)
    list match {
      case Nil => (pp.one, pp.one, this(e * f))
      case _ => {
        val (e0, f0, p0) = select(list, (e, f, zero))
        (e / e0, f / f0, p0)
      }
    }
  }
  def select(list: List[Relation], relation: Relation): Relation = list match {
    case head::tail => if (factorOf(head, relation)) head else select(tail, relation)
    case _ => relation
  }
  def factorOf(x: Relation, y: Relation) = {
    val (ex, fx, px) = x
    val (ey, fy, py) = y
    (ex | ey) && (fx | fy)
  }
  def makeKey(e: M, f: M) = {
    val de = dependencyOnVariables(e)
    val df = dependencyOnVariables(f)
    (de(0), df(0))
  }
  override def toString = s"${super.toString}(${(for ((a, b) <- table.asScala; relation <- b) yield toString(relation)).mkString(", ")})"
  def toString(relation: Relation) = {
    val (e, f, p) = relation
    s"${p.show}-${e.show}*${f.show}"
  }
  override def toMathML = s"<apply>${super.toMathML}<list>${(for ((a, b) <- table.asScala; relation <- b) yield toMathML(relation)).mkString}</list></apply>"
  def toMathML(relation: Relation): String = {
    val (e, f, p) = relation
    s"<apply><minus/>${p.toMathML}<apply><times/>${e.toMathML}${f.toMathML}</apply></apply>"
  }

  extension (ring: Polynomial[T, C, M]) def apply(s: T*): SolvablePolynomial[T, C, M] = {
    assert (s.foldLeft(true)((l, r) => l && r.isZero))
    this
  }

  extension (x: T) {
    final override def multiply(y: T) = super[Polynomial].multiply(x)(y)

    final override def subtract(m: M, c: C, y: T) = super[Polynomial].subtract(x)(m, c, y)

    final override def multiply(m: M, c: C) = super.multiply(x%* m)(c)

    final override def %* (m: M) = iterator(x).foldLeft(zero) { (l, r) =>
      val (s, _) = r
      l + s.multiply(m)
    }
  }

  extension (e: M) @targetName("ppMultiply") def multiply(f: M) = {
    val ep = dependencyOnVariables(e)
    val fp = dependencyOnVariables(f)
    if (ep.length == 0 || fp.length == 0) this(e * f) else {
      val el = ep(ep.length-1)
      val fl = fp(0)
      if (el <= fl) this(e * f) else {
        val e2 = e.projection(el)
        val f2 = f.projection(fl)
        val e1 = e / e2
        val f1 = f / f2
        val (e3, f3, c3) = lookup(e2, f2)
        var cs = c3
        if (!(f3.isOne)) {
          cs = cs%* f3
          update(e2 / e3, f2, cs)
        }
        if (!(e3.isOne)) {
          cs = this(e3) * cs
          update(e2, f2, cs)
        }
        if (!(f1.isOne)) cs = cs%* f1
        if (!(e1.isOne)) cs = this(e1) * cs
        cs
      }
    }
  }
}
