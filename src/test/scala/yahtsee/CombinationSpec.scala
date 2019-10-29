import org.scalatest.FunSpec
import yahtsee.Combination
import yahtsee.Combination._
import yahtsee.Dice
import yahtsee.Dice._

class CombinationSpec extends FunSpec {

  describe("Combination.getPoints()") {

    val r = (One, Two, Four, One, Four)
    it("Upper(Six) should be 0") {
        assert(Combination.getPoints(Upper(Six), r) === 0)
    }
  
    it("(Upper(Two) should be 2") {
        assert(Combination.getPoints(Upper(Two), r) === 2)
    }

      it("Upper(One) should be 2 too") {
      assert(Combination.getPoints(Upper(One), r) === 2)
    }

    it("Upper(Four) should be 8") {
        assert(Combination.getPoints(Upper(Four), r) === 8)
    }

    it("ThreeOfAKind() should be 0") {
        assert(Combination.getPoints(ThreeOfAKind, r) === 0)
    }

    val r2 = (One, Four, Four, One, Four)
    it("ThreeOfAKind() should be 14") {
        assert(Combination.getPoints(ThreeOfAKind, r2) === 1+4+4+1+4)
    }

    val r3 = (One, Four, Four, Four, Four)
    it("ThreeOfAKind() should be 17") {
        assert(Combination.getPoints(ThreeOfAKind, r3) === 1+4+4+4+4)
    }

    it("FourOfAKind() should be 0") {
        assert(Combination.getPoints(FourOfAKind, r2) === 0)
    }

    it("FourOfAKind() should be 17") {
        assert(Combination.getPoints(FourOfAKind, r3) === 1+4+4+4+4)
    }

    it("FullHouse() should be 25") {
        assert(Combination.getPoints(FullHouse, r2) === 25)
    }

    it("FullHouse() should be 0") {
        assert(Combination.getPoints(FullHouse, r3) === 0)
    }

    it("SmallStraight() should be 0") {
        assert(Combination.getPoints(SmallStraight, r3) === 0)
    }

    val r4 = (Four, Five, Three, Six,One)
    it("SmallStraight() should be 30 (1)") {
        assert(Combination.getPoints(SmallStraight, r4) === 30)
    }

    val r5 = (Two, One, Three, Four,Six)
    it("SmallStraight() should be 30 (2)") {
        assert(Combination.getPoints(SmallStraight, r5) === 30)
    }

    it("FullStraight() should be 0") {
        assert(Combination.getPoints(FullStraight, r5) === 0)
    }

    val r6 = (Two, One, Three, Four,Five)
    it("FullStraight() should be 40") {
        assert(Combination.getPoints(FullStraight, r6) === 40)
    }

    it("Chance() should be 19") {
        assert(Combination.getPoints(Chance, r4) === 4+5+3+6+1)
    }

    it("Yahtsee() should be 0") {
        assert(Combination.getPoints(Yahtsee, r4) === 0)
    }

    val r7 = (Four, Four, Four, Four,Four)
    it("Yahtsee() should be 50") {
        assert(Combination.getPoints(Yahtsee, r7) === 50)
    }




  }
}