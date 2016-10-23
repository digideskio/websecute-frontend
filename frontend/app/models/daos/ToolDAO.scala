package models.daos

import java.util.UUID

import models.{ DbTool, DbUser, Page, UiTool }

import scala.concurrent.Future

trait ToolDAO {
  /**
   * Saves a tool.
   *
   * @param tool The tool to save.
   * @return The saved tool.
   */
  def save(tool: DbTool): Future[DbTool]

  def create(creatorHandle: String, tool: forms.UpsertToolForm.Data): Future[DbTool]

  def delete(ownerHandle: String, name: String)

  def review(ownerHandle: String, name: String, reviewerHandle: String)

  def list(page: Int, filter: String, reviewed: Boolean = true, ownerHandle: Option[String]): Future[Page[UiTool]]

  def retrieve(ownerHandle: String, name: String): Future[UiTool]
}