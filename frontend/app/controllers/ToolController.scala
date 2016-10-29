package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.actions.{ SecuredRequest, SecuredRequestHandler }
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models.UiTool
import models.services.ToolService
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.mvc.{ Action, AnyContent, Controller, RequestHeader }
import utils.auth.DefaultEnv
import views.support.PageInfo

import scala.concurrent.ExecutionContext.Implicits.global

class ToolController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  socialProviderRegistry: SocialProviderRegistry,
  toolService: ToolService,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  def toolDetail(tool: UiTool)(implicit request: SecuredRequest[DefaultEnv, AnyContent]) = {
    Ok(views.html.tool.toolDetail(
      PageInfo(
        title = Messages("pages.toolDetail.title"),
        user = Some(request.identity)
      ),
      tool
    ))
  }

  def details(ownerHandle: String, toolName: String) = silhouette.SecuredAction.async { implicit request =>
    for {
      tool <- toolService.retrieve(ownerHandle, toolName)
    } yield {
      tool match {
        case Some(t) => toolDetail(t)
        case None => NotFound("Tool not found.")
      }
    }
  }
}
