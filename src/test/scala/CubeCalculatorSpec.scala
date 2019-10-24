import org.scalatest.FunSpec

class CubeCalculatorSpec extends FunSpec {

  describe("CubeCalculator") {

    it("should cube") {
      assert(CubeCalculator.cube(3) === 27)
    }
  }
}