package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models.services.ToolService
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.mvc.Controller
import utils.auth.DefaultEnv
import views.support.PageInfo

import scala.concurrent.ExecutionContext.Implicits.global

class BrowseToolsController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  toolService: ToolService,
  implicit val webJarAssets: WebJarAssets) extends Controller with I18nSupport {

  val socialProviderRegistry = SocialProviderRegistry

  def view(pageNo: Int, filter: String) = silhouette.SecuredAction.async { implicit request =>
    toolService.listAll(page = pageNo, filter = filter) map { p =>
      Ok(views.html.tool.browse(
        PageInfo(
          title = Messages("tools.browse.title"),
          user = Some(request.identity)),
        p
      ))
    }
  }
}
