ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.16"

val AkkaVersion = "2.8.8"
val LogbackVersion = "1.4.14"
val ScalaTestVersion = "3.2.19" // Versione stabile di ScalaTest

lazy val root = (project in file("."))
  .settings(
    name := "dua",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "ch.qos.logback" % "logback-classic" % LogbackVersion,

      // --- DIPENDENZE DI TEST ---
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
      "org.scalatest" %% "scalatest" % ScalaTestVersion % Test
    )
  )