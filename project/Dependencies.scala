import sbt._

object Dependencies {

  private val circeVersion = "0.11.1"

  val logback             = "ch.qos.logback"               %  "logback-classic"      % "1.2.3"
  val mysql               = "mysql"                        %  "mysql-connector-java" % "8.0.17"
  val awsLambdaCore       = "com.amazonaws"                %  "aws-lambda-java-core" % "1.2.0"
  val enumeratum          = "com.beachape"                 %% "enumeratum"           % "1.5.13"
  val guice               = "com.google.inject"            %  "guice"                % "4.2.2"
  val guiceAssistedInject = "com.google.inject.extensions" %  "guice-assistedinject" % "4.2.2"
  val scalikeJDBC         = "org.scalikejdbc"              %% "scalikejdbc"          % "3.3.5"
  val sangria             = "org.sangria-graphql"          %% "sangria"              % "1.4.2"
  val sangriaCirce        = "org.sangria-graphql"          %% "sangria-circe"        % "1.2.1"
  val sangriaSlowLog      = "org.sangria-graphql"          %% "sangria-slowlog"      % "0.1.8"
  val typeSafeConfig      = "com.typesafe"                 %  "config"               % "1.3.4"
  val ficus               = "com.iheart"                   %% "ficus"                % "1.4.7"
  val hikariCP            = "com.zaxxer"                   %  "HikariCP"             % "3.3.1"
  val circeCore           = "io.circe"                     %% "circe-core"           % circeVersion
  val circeGeneric        = "io.circe"                     %% "circe-generic"        % circeVersion
  val circeParser         = "io.circe"                     %% "circe-parser"         % circeVersion
  val scalatest           = "org.scalatest"                %% "scalatest"            % "3.0.8"

  sealed trait BaseProject {
    lazy val dependencies = compileDependencies ++ testDependencies

    val compileDependencies: Seq[ModuleID]
    val testDependencies: Seq[ModuleID]

    def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % Compile) map (_.exclude("commons-logging", "commons-logging"))
    def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % Test)
  }

  object Infrastructure extends BaseProject {
    val compileDependencies = compile(mysql, scalikeJDBC, guice, typeSafeConfig, ficus, hikariCP, circeCore, circeGeneric, circeParser)
    val testDependencies = test(scalatest)
  }

  object Entities extends BaseProject {
    val compileDependencies = compile(enumeratum)
    val testDependencies = test(scalatest)
  }

  object UseCases extends BaseProject {
    val compileDependencies = compile(guice, guiceAssistedInject)
    val testDependencies = test(scalatest)
  }

  object Adapters extends BaseProject {
    val compileDependencies = compile(guice, guiceAssistedInject, awsLambdaCore, scalikeJDBC, sangria, sangriaCirce, sangriaSlowLog)
    val testDependencies = test(scalatest)
  }

}
