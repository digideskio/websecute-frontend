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
    def name = column[Option[String]]("Name")
    def handle = column[Option[String]]("Handle")
    def bio = column[Option[String]]("Bio")
    def email = column[Option[String]]("Email")
    def avatarURL = column[Option[String]]("AvatarURL")
    def activated = column[Boolean]("Activated")

    def * = (userID, providerID, providerKey, name, handle, bio, email, avatarURL, activated) <> (DbUser.tupled, DbUser.unapply)
    def idx = index("idx_handle", handle, unique = true)
  }

  class Tools(tag: Tag) extends Table[DbTool](tag, "Tools") {
    def name = column[String]("Name", O.PrimaryKey) // name used in url
    def ownerHandle = column[String]("OwnerHandle", O.PrimaryKey)
    def reviewerHandle = column[Option[String]]("ReviewerHandle")
    def sourceCode = column[String]("SourceCode")
    def readmeMd = column[String]("ReadmeMd")
    def readmeHtml = column[String]("ReadmeHtml")
    def title = column[String]("Title")
    def created = column[Timestamp]("Created")

    def * = (name, ownerHandle, reviewerHandle, sourceCode, readmeMd, readmeHtml, title, created) <> (DbTool.tupled, DbTool.unapply)
  }

  class Dependencies(tag: Tag) extends Table[DbDependency](tag, "Dependencies") {
    def id = column[Long]("DependencyID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("Name")

    def * = (id, name) <> (DbDependency.tupled, DbDependency.unapply)
  }

  val usersTbl = TableQuery[Users]
  val toolsTbl = TableQuery[Tools]
}