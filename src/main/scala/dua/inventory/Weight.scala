package dua.inventory

import scala.annotation.targetName

case class Weight(gr: Int) extends Ordered[Weight] {
  override def compare(that: Weight): Int = this.gr.compare(that.gr)

  def toKg: BigDecimal = gr / 1000f

  @targetName("add")
  def +(other: Weight): Weight = Weight(gr + other.gr)

  @targetName("mul")
  def *(n: Int): Weight = Weight(gr * n)
}
