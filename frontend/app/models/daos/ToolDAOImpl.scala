package models.daos

import java.sql.Timestamp
import java.util.UUID
import javax.inject.Inject

import models.{ DbTool, DbUser, Page, UiTool }
import models.slick.TableDefinitions
import com.github.rjeschke.txtmark.Processor
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the tool object using Slick
 */
class ToolDAOImpl @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends ToolDAO with TableDefinitions with HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  def save(tool: DbTool): Future[DbTool] = {
    ??? // TODO
  }

  def create(ownerHandle: String, tool: forms.UpsertToolForm.Data): Future[DbTool] = {
    val newTool = DbTool(
      name = tool.name, // This field is ignored because of the AutoInc flag in TableDefinitions
      ownerHandle = ownerHandle,
      reviewerHandle = None,
      sourceCode = tool.sourceCode,
      readmeMd = tool.readmeMd,
      readmeHtml = Processor.process(tool.readmeMd),
      title = tool.title,
      created = new Timestamp(System.currentTimeMillis())
    )

    val action = (toolsTbl returning toolsTbl) += newTool

    db.run(action)
  }

  def delete(name: String, ownerHandle: String) = db.run(toolsTbl.filter(t => t.name === name && t.ownerHandle === ownerHandle).delete)

  def review(ownerHandle: String, name: String, reviewerHandle: String) =
    db.run(
      toolsTbl.filter(t => t.name === name && t.ownerHandle === ownerHandle)
        .map(t => (t.ownerHandle, t.name, t.reviewerHandle))
        .update((ownerHandle, name, Some(reviewerHandle)))
    )

  def list(page: Int = 0, filter: String = "%", onlyReviewed: Boolean = true, ownerHandle: Option[String] = None): Future[Page[UiTool]] = {
    val pageSize: Long = 10
    val offset: Long = 10 * page.toLong

    def toolFilter(t: ToolDAOImpl.this.Tools) = accessFilter(t) && (t.title.toLowerCase like "%" + filter.toLowerCase + "%")

    def accessFilter(t: ToolDAOImpl.this.Tools) =
      if (onlyReviewed) t.reviewerHandle.nonEmpty // View only reviewed tools
      else if (ownerHandle.isEmpty) t.reviewerHandle.isEmpty // If no user is given, view all reviewed tools
      else t.reviewerHandle.isEmpty && t.ownerHandle === ownerHandle.get // View user's unreviewed tools

    val query = (for {
      t <- toolsTbl if toolFilter(t)
      u <- usersTbl if t.ownerHandle === u.handle
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

  def retrieve(ownerHandle: String, name: String): Future[UiTool] =
    for {
      t <- db.run(toolsTbl.filter(t => t.ownerHandle === ownerHandle && t.name === name).result.head)
      u <- db.run(usersTbl.filter(_.handle === t.ownerHandle).result.head)
    } yield UiTool(t, u)
}