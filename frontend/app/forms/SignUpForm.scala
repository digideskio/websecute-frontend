package forms

import play.api.data.Form
import play.api.data.Forms._

object SignUpForm {
  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "handle" -> nonEmptyText,
      "bio" -> optional(nonEmptyText),
      "email" -> email,
      "password" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  case class Data(
    name: String,
    handle: String,
    bio: Option[String],
    email: String,
    password: String)
}
