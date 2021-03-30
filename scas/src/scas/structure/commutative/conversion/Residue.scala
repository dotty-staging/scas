package scas.structure.commutative.conversion

trait Residue[T: scas.structure.commutative.UniqueFactorizationDomain] extends scas.structure.commutative.Residue[T] with UniqueFactorizationDomain[T] {
  override def ring = scas.structure.commutative.UniqueFactorizationDomain[T]
}
