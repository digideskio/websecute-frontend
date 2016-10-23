package models

import java.util.UUID

import com.mohiva.play.silhouette.api.{ Identity, LoginInfo }

case class User(
  userID: UUID,
  loginInfo: LoginInfo,
  name: Option[String],
  handle: Option[String],
  bio: Option[String],
  email: Option[String],
  avatarURL: Option[String],
  activated: Boolean) extends Identity

object User {
  def getName(fullName: Option[String], firstName: Option[String], lastName: Option[String]) = fullName.orElse {
    firstName -> lastName match {
      case (Some(f), Some(l)) => Some(f + " " + l)
      case (Some(f), None) => Some(f)
      case (None, Some(l)) => Some(l)
      case _ => None
    }
  }
}

case class DbUser(
  userID: UUID,
  providerID: String,
  providerKey: String,
  name: Option[String],
  handle: Option[String],
  bio: Option[String],
  email: Option[String],
  avatarURL: Option[String],
  activated: Boolean)

case class UiUser(
  userID: UUID,
  name: String,
  handle: String,
  avatarURL: Option[String])