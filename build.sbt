
val kamonVersion = "1.0.0-RC4"

val commonSettings = Seq(
  scalaVersion := "2.12.4",
  resolvers += Resolver.bintrayRepo("kamon-io", "snapshots"),
  libraryDependencies ++= Seq(
    "io.kamon" %% "kamon-core" % kamonVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3"
  )
)

lazy val simple = (project in file("simple"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
    )
  ).enablePlugins(JavaAppPackaging)

lazy val customReporter = (project in file("custom-reporter"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
    )
  )

lazy val customCollector = (project in file("custom-collector"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
    )
  )