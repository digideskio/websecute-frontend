import sbt.Project.projectToRef

lazy val scalaV = "2.11.8"

lazy val clients = Seq(client)

lazy val frontend = (project in file("frontend")).settings(
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    compile in Compile <<= (compile in Compile) dependsOn scalaJSPipeline
  ).
  enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(
    scalaVersion := scalaV,
    name := "client_toolDetail",
    version := "0.1-SNAPSHOT",
    persistLauncher in Compile := true,
    persistLauncher in Test := false,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1"
    ),
    persistLauncher := true,
    persistLauncher in Test := false
  ).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
    dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js


onLoad in Global := (Command.process("project frontend", _: State)) compose (onLoad in Global).value


fork in run := true
