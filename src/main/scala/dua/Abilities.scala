package dua

case class Abilities(str: Int = 0, dex: Int = 0, con: Int = 0, int: Int = 0, wis: Int = 0, cha: Int = 0) {
  def modify(mod: Abilities) = Abilities(
    str = this.str + mod.str,
    dex = this.dex + mod.dex,
    con = this.con + mod.con,
    int = this.int + mod.int,
    wis = this.wis + mod.wis,
    cha = this.cha + mod.cha
  )
}
