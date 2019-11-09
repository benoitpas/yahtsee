package yahtsee

import org.scalatest.FunSpec
import cats.effect.IO

class InteractionSpec extends FunSpec {
    describe("Interaction") {
        it("should read a list") {
            def io  = IO[String] {
                "1,2,3"
            }

            assert(Interaction.readIntList(io).unsafeRunSync == List(1,2,3))
            assert(Interaction.readIntList(io).attempt.unsafeRunSync == Right(List(1,2,3)))
        }

        it("should fail to read a list") {
            def io  = IO[String] {
                "1,2,3,l"
            }

            assert(Interaction.readIntList(io).attempt.unsafeRunSync.isLeft)
        }

        it("return an empty list for an empty string") {
            def io  = IO[String] {
                ""
            }

            assert(Interaction.readIntList(io).unsafeRunSync == List())
        }

    }
}