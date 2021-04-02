package scas.structure.commutative.conversion

trait Residue[T](using override val ring: scas.structure.commutative.UniqueFactorizationDomain[T]) extends scas.structure.commutative.Residue[T] with UniqueFactorizationDomain[T]
