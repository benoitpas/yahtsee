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

    def generateRoll(s:Seed) = {
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

    
    /*
    import cats.mtl.MonadState
    type MonadStateSeed[F[_]] = MonadState[F, Seed]

    import cats.Eval
    import cats.Monad 
    import cats.data.State
    import cats.effect.LiftIO
    import Roll.Roll

    type Env = String
    type Request = String
    type Response = String
    type Config = String

def initialEnv: Env = ???

def request(r: Request, env: Env): IO[Response] = ???
    type Result = List[Response]

def updateEnv(r: Response, env: Env): Env = ???

def requests: List[Request] = ???

def newServiceCall(c: Config, req: Request, e: Env): IO[Response] = ???

type MonadStateEnv[F[_]] = MonadState[F, Env]
// defined type alias MonadStateEnv

def requestWithState[F[_]: Monad: MonadStateEnv: LiftIO](c: Config, req: Request): F[Response] = for {
  env <- MonadState[F, Env].get
  response <- newServiceCall(c, req, env).to[F]
  _ <- MonadState[F, Env].modify(updateEnv(response, _))
} yield response
// requestWithState: [F[_]](c: Config, req: Request)(implicit evidence$1: cats.Monad[F], implicit evidence$2: MonadStateEnv[F], implicit evidence$3: cats.effect.LiftIO[F])F[Response]

*/
    /*
    def getNewRoll(s: Seed, r: Roll): IO[(Roll,Seed)] = ???
    def updateEnv(s: Seed,r: Roll): Seed = s

    def updatedRoll[F[_]: Monad: MonadStateSeed: LiftIO](r: Roll): F[Roll] = for {
        seed <- MonadState[F, Seed].get
        reponse <- getNewRoll(seed, r).to[F]
        _ <- MonadState[F, Seed].modify(updateEnv(_,reponse.s))
    } yield reponse._1*/
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