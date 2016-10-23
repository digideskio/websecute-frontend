package forms

import play.api.data.Form
import play.api.data.Forms._

/**
 * The form which handles creating and editing tools.
 */
object UpsertToolForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "sourceCode" -> nonEmptyText,
      "readmeMd" -> nonEmptyText,
      "title" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  case class Data(
    name: String,
    sourceCode: String,
    readmeMd: String,
    title: String)
}