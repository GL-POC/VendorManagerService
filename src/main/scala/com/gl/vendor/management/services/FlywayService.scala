package com.gl.vendor.management.services

import javax.sql.DataSource
import org.flywaydb.core.Flyway

class FlywayService(dataSource: DataSource) {

  private[this] val flyway = new Flyway()
  flyway.setDataSource(dataSource)
  flyway.setBaselineOnMigrate(true)

  def migrateDatabaseSchema() : Unit = flyway.migrate()

  def dropDatabase() : Unit = flyway.clean()
}
