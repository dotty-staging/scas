package scas.application

import scas._

object PolyPower extends App {
  import Implicits.ZZ
  implicit val r = Polynomial(ZZ, 'x, 'y, 'z)
  import r.{size, generators}
  val Array(x, y, z) = generators
  val p = 1 + x + y + z
  println("p: " + p)
  val q = pow(p, 20)
  println("q: " + size(q))
  val q1 = 1 + q
  println("q1: " + size(q1))
  var t = System.currentTimeMillis();
  val q2 = q * q1
  t = System.currentTimeMillis() - t;
  println("q2: " + size(q2))
  println("t: " + t)
}
