package com.tmnow

import cats.effect._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.HttpApp
import org.http4s.server.middleware.Logger
import org.http4s.server.Router
import org.http4s.server.Server

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    BlazeApp.resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
}

object BlazeApp {
  val app = Logger.httpApp(true, true)(
    Router(
      "/service" -> CommentController.routes
    ).orNotFound
  )

  def resource: Resource[IO, Server] = {
    BlazeServerBuilder[IO]
      .bindHttp(8080)
      .withHttpApp(app)
      .resource
  }
}
