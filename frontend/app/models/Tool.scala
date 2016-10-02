package models

import java.sql.Timestamp

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
)

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
  userID: String,
  reviewerID: Option[String],
  variables: Option[String],
  name: String,
  description: String,
  toolBody: String,
  outputExt: String,
  created: Timestamp
)