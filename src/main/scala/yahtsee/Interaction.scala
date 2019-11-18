package yahtsee

import cats.effect.IO
import cats.implicits._ 

object Interaction {
    def readIntList(): IO[List[Int]] = {
        val io = IO(scala.io.StdIn.readLine)
        readIntList(io)
    }

    def readIntList(io:IO[String]) : IO[List[Int]] = for {
        l <- io
    } yield l.trim match {
        case "" => List()
        case l2 => l2.split(",").toList.map(_.toInt)
    }
    
/*
    // Better error handling
    def readIntList(io:IO[String]) : IO[Either[NumberFormatException,List[Int]]] = for {
        l <- io
    } yield l match {
        case "" => Either.right(List())
        case _ => l.split(",").toList.traverse((s:String) => Either.catchOnly[NumberFormatException](s.toInt))
    }*/


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

    // flatmap on the IO monad and pass the state throught (pure)
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

    // Use 'for' instead of flatMap on IO monad
    def generateRoll2b(s1:Seed) : IO[(Seed,Roll)] = {
        val r1 = RandomGen.nextRoll.run(s1).value
        for {
            _ <- IO {println(r1._2)}
            l1 <- readIntList
            r2 = RandomGen.updateRoll(r1._2,l1).run(r1._1).value
            _ <- IO {println(r2._2)}
            l2 <- readIntList
            r3 = RandomGen.updateRoll(r2._2,l2).run(r2._1).value
            _ <- IO { println(r3._2)}
        } yield r3
    }

    // trying to use StateT monad transformer to mix IO and Seed monads (pure)
    def firstRoll(seed: Seed) : IO[(Seed,Roll)] = IO {
        RandomGen.nextRoll.run(Seed()).value
    }

    def updateRoll(r: Roll, seed: Seed): IO[(Seed,Roll)] = 
        for {
            _ <- IO {println(r) }
            l <- readIntList()
        } yield RandomGen.updateRoll(r,l).run(seed).value 


    import cats.data.StateT
    def updateRollWithState(r: Roll): StateT[IO, Seed, (Seed,Roll)] = for {
        seed <- StateT.get[IO, Seed]
        resp <- StateT.liftF(updateRoll(r, seed))
        _ <- StateT.modify[IO, Seed](_ => resp._1)
    } yield resp

    def generateRoll3 = 
        for {
            seed <- StateT.get[IO, Seed]
            resp1 <- StateT.liftF(firstRoll(seed))
            _ <- StateT.modify[IO,Seed](_ => resp1._1)
            resp2 <- updateRollWithState(resp1._2)
            resp3 <- updateRollWithState(resp2._2)
         } yield resp3
}
