package scas.structure

import scas.BigInteger

abstract class Quotient[T: UniqueFactorizationDomain] extends Field[(T, T)] with
  def apply(n: T): (T, T) = (n, UniqueFactorizationDomain[T].one)
  def (x: (T, T)) + (y: (T, T)) = {
    val (a, b) = x
    val (c, d) = y
    (a * d + c * b, b * d)
  }
  def (x: (T, T)) - (y: (T, T)) = {
    val (a, b) = x
    val (c, d) = y
    (a * d - c * b, b * d)
  }
  def (x: (T, T)) * (y: (T, T)) = {
    val (a, b) = x
    val (c, d) = y
    (a * c, b * d)
  }
  def inverse(x: (T, T)) = {
    val (n, d) = x
    (d, n)
  }
  override def (x: (T, T)) \ (exp: BigInteger) = if (BigInteger.signum(exp) < 0) inverse(x) \ (-exp) else {
    val (n, d) = x
    (n \ exp, d \ exp)
  }
  override def abs(x: (T, T)) = {
    val (n, d) = x
    (UniqueFactorizationDomain[T].abs(n), d)
  }
  def signum(x: (T, T)) = {
    val (n, _) = x
    UniqueFactorizationDomain[T].signum(n)
  }
  def zero = this(UniqueFactorizationDomain[T].zero)
  def one = this(UniqueFactorizationDomain[T].one)
