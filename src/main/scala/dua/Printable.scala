package dua

trait Printable {
  def print(variant: Option[Any] = None)(using status: DuaStatus): String
}
