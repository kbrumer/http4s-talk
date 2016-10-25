package demo

import doobie.imports._
import org.http4s.server.blaze._

import scalaz.concurrent.Task

object Main extends App {
  val xa = DriverManagerTransactor[Task](
    "org.postgresql.Driver", "jdbc:postgresql:demo", "postgres", "db#2700"
  )

  val server =
    BlazeBuilder
      .bindHttp(8080)
      .mountService(DemoService.service(xa))
      .run

  server.awaitShutdown()
}
