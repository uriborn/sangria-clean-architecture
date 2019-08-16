package com.sangria_clean_architecture.infrastructure

import com.google.inject.{Inject, Singleton}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import scalikejdbc.{ConnectionPool, DataSourceConnectionPool}

@Singleton
class DBComponent @Inject()(dbConfig: ScalikejdbcConfig) {

  def initialize(): Unit = {
    val dataSource: HikariDataSource = {
      val hc = new HikariConfig()
      hc.setDriverClassName(dbConfig.rdb.driver)
      hc.setJdbcUrl(dbConfig.rdb.url)
      hc.setUsername(dbConfig.rdb.user)
      hc.setPassword(dbConfig.rdb.password)
      hc.setPoolName(dbConfig.rdb.poolName)
      hc.setMinimumIdle(dbConfig.rdb.poolInitialSize)
      hc.setMaximumPoolSize(dbConfig.rdb.poolMaxSize)
      hc.setConnectionTimeout(dbConfig.rdb.poolConnectionTimeoutMillis)
      hc.setConnectionTestQuery(dbConfig.rdb.poolValidationQuery)
      hc.setMaxLifetime(dbConfig.rdb.maxLifeTime)
      new HikariDataSource(hc)
    }
    ConnectionPool.singleton(new DataSourceConnectionPool(dataSource))
  }

  initialize()
}
