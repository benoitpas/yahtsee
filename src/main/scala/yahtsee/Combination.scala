package yahtsee

sealed abstract class Combination extends Product with Serializable

object Combination {
  final case class Upper(diceValue: Int) extends Combination
  final case object ThreeOfAKind extends Combination
  final case object FourOfAKind extends Combination
  final case object FullHouse extends Combination
  final case object SmallStraight extends Combination
  final case object FullStraight extends Combination
  final case object Chance extends Combination
  final case object Yahtsee extends Combination

//  def getPoints(c: Combination, r:Roll) : Int 
}