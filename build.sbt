import sbt.Keys._

val Scala_2_12 = "2.12.21"
val Scala_2_13 = "2.13.18"

organization       := "com.yannmoisan"
scalaVersion       := Scala_2_12
crossScalaVersions := Seq(Scala_2_12, Scala_2_13)

scalafmtOnCompile := true

libraryDependencies += "org.scalameta" %% "munit" % "1.3.5" % Test

// Use %%% for non-JVM projects.
testFrameworks += new TestFramework("munit.Framework")

startYear := Some(2020)
licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/yannmoisan/sbopt"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/yannmoisan/sbopt"),
    "git@github.com:yannmoisan/sbopt.git"
  )
)

Test / publishArtifact := false
pomIncludeRepository := { _ =>
  false
}
pomExtra :=
  <developers>
    <developer>
      <id>yannmoisan</id>
      <name>Yann Moisan</name>
      <url>http://github.com/yannmoisan</url>
    </developer>
  </developers>
