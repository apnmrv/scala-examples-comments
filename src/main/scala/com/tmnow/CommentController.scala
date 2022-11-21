package com.tmnow

import cats.effect.IO
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.{Http4sDsl, RequestDslBinCompat}

import scala.util.Try

object CommentController extends Http4sDsl[IO] {

  // Import extension methods
  val dsl: Http4sDsl[IO] with RequestDslBinCompat = Http4sDsl[IO]

  def routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case req@POST -> Root / "comments" => ServeReponse.create(req)
    case req@GET -> Root / "comments" => ServeReponse.allComments(req)
    case req@GET -> Root / "comments-by-id" => ServeReponse.commentById(req)
    case req@PUT -> Root / "update-by-id" => ServeReponse.update(req)
    case GET -> Root / "ping" => Ok("pong")
  }

  object ServeReponse {

    val srv = CommentService

    def commentById(req: Request[IO]) = {
      Try {
        req.params("id").toLong
      }.toOption match {
        case Some(id) => srv.getById(id).flatMap {
          case Some(comment) => Ok(comment.asJson)
          case None => NotFound(s"Can not find any comment for id='$id'")
        }
        case None => BadRequest("Can not parse query parameter 'id'")
      }
    }

    // TODO: implement get all comments
    def allComments(req: Request[IO]) = Ok(srv.getAll.map(_.asJson))

    // TODO: implement update
    def update(req: Request[IO]) = {

      Try {
        (req.params("id").toLong, req.params("message"))
      }.toOption match {
        case Some((id, msg)) =>
          srv.update(id, msg)
          Ok(s"Comment $id updated".asJson)
        case None => BadRequest("Can not parse query parameters 'id' and 'message'")
      }
    }

    // TODO: Implement create
    def create(req: Request[IO]) = {
      Try {
        req.params("message")
      }.toOption match {
        case Some(msg) =>
          srv.insert(CommentRow(msg)) match {
            case num: IO[Int] if num == IO.pure(1) => Ok(s"A row has been inserted")
            case _ => NotFound(s"Can not create message '$msg''")
          }
        case None => BadRequest(s"Can not parse message parameter value")
      }
    }
  }
}
