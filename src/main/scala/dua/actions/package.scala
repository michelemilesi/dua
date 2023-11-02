package dua

package object actions {
  extension (n: Int) {
    def d2: DiceRoll = DiceRoll.roll(n, 2)

    def d3: DiceRoll = DiceRoll.roll(n, 3)

    def d4: DiceRoll = DiceRoll.roll(n, 4)

    def d6: DiceRoll = DiceRoll.roll(n, 6)

    def d8: DiceRoll = DiceRoll.roll(n, 8)

    def d10: DiceRoll = DiceRoll.roll(n, 10)

    def d12: DiceRoll = DiceRoll.roll(n, 12)

    def d20: DiceRoll = DiceRoll.roll(n, 20)

    def d100: DiceRoll = DiceRoll.roll(n, 100)
  }

}
