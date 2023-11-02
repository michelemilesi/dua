import Dependencies._

ThisBuild / name := "dua"

ThisBuild / organization := "it.michele.milesi"

ThisBuild / scalaVersion := Versions.scala3

ThisBuild / libraryDependencies += "io.circe" %% "circe-yaml-v12" % "0.15.0-RC1"
ThisBuild / libraryDependencies += "org.jmotor" %% "scala-i18n" % "1.0.9"
ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

ThisBuild / coverageFailOnMinimum := true
ThisBuild / coverageMinimumStmtTotal := 80
ThisBuild / coverageMinimumBranchTotal := 80


