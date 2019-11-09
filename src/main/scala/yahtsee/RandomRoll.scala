package yahtsee

import cats.data.State

case class Seed(r:java.util.Random = new java.util.Random()) {
    val int = r.nextInt()
    def next = Seed(r)
}

import Roll.Roll

object RandomGen {
    val nextInt: State[Seed, Int] = State(seed => (seed.next, seed.int))
    def sameInt(i:Integer): State[Seed,Int] = State(seed => (seed,i))

    def toDice(i: Int) = Dice(math.abs(i) % 6)

    def nextRoll = for (
        v1 <- nextInt;
        v2 <- nextInt;
        v3 <- nextInt; 
        v4 <- nextInt;  
        v5 <- nextInt) 
        yield (toDice(v1), toDice(v2), toDice(v3), toDice(v4), toDice(v5))

    def updateRoll(r:Roll, l:List[Int]) = for (
        v1 <- if (l.contains(1)) nextInt else sameInt(r._1.id);
        v2 <- if (l.contains(2)) nextInt else sameInt(r._2.id);
        v3 <- if (l.contains(3)) nextInt else sameInt(r._3.id);
        v4 <- if (l.contains(4)) nextInt else sameInt(r._4.id);
        v5 <- if (l.contains(5)) nextInt else sameInt(r._5.id))
        yield (toDice(v1), toDice(v2), toDice(v3), toDice(v4), toDice(v5))
    
    def nextRoll2 = nextInt flatMap ( 
        v1 => nextInt flatMap (
            v2 => nextInt flatMap (
                v3 => nextInt flatMap (
                    v4 => nextInt map (
                        v5 => (toDice(v1),toDice(v2),toDice(v3),toDice(v4),toDice(v5)))))))
        
} 
