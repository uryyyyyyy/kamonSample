val kamonVersion = "1.0.0"

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

lazy val systemMetricsSample = (project in file("system-metrics-sample"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "io.kamon" %% "kamon-system-metrics" % "1.0.0"
    )
  ).enablePlugins(JavaAppPackaging)

lazy val futureSample = (project in file("future-sample"))
  .settings(commonSettings: _*)
  .enablePlugins(JavaAppPackaging, JavaAgent)
  .settings(
    javaAgents += "org.aspectj" % "aspectjweaver" % "1.8.13" % "runtime",
    libraryDependencies ++= Seq(
      "io.kamon" %% "kamon-scala-future" % "1.0.0",
      "io.kamon" %% "kamon-scalaz-future" % "1.0.0",
      "io.kamon" %% "kamon-twitter-future" % "1.0.0"
    )
  )

lazy val executorSample = (project in file("executor-sample"))
  .settings(commonSettings: _*)
  .enablePlugins(JavaAppPackaging, JavaAgent)
  .settings(
    javaAgents += "org.aspectj" % "aspectjweaver" % "1.8.13" % "runtime",
    libraryDependencies ++= Seq(
      "io.kamon" %% "kamon-executors" % "1.0.1"
    )
  )

lazy val akkaSample = (project in file("akka-sample"))
  .settings(commonSettings: _*)
  .enablePlugins(JavaAppPackaging, JavaAgent)
  .settings(
    javaAgents += "org.aspectj" % "aspectjweaver" % "1.8.13" % "runtime",
    libraryDependencies ++= Seq(
      "io.kamon" %% "kamon-akka-2.5" % "1.0.0",
      "com.typesafe.akka" %% "akka-actor"      % "2.5.8",
    )
  )