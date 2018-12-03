lazy val root = (project in file(".")).enablePlugins(SbtWeb)

import sbt.internal.inc.LoggedReporter

WebKeys.reporter := {
  val logFile = target.value / "test-errors.log"
  new LoggedReporter(-1, new Logger {

    def trace(t: => Throwable): Unit = {}

    def success(message: => String): Unit = {}

    def log(level: Level.Value, message: => String): Unit = {
      if (level == Level.Error) {
        IO.append(logFile, message + "\n")
      }
    }
  })
}

val checkTestErrorLogContents = taskKey[Unit]("check that test log contents are correct")
checkTestErrorLogContents := {
  val contents = IO.read(target.value / "test-errors.log").trim.split("\n").mkString("|")
  if (!contents.matches("""\[Error\] com\.typesafe\.sbt\.web\.LinePosition@\w+: unexpected %\|one error found""")) {
    sys.error(s"Unexpected contents: $contents")
  }
}
