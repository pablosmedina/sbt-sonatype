/*
 * Copyright 2012 Taro L. Saito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xerial.sbt

import java.io.File
import sbt._
import Keys._
import sbt.ScriptedPlugin._
import sbtrelease.ReleasePlugin._

object SonatypeBuild extends Build {

  val SCALA_VERSION = "2.10.3"

  import xerial.sbt.Sonatype.SonatypeKeys._

  lazy val buildSettings = Defaults.defaultSettings ++ releaseSettings ++ scriptedSettings ++ xerial.sbt.Sonatype.sonatypeSettings ++ Seq[Setting[_]](
    profileName := "org.xerial",
    organization := "org.xerial.sbt",
    organizationName := "Xerial project",
    organizationHomepage := Some(new URL("http://xerial.org/")),
    description := "A sbt plugin for automating staging processes in Sonatype",
    scalaVersion := SCALA_VERSION,
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := {
      _ => false
    },
    sbtPlugin := true,
    parallelExecution := true,
    scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-target:jvm-1.6"),
    scriptedBufferLog := false,
    scriptedLaunchOpts ++= {
      import scala.collection.JavaConverters._
      management.ManagementFactory.getRuntimeMXBean().getInputArguments().asScala.filter(a => Seq("-Xmx","-Xms").contains(a) || a.startsWith("-XX")).toSeq
    },
    pomExtra := {
      <url>http://xerial.org/</url>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
      </licenses>
        <scm>
          <connection>scm:git:github.com/xerial/sbt-sonatype.git</connection>
          <developerConnection>scm:git:git@github.com:xerial/sbt-sonatype.git</developerConnection>
          <url>github.com/xerial/sbt-sonatype.git</url>
        </scm>
        <developers>
          <developer>
            <id>leo</id>
            <name>Taro L. Saito</name>
            <url>http://xerial.org/leo</url>
          </developer>
        </developers>
    }
  )


  // Project modules
  lazy val sbtSonatype = Project(
    id = "sbt-sonatype",
    base = file("."),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= Seq(
        //"io.spray" % "spray-client" % "1.2.0",
        //"com.typesafe.akka" %% "akka-actor" % "2.2.3",
        "org.apache.httpcomponents" % "httpclient" % "4.2.6",
        "org.scalatest" % "scalatest_2.10" % "2.0" % "test"
      )
    )
  )

}








