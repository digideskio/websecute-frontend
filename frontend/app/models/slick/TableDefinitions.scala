package models.slick

import java.sql.Timestamp
import java.util.UUID

import models.{ DbDependency, DbTool, DbUser }
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

  class Tools(tag: Tag) extends Table[DbTool](tag, "Tools") {
    def id = column[Long]("ToolID", O.PrimaryKey, O.AutoInc)
    def userID = column[String]("UserID")
    def reviewerID = column[Option[String]]("ReviewerID")
    def variables = column[Option[String]]("Variables")
    def name = column[String]("Name")
    def description = column[String]("Description")
    def script = column[String]("Script")
    def extension = column[String]("Extension")
    def created = column[Timestamp]("Created")

    def * = (id, userID, reviewerID, variables, name, description, script, extension, created) <> (DbTool.tupled, DbTool.unapply)
  }

  class Dependencies(tag: Tag) extends Table[DbDependency](tag, "Dependencies") {
    def id = column[Long]("DependencyID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("Name")

    def * = (id, name) <> (DbDependency.tupled, DbDependency.unapply)
  }

  val usersTbl = TableQuery[Users]
  val toolsTbl = TableQuery[Tools]
}