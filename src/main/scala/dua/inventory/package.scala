package dua

package object inventory {
  extension (n: Int) {
    def GP: Coins = Coins(gold = n)
    def SP: Coins = Coins(silver = n)
    def CP: Coins = Coins(copper = n)

    def Kg: Weight = Weight(n * 1000)

    def gr: Weight = Weight(n)
  }
  
  extension (n: Double) {
    def Kg: Weight = Weight((n * 1000).toInt)
  }


  enum ArmorType(val proficiency: Proficiency):
    case LIGHT_ARMOR extends ArmorType(Proficiency.LIGHT_ARMORS)
    case MEDIUM_ARMOR extends ArmorType(Proficiency.MEDIUM_ARMORS)
    case HEAVY_ARMOR extends ArmorType(Proficiency.HEAVY_ARMORS)
    case SHIELD extends ArmorType(Proficiency.SHIELDS)

  enum WeaponType(val proficiency: Proficiency):
    case SIMPLE_WEAPON extends WeaponType(Proficiency.SIMPLE_WEAPONS)
    case MARTIAL_WEAPON extends WeaponType(Proficiency.MARTIAL_WEAPONS)

  enum DamageType:
    case BLUDGEONING, PIERCING, SLASHING

}

