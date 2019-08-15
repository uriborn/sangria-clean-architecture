import com.typesafe.config.ConfigFactory

enablePlugins(FlywayPlugin)

val conf = ConfigFactory.parseFile(new File("modules/infrastructure/conf/rdb/local.conf"))

flywayLocations := Seq("filesystem:./modules/infrastructure/conf/db/migrations")
flywayUrl := conf.getString("db.default.url")
flywayUser := conf.getString("db.default.user")
flywayPassword := conf.getString("db.default.password")
flywayOutOfOrder := true
