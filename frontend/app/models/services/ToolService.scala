package models.services

import com.mohiva.play.silhouette.api.services.IdentityService
import models.{ Page, UiTool, User }

import scala.concurrent.Future

trait ToolService {

  def retrieve(ownerHandle: String, name: String): Future[Option[UiTool]]

  def list(page: Int = 0, filter: String = "%", onlyReviewed: Boolean = true, ownerHandle: Option[String] = None): Future[Page[UiTool]]
}
