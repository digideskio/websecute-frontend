package models.services

import javax.inject.Inject

import models.daos.ToolDAO

class ToolServiceImpl @Inject() (toolDAO: ToolDAO) extends ToolService {

  def retrieve(ownerHandle: String, name: String) = toolDAO.retrieve(ownerHandle, name)

  def list(page: Int = 0, filter: String = "%", onlyReviewed: Boolean = true, ownerHandle: Option[String] = None) = toolDAO.list(page, filter, onlyReviewed, ownerHandle)
}
