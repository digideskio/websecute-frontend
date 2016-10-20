package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models.daos.ToolDAOImpl
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.mvc.Controller
import utils.auth.DefaultEnv
import views.support.PageInfo

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * The controller for browsing existing tools.
 *
 * @param messagesApi The Play messages API.
 * @param silhouette The Silhouette stack.
 * @param socialProviderRegistry The social provider registry.
 * @param webJarAssets The webjar assets implementation.
 */
class BrowseToolsController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  socialProviderRegistry: SocialProviderRegistry,
  toolDAOImpl: ToolDAOImpl,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Handles the view action.
   *
   * @return The result to display.
   */
  def view(pageNo: Int, filter: String) = silhouette.SecuredAction.async { implicit request =>
    toolDAOImpl.list(page = pageNo, filter = filter) map { p =>
      Ok(views.html.tools.browse(
        PageInfo(
          title = Messages("tools.browse.title"),
          user = Some(request.identity)),
        p
      ))
    }
  }
}
