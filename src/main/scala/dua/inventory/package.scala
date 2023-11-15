package dua

package object inventory {
  extension (n: Int) {
    def GP: Coins = Coins(gold = n)
    def SP: Coins = Coins(silver = n)
    def CP: Coins = Coins(copper = n)
  }
}
