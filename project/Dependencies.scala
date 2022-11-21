import sbt._

object Dependencies {

  lazy val Specs2 = "org.specs2" %% "specs2-core" % "4.14.1"

  val LogBackClassic =
    // "ch.qos.logback" % "logback-classic" % "1.2.11" exclude("org.slf4j", "slf4j-api")
    "ch.qos.logback" % "logback-classic" % "1.3.0-alpha14"

  val SlickV = "3.4.0-M1"
  lazy val Slick = Seq(
    "com.typesafe.slick" %% "slick" % SlickV,
    "com.typesafe.slick" %% "slick-hikaricp" % SlickV,
    "org.postgresql" % "postgresql" % "42.2.10"
  )

  private val http4sVersion = "0.23.10"
  def HttpServerLibs = Seq(
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion excludeAll (
      ExclusionRule(organization = "io.circe"),
      ExclusionRule(organization = "org.slf4j"),
      ExclusionRule(organization = "org.log4s")
    ),
    "org.http4s" %% "http4s-prometheus-metrics" % http4sVersion,
    "org.http4s" %% "http4s-scala-xml" % http4sVersion excludeAll (ExclusionRule(
      organization = "org.scala-lang.modules"
    ))
  )

  val circeVersion = "0.14.1"
  def CirceLibs =
    Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-generic-extras" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-optics" % "0.12.0"
    )

}
