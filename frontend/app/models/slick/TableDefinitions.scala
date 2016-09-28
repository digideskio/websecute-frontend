package models.slick

import java.sql.Timestamp
import java.util.UUID

import models.DbUser
import slick.driver.JdbcProfile

trait TableDefinitions {
  protected val driver: JdbcProfile
  import driver.api._

  class Users(tag: Tag) extends Table[DbUser](tag, "Users") {
    def userID = column[UUID]("UserID")
    def providerID = column[String]("ProviderID", O.PrimaryKey)
    def providerKey = column[String]("ProviderKey", O.PrimaryKey)
    def firstName = column[Option[String]]("FirstName")
    def lastName = column[Option[String]]("LastName")
    def fullName = column[Option[String]]("FullName")
    def email = column[Option[String]]("Email")
    def avatarURL = column[Option[String]]("AvatarURL")
    def activated = column[Boolean]("Activated")

    def * = (userID, providerID, providerKey, firstName, lastName, fullName, email, avatarURL, activated) <> (DbUser.tupled, DbUser.unapply)
  }

  val usersTbl = TableQuery[Users]
}