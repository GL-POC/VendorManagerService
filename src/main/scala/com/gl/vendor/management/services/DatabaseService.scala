package com.gl.vendor.management.services

import com.gl.vendor.management.entities.db.DAO
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import slick.jdbc.JdbcProfile

class DatabaseService(jdbcUrl: String, user: String, password: String, val driver: JdbcProfile) {

  private val hikariConfig = new HikariConfig()
  hikariConfig.setJdbcUrl(jdbcUrl)
  hikariConfig.setUsername(user)
  hikariConfig.setPassword(password)

  val dataSource = new HikariDataSource(hikariConfig)

  private val flywayService = new FlywayService(dataSource)
  println("Migrating database..")
  flywayService.migrateDatabaseSchema()

  import driver.api._
  val db = Database.forDataSource(dataSource)

  val daoType = DAO(driver)
  db.createSession()
}