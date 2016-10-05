package models

import java.sql.Timestamp
import java.util.UUID

/**
 *
 * @param toolID The unique ID of the tool.
 * @param creator ID of the tool's creator.
 * @param reviewer Optional ID of the administrator who has approved the tool.
 * @param variables Variables used by the tool's script.
 * @param name Tool name.
 * @param description Tool description.
 * @param toolBody The script.
 * @param outputExt Output extension.
 * @param created Tool creation date.
 */
case class UiTool(
  toolID: Long,
  creator: UiUser,
  reviewer: Option[UiUser],
  variables: Option[String],
  name: String,
  description: String,
  toolBody: String,
  outputExt: String,
  created: Timestamp
) {
  def this(t: DbTool, u: DbUser) = this(t.toolID, UiUser(u.userID, u.fullName.get, u.avatarURL), None, t.variables, t.name, t.description, t.toolBody, t.outputExt, t.created)
}

object UiTool {
  def apply(t: DbTool, u: DbUser) = new UiTool(t, u)
}

/**
 *
 * @param toolID The unique ID of the tool.
 * @param userID ID of the tool's creator.
 * @param reviewerID Optional ID of the administrator who has approved the tool.
 * @param variables Variables used by the tool's script.
 * @param name Tool name.
 * @param description Tool description.
 * @param toolBody The script.
 * @param outputExt Output extension.
 * @param created Tool creation date.
 */
case class DbTool(
  toolID: Long,
  userID: UUID,
  reviewerID: Option[String],
  variables: Option[String],
  name: String,
  description: String,
  toolBody: String,
  outputExt: String,
  created: Timestamp
)