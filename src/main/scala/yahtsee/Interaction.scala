package yahtsee

import cats.effect.IO

object Interaction {
    def readIntList(): IO[List[Int]] = {
        val io = IO(scala.io.StdIn.readLine)
        readIntList(io)
    }

    def readIntList(io:IO[String]) : IO[List[Int]] = for {
        l <- io
    } yield l.split(",").map(_.toInt).toList
/*
    def generateRoll() {
        val roll = RandomGen.nextRoll

        for (
            r <- roll;
            l <- readIntList();
            r2 <- newRoll(r,l);
            l2 <- readIntList();
            r3 <- newRoll(r2,l2))
            yield r3
    }*/
}