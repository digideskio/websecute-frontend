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

  /**
   * Creates a new tool.
   *
   * @param creatorId The tool's creator.
   * @param tool The tool to be saved.
   * @return The saved tool.
   */
  def create(creatorId: UUID, tool: forms.UpsertToolForm.Data): Future[DbTool]

  /**
   * Deletes an existing tool.
   *
   * @param id An ID of the tool to be deleted.
   */
  def delete(id: Long)

  /**
   * Reviews an existing tool.
   *
   * @param toolID An ID of the tool to be reviewed.
   * @param reviewerID an ID of the person who has reviewed the tool.
   */
  def review(toolID: Long, reviewerID: String)

  /**
   * Loads a list of tools.
   *
   * @param page Page number (starts from 0).
   * @param filter filter used on tool names.
   * @param reviewed True: view only reviewed tools; false: view only non-reviewed tools
   * @param ownerID If reviewed is false, view only owner's unreviewed tools.
   */
  def list(page: Int, filter: String, reviewed: Boolean = true, ownerID: Option[UUID]): Future[Page[UiTool]]

  /**
   * Retrieves a tool and its author's public profile.
   *
   * @param id The searched tool's ID.
   * @return Maybe a tuple of the searched tool and its author's public profile.
   */
  def retrieve(id: Long): Future[UiTool]
}