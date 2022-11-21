package com.tmnow

import cats.effect.IO

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

object CommentService {

  val dao: CommentDAO.type = CommentDAO

  implicit def ioFromFuture[A](fut: Future[A]): IO[A] = IO.fromFuture(IO(fut))

  def insert(row: CommentRow): IO[Int] = dao.insert(row)

  def getAll: IO[List[CommentRow]] = dao.getAll.map(_.toList)

  def getById(id: Long): IO[Option[CommentRow]] = dao.getById(id).map(_.headOption)

  def update(id: Long, name: String): IO[Int] = dao.update(id, name)

  def delete(id: Long): IO[Int] = dao.delete(id)

}
