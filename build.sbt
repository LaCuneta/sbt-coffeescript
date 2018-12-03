organization := "com.typesafe.sbt"
name := "sbt-coffeescript"
libraryDependencies ++= Seq(
  "org.webjars.npm" % "coffeescript" % "2.3.2",
  "org.webjars" % "mkdirp" % "0.5.0"
)
addSbtJsEngine("1.2.2")
