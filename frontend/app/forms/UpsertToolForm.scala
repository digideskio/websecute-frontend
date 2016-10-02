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
      "description" -> nonEmptyText,
      "script" -> nonEmptyText,
      "variables" -> optional(text),
      "extension" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  /**
   * The form data.
   *
   * @param name The name of a tool.
   * @param description The description of a tool.
   * @param script The script.
   * @param variables The script's variables and their default values.
   * @param extension The extension of the script's output.
   */
  case class Data(
    name: String,
    description: String,
    script: String,
    variables: Option[String],
    extension: String)
}