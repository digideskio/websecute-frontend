package models

import java.sql.Timestamp
import java.util.UUID

case class UiTool(
  name: String,
  owner: UiUser,
  reviewer: Option[UiUser],
  sourceCode: String,
  readmeMd: String,
  readmeHtml: String,
  title: String,
  created: Timestamp
) {
  def this(t: DbTool, u: DbUser) = this(
    name = t.name,
    owner = UiUser(
      userID = u.userID,
      name = u.name.get,
      handle = u.handle.get,
      avatarURL = u.avatarURL
    ),
    reviewer = None,
    sourceCode = t.sourceCode,
    readmeMd = t.readmeMd,
    readmeHtml = t.readmeHtml,
    title = t.title,
    created = t.created)
}

object UiTool {
  def apply(t: DbTool, u: DbUser) = new UiTool(t, u)
}

case class DbTool(
  name: String,
  ownerHandle: String,
  reviewerHandle: Option[String],
  sourceCode: String,
  readmeMd: String,
  readmeHtml: String,
  title: String,
  created: Timestamp
)
