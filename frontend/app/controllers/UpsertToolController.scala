package controllers

import java.sql.Timestamp
import javax.inject.Inject

import com.github.rjeschke.txtmark.Processor
import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.actions.{ SecuredRequest, SecuredRequestHandler }
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import forms.UpsertToolForm
import models.{ DbTool, UiTool }
import models.services.ToolService
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.mvc.{ Action, AnyContent, Controller, RequestHeader }

import scala.concurrent.Future
import utils.auth.DefaultEnv
import views.support.PageInfo

import scala.concurrent.ExecutionContext.Implicits.global

class UpsertToolController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  socialProviderRegistry: SocialProviderRegistry,
  toolService: ToolService,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  def view = silhouette.SecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.tool.upsertTool(UpsertToolForm.form)))
  }

  def submit = silhouette.SecuredAction.async { implicit request =>
    UpsertToolForm.form.bindFromRequest.fold(
      form => Future.successful(BadRequest(views.html.tool.upsertTool(form))),
      data => {
        val dbTool = DbTool(
          name = data.name, // This field is ignored because of the AutoInc flag in TableDefinitions
          ownerHandle = request.identity.handle.get,
          reviewerHandle = None,
          sourceCode = data.sourceCode,
          readmeMd = data.readmeMd,
          readmeHtml = Processor.process(data.readmeMd),
          title = data.title,
          created = new Timestamp(System.currentTimeMillis())
        )

        //toolService.
        for {
          result <- toolService.upsert(dbTool)
        } yield {
          Redirect(routes.BrowseToolsController.view()).flashing("info" -> Messages("tool.upsert.success"))
        }
      }
    )
  }

}
