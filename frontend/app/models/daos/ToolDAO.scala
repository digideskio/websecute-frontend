package models.daos

import _root_.slick.lifted.Rep
import slick.model.Table
import models._

import scala.concurrent.Future

trait ToolDAO {
  def save(tool: DbTool): Future[DbTool]

  def upsert(tool: DbTool): Future[Int]

  def create(tool: DbTool): Future[DbTool]

  def delete(ownerHandle: String, name: String)

  def review(ownerHandle: String, name: String, reviewerHandle: String)

  def listAll(page: Int, filter: String): Future[Page[UiTool]]

  def retrieve(ownerHandle: String, name: String): Future[Option[UiTool]]
}