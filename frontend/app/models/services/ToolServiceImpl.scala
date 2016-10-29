package models.services

import javax.inject.Inject
import scala.concurrent.Future

import models.DbTool
import models.daos.ToolDAO

class ToolServiceImpl @Inject() (toolDAO: ToolDAO) extends ToolService {
  def upsert(tool: DbTool) = toolDAO.upsert(tool)

  def retrieve(ownerHandle: String, name: String) = toolDAO.retrieve(ownerHandle, name)

  def listAll(page: Int = 0, filter: String = "%") = toolDAO.listAll(page, filter)
}
