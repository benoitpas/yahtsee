package yahtsee

object InteractionApp extends App {
    val s = Seed()
    println(Interaction.generateRoll(s).unsafeRunSync())
    println(Interaction.generateRoll2(s).unsafeRunSync())
    println(Interaction.generateRoll3(s).unsafeRunSync())

}