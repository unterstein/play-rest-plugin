name := "play-rest-plugin"

version := "0.1.0-SNAPSHOT"

organization := "com.github.unterstein"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.5.3",
  "com.google.code.gson" % "gson" % "2.6.2",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)


publishTo <<= version {
  case v if v.trim.endsWith("SNAPSHOT") => Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
  case _ => Some(Resolver.file("Github Pages",  new File("../unterstein.github.io/repo")))
}
