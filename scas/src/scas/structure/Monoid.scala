package scas.structure

import scas.base.BigInteger
import BigInteger.given

trait Monoid[T] extends SemiGroup[T] {
  extension[U] (a: U)(using c: U => T) {
    def \ (b: BigInteger): T = c(a).pow(b)
    def \:(b: BigInteger) = a \ b
  }
  extension (a: T) {
    def \[U](b: U)(using c: U => BigInteger): T = a.pow(c(b))
    def pow(b: BigInteger): T = {
      assert (b.signum >= 0)
      if (b.isZero) one else if ((b % 2).isZero) {
        val c = a \ (b / 2)
        c * c
      } else {
        a * a \ (b - 1)
      }
    }
    def \:[U](b: U)(using c: U => BigInteger) = a \ b
  }
  extension (x: T) {
    def isUnit: Boolean
    def isOne = x >< one
  }
  def one: T
}
