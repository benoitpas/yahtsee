package yahtsee

object Dice extends Enumeration {
  type Dice = Value
  val One, Two, Three, Four, Five, Six = Value
}

import Dice.Dice

sealed abstract class Combination extends Product with Serializable

object Combination {
  final case class Upper(diceValue: Dice) extends Combination
  final case object ThreeOfAKind extends Combination
  final case object FourOfAKind extends Combination
  final case object FullHouse extends Combination
  final case object SmallStraight extends Combination
  final case object FullStraight extends Combination
  final case object Chance extends Combination
  final case object Yahtsee extends Combination

//  def getPoints(c: Combination, r:Roll) : Int 
}