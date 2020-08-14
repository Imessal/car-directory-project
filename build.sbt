name := """car-directory-project"""
organization := "none"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "none.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "none.binders._"
