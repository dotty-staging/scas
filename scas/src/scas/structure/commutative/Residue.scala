package scas.structure.commutative

trait Residue[T](using ring: UniqueFactorizationDomain[T]) extends UniqueFactorizationDomain[T] {
  def apply(x: T): T
  override def convert(x: T) = this(ring.convert(x))
  extension (x: T) {
    def signum = ring.signum(x)
    def add(y: T) = this(ring.add(x)(y))
    def subtract(y: T) = this(ring.subtract(x)(y))
    def multiply(y: T) = this(ring.multiply(x)(y))
  }
  def equiv(x: T, y: T) = ring.equiv(x, y)
  extension (x: T) {
    def toCode(level: Level) = ring.toCode(x)(level)
    def toMathML = ring.toMathML(x)
  }
  def zero = ring.zero
  def one = ring.one

  extension (ring: UniqueFactorizationDomain[T]) def residue(s: T*): Residue[T]
}
