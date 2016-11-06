package client

import org.scalajs.dom

import scala.scalajs.js.JSApp

object toolDetail extends JSApp {
  def main: Unit = {
    dom.document.getElementById("msgBox").innerHTML = "[OK]: Scala.js"
  }
}
