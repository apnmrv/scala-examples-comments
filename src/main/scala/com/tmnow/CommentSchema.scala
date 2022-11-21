package com.tmnow

import io.circe.Encoder
import io.circe.generic.semiauto._
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile
import slick.lifted.ProvenShape

import java.time.Instant

trait PostgresSettings {
  val profile: slick.jdbc.PostgresProfile = PostgresProfile
  val db: slick.jdbc.JdbcBackend.DatabaseDef = Database.forConfig("postgres")
}

case class CommentRow(
                       message: String,
                       created: Instant = Instant.now(),
                       modified: Instant = Instant.now(),
                       id: Long = 0L,
                     )

object CommentRow {
  implicit val jsonFmt: Encoder.AsObject[CommentRow] = deriveEncoder[CommentRow]
}


object CommentSchema extends PostgresSettings {


  import profile.api._

  val comments: TableQuery[CommentTable] = TableQuery[CommentTable]

  class CommentTable(tag: Tag) extends Table[CommentRow](tag, "comment") {

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def message: Rep[String] = column[String]("message")

    def created: Rep[Instant] = column[Instant]("created")

    def modified: Rep[Instant] = column[Instant]("modified")

    def * : ProvenShape[CommentRow] = (message, created, modified, id).shaped <> ((CommentRow.apply _).tupled, CommentRow.unapply)
  }
}
