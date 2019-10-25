package yahtsee

object Dice extends Enumeration {
  type Dice = Value
  val One, Two, Three, Four, Five, Six = Value
  def getValue(d:Dice) = d.id + 1
}

import Dice.Dice

object Roll {
  type Roll = (Dice,Dice,Dice,Dice,Dice)
  def getIterator(r: Roll) = r.productIterator.map(_.asInstanceOf[Dice.Value])
  def count(r: Roll, d: Dice) = getIterator(r).filter(d2 =>  (d2 == d)).toList.length
  def count(r: Roll) = getIterator(r).foldLeft(Map[Dice,Int]())((m,d:Dice) => m ++ List(d -> (m.getOrElse(d,0) + 1)))
}

import Roll.Roll

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

  type Points = Int

  def getPoints(c: Combination, r:Roll) : Points = c match {
    case Upper(d) => Roll.count(r, d) * Dice.getValue(d)
//    case ThreeOfAKind => Roll.count()
  }
}