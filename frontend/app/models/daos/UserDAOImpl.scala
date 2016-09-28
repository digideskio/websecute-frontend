package models.daos

import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import models.slick.TableDefinitions
import models.{ DbUser, User }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
 * Give access to the user object.
 */
class UserDAOImpl @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends UserDAO with TableDefinitions with HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  def find(loginInfo: LoginInfo): Future[Option[User]] = {
    val filterByLInfo = usersTbl.filter(x => x.providerID === loginInfo.providerID && x.providerKey === loginInfo.providerKey)
    db.run(filterByLInfo.result.headOption).flatMap {
      case Some(u) => Future.successful(Some(User(u.userID, LoginInfo(u.providerID, u.providerKey), u.firstName, u.lastName, u.fullName, u.email, u.avatarURL, u.activated)))
      case None => Future.successful(None)
    }
  }

  /**
   * Finds a user by its user ID.
   *
   * @param userID The ID of the user to find.
   * @return The found user or None if no user for the given ID could be found.
   */
  def find(userID: UUID): Future[Option[User]] = {
    val filterById = usersTbl.filter(_.userID === userID)
    db.run(filterById.result.headOption).flatMap {
      case Some(u) => Future.successful(Some(User(u.userID, LoginInfo(u.providerID, u.providerKey), u.firstName, u.lastName, u.fullName, u.email, u.avatarURL, u.activated)))
      case None => Future.successful(None)
    }
  }

  /**
   * Saves a user.
   *
   * @param u The user to save.
   * @return The saved user.
   */
  def save(u: User) = {
    val action = DBIO.seq(usersTbl.insertOrUpdate(DbUser(u.userID, u.loginInfo.providerID, u.loginInfo.providerKey, u.firstName, u.lastName, u.fullName, u.email, u.avatarURL, u.activated)))
    db.run(action).flatMap(r => Future.successful(u))
  }
}

/**
 * The companion object.
 */
object UserDAOImpl {

}
