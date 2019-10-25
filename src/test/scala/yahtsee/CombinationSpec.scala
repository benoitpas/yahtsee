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

  
  }
}