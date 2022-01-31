import sbt.Keys._

organization       := "com.yannmoisan"
crossScalaVersions := Seq("2.12.15", "2.13.6")

scalafmtOnCompile := true

libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test

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

publishArtifact in Test := false
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
