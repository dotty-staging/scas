package jas

type BigInteger = edu.jas.arith.BigInteger

object BigInteger extends Ring[BigInteger] {
  given BigInteger.type = this
  val factory = new BigInteger()
  def apply(str: String) = new BigInteger(str)

  given int2bigInt: (Int => BigInteger) = new BigInteger(_)
  given long2bigInt: (Long => BigInteger) = new BigInteger(_)

  extension (a: Long) def \:(b: Long) = long2bigInt(a) \ bigInt2scas.apply(b)

  given bigInt2scas[U](using c: U => BigInteger): (U => scas.base.BigInteger) = x => (c(x)).`val`
}