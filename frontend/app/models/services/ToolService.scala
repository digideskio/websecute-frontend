package models.services

import models.{ DbTool, Page, UiTool }

import scala.concurrent.Future

trait ToolService {
  def upsert(tool: DbTool): Future[Int]

  def retrieve(ownerHandle: String, name: String): Future[Option[UiTool]]

  def listAll(page: Int = 0, filter: String = "%"): Future[Page[UiTool]]
}
