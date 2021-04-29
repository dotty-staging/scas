package scas.base.conversion

import scas.structure.commutative.ordered.conversion.EuclidianDomain
import scas.base.BigInteger.Impl

type BigInteger = scas.base.BigInteger

object BigInteger extends Impl with EuclidianDomain[BigInteger] {
  given BigInteger.type = this

  given int2bigInt: (Int => BigInteger) = java.math.BigInteger.valueOf(_)
  given long2bigInt: (Long => BigInteger) = java.math.BigInteger.valueOf(_)

  extension (a: Long) {
    def \ (b: Long): BigInteger = long2bigInt(a) \ b
    def \:(b: Long) = a \ b
  }
}
