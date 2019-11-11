package yahtsee

import cats.effect.IO

object Interaction {
    def readIntList(): IO[List[Int]] = {
        val io = IO(scala.io.StdIn.readLine)
        readIntList(io)
    }

    def readIntList(io:IO[String]) : IO[List[Int]] = for {
        l <- io
    } yield l match {
        case "" => List()
        case _ => l.split(",").map(_.toInt).toList
    }


    // Simple unpure implementation
    def generateRoll(s:Seed) = IO {
        val r1 = RandomGen.nextRoll.run(s).value
        println(r1._2)
        val l1 = readIntList().unsafeRunSync()
        val r2 = RandomGen.updateRoll(r1._2,l1).run(r1._1).value
        println(r2._2)
        val l2 = readIntList().unsafeRunSync()
        val r3 = RandomGen.updateRoll(r2._2,l2).run(r2._1).value
        println(r3._2)
        r3
    }

    // flatmap on the state 'Seed' monad
    import Roll.Roll
    def generateRoll2(s1:Seed) : IO[(Seed,Roll)] = {
        val r1 = RandomGen.nextRoll.run(s1).value
        (IO {println(r1._2)}).flatMap( 
            _ => readIntList.flatMap(
                l1 => { 
                    val r2 = RandomGen.updateRoll(r1._2,l1).run(r1._1).value
                    (IO { println(r2._2)}).flatMap(
                        _ => readIntList.flatMap(
                            l2 => { 
                                val r3 = RandomGen.updateRoll(r2._2,l2).run(r2._1).value
                                (IO { println(r3._2)}).map(_ => r3)
                            }
                        )
                    )
                
                }
            )
        )
    }

    // trying to use StateT monad transformer to mix IO and Seed monads
    def request(r: Roll, seed: Seed): IO[(Seed,Roll)] = 
        for {
            _ <- IO {println(r) }
            l <- readIntList()
        } yield RandomGen.updateRoll(r,l).run(seed).value 


    import cats.data.StateT
    def requestWithState(r: Roll): StateT[IO, Seed, (Seed,Roll)] = for {
        seed <- StateT.get[IO, Seed]
        resp <- StateT.liftF(request(r, seed))
        _ <- StateT.modify[IO, Seed](_ => resp._1)
    } yield resp

    def generateRoll3(s:Seed) = {
        val r = RandomGen.nextRoll.run(Seed()).value
        (for {
            _ <- StateT.modify[IO,Seed](_ => r._1)
            resp1 <- requestWithState(r._2)
            resp2 <- requestWithState(resp1._2)
         } yield resp2).run(r._1)
    }
}
