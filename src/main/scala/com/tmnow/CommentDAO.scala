package com.tmnow

import com.tmnow.CommentSchema.db

import java.time.Instant

object CommentDAO {

  val S = CommentSchema

  import S.profile.api._

  def insert(row: CommentRow) = {
    val query = S.comments
    val action = query += row
    db.run(action)
  }

  def delete(id: Long) = {
    val query = S.comments.filter(_.id === id)
    val action = query.delete
    db.run(action)
  }

  def update(id: Long, message: String) = {
    val query = S.comments
      .filter(_.id === id)
      .map(c => (c.message, c.modified))
    val action = query.update((message, Instant.now()))
    db.run(action)
  }

  def getAll = {
    val query = S.comments
    val action = query.result
    db.run(action)
  }

  def getById(id: Long) = {
    val query = S.comments.filter(_.id === id)
    val action = query.result
    db.run(action)
  }
}
