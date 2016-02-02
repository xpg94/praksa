name := """table4you"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1207.jre7"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.38"
libraryDependencies += "org.apache.commons" % "commons-email" % "1.4"
libraryDependencies += "org.glassfish.jersey.media" % "jersey-media-json-jackson" % "2.22.1"
libraryDependencies += "joda-time" % "joda-time" % "2.9.2"
libraryDependencies += "commons-codec" % "commons-codec" % "1.10"
libraryDependencies += "commons-validator" % "commons-validator" % "1.5.0"



// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)