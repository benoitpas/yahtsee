package yahtsee
import org.scalatest.FunSpec
import cats.Eval

class RandomGenSpec extends FunSpec {
    describe("RandomGen") {

        val s = Seed(new java.util.Random(0))
        val r = RandomGen.nextInt.run(s)

        it("First int should be -1155484576") {
            assert(r.value._2 === -1155484576)
        }

        val r2 = RandomGen.nextInt.run(r.value._1)

        it("Second int should be -723955400") {
            assert(r2.value._2 === -723955400)
        }

        val r3 = RandomGen.nextInt.run(r2.value._1)

        it("Third int should be 1033096058") {
            assert(r3.value._2 === 1033096058)
        }
    }
}