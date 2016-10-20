package views.support

case class PageInfo(
  title: String = "default title",
  url: String = "",
  description: String = "",
  user: Option[models.User] = None
)
