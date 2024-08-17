package scas.structure

trait Field[T] extends NotQuiteField[T] with NotQuiteGroup[T] {
  extension (x: T) {
    override def isUnit = !x.isZero
    def divide(y: T) = x * inverse(y)
  }
}

object Field {
  def apply[T : Field] = summon[Field[T]]
}
