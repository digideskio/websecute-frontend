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

  def save(tool: DbTool): Future[DbTool] = ???

  def upsert(tool: DbTool): Future[Int] = {
    val action = toolsTbl.insertOrUpdate(tool)
    db.run(action)
  }

  def create(tool: DbTool): Future[DbTool] = {
    val action = (toolsTbl returning toolsTbl) += tool
    db.run(action)
  }

  def delete(name: String, ownerHandle: String) = db.run(toolsTbl.filter(t => t.name === name && t.ownerHandle === ownerHandle).delete)

  def review(ownerHandle: String, name: String, reviewerHandle: String) =
    db.run(
      toolsTbl.filter(t => t.name === name && t.ownerHandle === ownerHandle)
        .map(t => (t.ownerHandle, t.name, t.reviewerHandle))
        .update((ownerHandle, name, Some(reviewerHandle)))
    )

  def listAll(page: Int = 0, filter: String = "%") = list(page, filter, _ => true)

  def listReviewed(page: Int = 0, filter: String = "%") = list(page, filter, t => t.reviewerHandle.nonEmpty)

  def listUsersUnreviewed(page: Int = 0, filter: String = "%", ownerHandle: String) = {
    list(page, filter, t => t.reviewerHandle.isEmpty && t.ownerHandle === ownerHandle)
  }

  def listAllUnreviewed(page: Int = 0, filter: String = "%") = list(page, filter, t => t.reviewerHandle.isEmpty)

  //def list(page: Int = 0, filter: String = "%", onlyReviewed: Boolean = true, ownerHandle: Option[String] = None): Future[Page[UiTool]] = {
  def list(page: Int = 0, filter: String = "%", accessFilter: ToolDAOImpl.this.Tools => Rep[Boolean]): Future[Page[UiTool]] = {
    val pageSize: Long = 10
    val offset: Long = 10 * page.toLong

    def toolFilter(t: ToolDAOImpl.this.Tools) = accessFilter(t) && (t.title.toLowerCase like "%" + filter.toLowerCase + "%")

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

  def retrieve(ownerHandle: String, name: String): Future[Option[UiTool]] = {
    val q = for {
      (t, u) <- toolsTbl.filter(_.name === name) join usersTbl.filter(_.handle === ownerHandle) on (_.ownerHandle === _.handle)
    } yield (t, u)

    for {
      seq <- db.run(q.result)
    } yield seq.headOption match {
      case Some(tu) => Some(UiTool(tu._1, tu._2))
      case None => None
    }
  }
}