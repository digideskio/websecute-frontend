package models.daos

import java.util.UUID

import models.{ DbTool, DbUser, Page, UiTool }

import scala.concurrent.Future

trait ToolDAO {
  def save(tool: DbTool): Future[DbTool]

  def create(creatorHandle: String, tool: forms.UpsertToolForm.Data): Future[DbTool]

  def delete(ownerHandle: String, name: String)

  def review(ownerHandle: String, name: String, reviewerHandle: String)

  def list(page: Int, filter: String, onlyReviewed: Boolean = true, ownerHandle: Option[String] = None): Future[Page[UiTool]]

  def retrieve(ownerHandle: String, name: String): Future[Option[UiTool]]
}