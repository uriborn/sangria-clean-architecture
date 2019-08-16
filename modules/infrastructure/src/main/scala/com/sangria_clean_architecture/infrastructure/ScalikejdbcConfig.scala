package com.sangria_clean_architecture.infrastructure

class ScalikejdbcConfig extends ApplicationConf {

  object rdb { // TODO: read from conf file
    lazy val driver: String   = "com.mysql.cj.jdbc.Driver"
    lazy val url: String      = "jdbc:mysql://docker.for.mac.localhost:3307/star_wars_local?useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useUnicode=true&connectionCollation=utf8mb4_bin"
    lazy val user: String     = "root"
    lazy val password: String = "root"

    lazy val poolName: String                  = "star-wars-db-pool"
    lazy val poolInitialSize: Int              = 20
    lazy val poolMaxSize: Int                  = 10
    lazy val poolConnectionTimeoutMillis: Long = 10000
    lazy val poolValidationQuery: String       = "SELECT 1"
    lazy val maxLifeTime: Long                 = 1800000
  }

}
