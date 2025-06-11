ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.16"

val AkkaVersion = "2.8.8"
val LogbackVersion = "1.4.14" // Una versione stabile e recente di Logback

lazy val root = (project in file("."))
  .settings(
    name := "dua",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "ch.qos.logback" % "logback-classic" % LogbackVersion // <-- AGGIUNTA
    )
  )