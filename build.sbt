Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val sbtAssemblySettings = baseAssemblySettings ++ Seq(
  assembly / assemblyOutputPath    := baseDirectory.value / "hangman.jar",
  assembly / assemblyMergeStrategy := {
    case PathList("META-INF", _ @_*) => MergeStrategy.discard
    case _                           => MergeStrategy.first
  }
)

lazy val root = (project in file("."))
  .settings(
    name                := "Hangman",
    scalaVersion        := "2.13.8",
    version             := "0.1.0",
    sbtAssemblySettings,
    scalacOptions      ++= Seq("-deprecation", "-unchecked", "-feature", "-Ymacro-annotations"),
    libraryDependencies += "org.eclipse.jetty"  % "jetty-server"    % "11.0.11",
    libraryDependencies += "org.eclipse.jetty"  % "jetty-servlet"   % "11.0.11",
    libraryDependencies += "org.scalactic"     %% "scalactic"       % "3.2.12"   % Test,
    libraryDependencies += "org.scalatest"     %% "scalatest"       % "3.2.12"   % Test,
    libraryDependencies += "org.scalacheck"    %% "scalacheck"      % "1.16.0"   % Test,
    libraryDependencies += "org.scalatestplus" %% "scalacheck-1-16" % "3.2.12.0" % Test
  )

Test / parallelExecution := false
