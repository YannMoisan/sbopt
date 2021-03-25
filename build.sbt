import sbt.Keys._

organization := "com.yannmoisan"
crossScalaVersions := Seq("2.12.13", "2.13.3")

scalafmtOnCompile := true

libraryDependencies += "org.scalameta" %% "munit" % "0.7.22" % Test

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

publishTo := {
  Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  )
}
publishMavenStyle := true
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

publishTo := sonatypePublishToBundle.value

import sbtrelease.ReleaseStateTransformations._

releaseCrossBuild := true

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
