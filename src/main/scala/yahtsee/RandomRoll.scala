package yahtsee

import cats.data.State

case class Seed(r:java.util.Random = new java.util.Random()) {
    val int = r.nextInt()
    def next = Seed(r)
}

import Roll.Roll

object RandomGen {
    val nextInt: State[Seed, Int] = State(seed => (seed.next, seed.int))

    def toDice(i: Int) = Dice(math.abs(i) % 6)

    def nextRoll = for (
        v1 <- nextInt;
        v2 <- nextInt;
        v3 <- nextInt; 
        v4 <- nextInt;  
        v5 <- nextInt) 
        yield (toDice(v1), toDice(v2), toDice(v3), toDice(v4), toDice(v5))

    def nextRoll2 = nextInt flatMap ( 
        v1 => nextInt flatMap (
            v2 => nextInt flatMap (
                v3 => nextInt flatMap (
                    v4 => nextInt map (
                        v5 => (toDice(v1),toDice(v2),toDice(v3),toDice(v4),toDice(v5)))))))
        
} 
