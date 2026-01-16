val scalaJSVersion = sys.env.getOrElse("SCALAJS_VERSION", "1.20.2")

lazy val `scalajs-bundler-linker` =
  project.in(file("scalajs-bundler-linker"))
    .settings(
      scalaVersion := "2.12.21",
      libraryDependencies += "org.scala-js" %% "scalajs-linker" % scalaJSVersion
    )

val `sbt-scalajs-bundler` =
  project.in(file("sbt-scalajs-bundler"))
    .enablePlugins(SbtPlugin, BuildInfoPlugin)
    .settings(commonSettings)
    .settings(
      description := "Module bundler for Scala.js projects",
      libraryDependencies += "com.google.jimfs" % "jimfs" % "1.3.1",
      libraryDependencies += "org.playframework" %% "play-json" % "3.0.6",
      addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion),
      buildInfoKeys := Seq[BuildInfoKey](version),
      buildInfoPackage := "scalajsbundler.sbtplugin.internal",
      // When supported, add: buildInfoOptions += sbtbuildinfo.BuildInfoOption.PackagePrivate
      scriptedDependencies := {
        val () = scriptedDependencies.value
        val () = publishLocal.value
        val () = (`scalajs-bundler-linker` / publishLocal).value
      },
    )

val `sbt-web-scalajs-bundler` =
  project.in(file("sbt-web-scalajs-bundler"))
    .enablePlugins(SbtPlugin)
    .settings(commonSettings)
    .settings(
      // sbt-web-scalajs does not support sbt 1.2.x
      scriptedDependencies := {
        val () = scriptedDependencies.value
        val () = publishLocal.value
        val () = (`sbt-scalajs-bundler` / publishLocal).value
        val () = (`scalajs-bundler-linker` / publishLocal).value
      },
      description := "Module bundler for Scala.js projects (integration with sbt-web-scalajs)",
      addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.3.0")
    )
    .dependsOn(`sbt-scalajs-bundler`)

// Dummy project that exists just for the purpose of aggregating the two sbt
// plugins. I can not do that in the `doc` project below because the
// scalaVersion is not compatible.
val apiDoc =
  project.in(file("api-doc"))
    .settings(noPublishSettings: _*)
    .aggregate(`sbt-scalajs-bundler`, `sbt-web-scalajs-bundler`)

val ornateTarget = Def.setting(target.value / "ornate")

val `scalajs-bundler` =
  project.in(file("."))
    .settings(noPublishSettings: _*)
    .aggregate(`sbt-scalajs-bundler`, `sbt-web-scalajs-bundler`)

inThisBuild(List(
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-encoding", "UTF-8",
    "-unchecked",
    "-Xlint",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  ),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/scalacenter/scalajs-bundler"),
      "scm:git@github.com:scalacenter/scalajs-bundler.git"
    )
  ),
  organization := "ch.epfl.scala",
  homepage := Some(url(s"https://github.com/scalacenter/scalajs-bundler")),
  licenses := Seq("MIT License" -> url("http://opensource.org/licenses/mit-license.php")),
  developers := List(Developer("julienrf", "Julien Richard-Foy", "julien.richard-foy@epfl.ch", url("http://julien.richard-foy.fr")))
))

lazy val commonSettings = List(
  scriptedLaunchOpts ++= Seq(
    "-Dplugin.version=" + version.value,
    s"-Dscalajs.version=$scalaJSVersion",
    "-Dsbt.execute.extrachecks=true" // Avoid any deadlocks.
  ),
  scriptedBufferLog := false,
  scalaVersion := "2.12.21"
)

lazy val noPublishSettings =
  Seq(
    publishArtifact := false,
    publish := {},
    publishLocal := {},
    scalaVersion := "2.12.21"
  )

ThisBuild / ivyLoggingLevel := UpdateLogging.Quiet
