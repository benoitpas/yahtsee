package yahtsee

import cats.data.State

case class Seed(r:java.util.Random = new java.util.Random()) {
    val int = r.nextInt()
    def next = Seed(r)
}

object RandomGen {
    val nextInt: State[Seed, Long] = State(seed =>
  (seed.next, seed.int))
}