package models.daos

import java.sql.Timestamp
import java.util.UUID
import javax.inject.Inject

import models.{ DbTool, DbUser, Page, UiTool }
import models.slick.TableDefinitions
import play.api.Logger
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the tool object using Slick
 */
class ToolDAOImpl @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends ToolDAO with TableDefinitions with HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  /**
   * Saves a tool.
   *
   * @param tool The tool to save.
   * @return The saved tool.
   */
  def save(tool: DbTool): Future[DbTool] = {
    ??? // TODO
  }

  /**
   * Creates a new tool.
   *
   * @param ownerId The tool's creator.
   * @param tool The tool to be saved.
   * @return The saved tool.
   */
  def create(ownerId: UUID, tool: forms.UpsertToolForm.Data): Future[DbTool] = {
    val newTool = DbTool(
      toolID = 0, // This field is ignored because of the AutoInc flag in TableDefinitions
      userID = ownerId,
      reviewerID = None,
      variables = tool.variables,
      name = tool.name,
      description = tool.description,
      toolBody = tool.script,
      outputExt = tool.extension,
      created = new Timestamp(System.currentTimeMillis())
    )

    val action = (toolsTbl returning toolsTbl.map(_.id) into ((tool, id) => newTool.copy(toolID = id))) += newTool
    db.run(action)
  }

  /**
   * Deletes an existing tool.
   *
   * @param id An ID of the tool to be deleted.
   */
  def delete(id: Long) = db.run(toolsTbl.filter(_.id === id).delete)

  /**
   * Reviews an existing tool.
   *
   * @param toolID An ID of the tool to be reviewed.
   * @param reviewerID an ID of the person who has reviewed the tool.
   */
  def review(toolID: Long, reviewerID: String) =
    db.run(
      toolsTbl.filter(_.id === toolID)
        .map(t => (t.id, t.reviewerID))
        .update((toolID, Some(reviewerID)))
    )

  /**
   * Loads a list of tools.
   *
   * @param page Page number (starts from 0).
   * @param filter Filter used on tool names.
   * @param onlyReviewed True: view only reviewed tools; false: view only non-reviewed tools
   * @param ownerId If reviewed is false, view only owner's unreviewed tools.
   */
  def list(page: Int = 0, filter: String = "%", onlyReviewed: Boolean = true, ownerId: Option[UUID] = None): Future[Page[UiTool]] = {
    val pageSize: Long = 10
    val offset: Long = 10 * page.toLong

    def toolFilter(t: ToolDAOImpl.this.Tools) = accessFilter(t) && (t.name.toLowerCase like "%" + filter.toLowerCase + "%")

    def accessFilter(t: ToolDAOImpl.this.Tools) =
      if (onlyReviewed) t.reviewerID.nonEmpty // View only reviewed tools
      else if (ownerId.isEmpty) t.reviewerID.isEmpty // If no user is given, view all reviewed tools
      else t.reviewerID.isEmpty && t.userID === ownerId.get // View user's unreviewed tools

    val query = (for {
      t <- toolsTbl if toolFilter(t)
      u <- usersTbl if t.userID === t.userID
    } yield (t, u)).drop(offset).take(pageSize)

    val totalCntQuery = toolsTbl.filter(toolFilter).length

    val toolsWithOwners: Future[Seq[(DbTool, DbUser)]] = db.run(query.result)
    val uiTools: Future[Seq[UiTool]] = toolsWithOwners.map { s: Seq[(DbTool, DbUser)] =>
      s map {
        case (t: DbTool, u: DbUser) => UiTool(t, u)
      }
    }

    val totalCnt: Future[Int] = db.run(Query(totalCntQuery).result.head)

    for {
      tools <- uiTools
      cnt <- totalCnt
    } yield Page(tools, page, offset, cnt.toLong, fulltextQuery = filter)
  }

  /**
   * Retrieves a tool and its owner's public profile.
   *
   * @param toolId The searched tool's ID.
   * @return Option of the tool in its UI form.
   */
  def retrieve(toolId: Long): Future[UiTool] =
    for {
      t <- db.run(toolsTbl.filter(_.id === toolId).result.head)
      u <- db.run(usersTbl.filter(_.userID === t.userID).result.head)
    } yield UiTool(t, u)
}