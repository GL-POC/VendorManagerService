package com.gl.vendor.management.entities.db

import com.gl.vendor.management.entities.Vendor
import slick.jdbc.JdbcProfile

case class DAO(val driver: JdbcProfile) {
  import driver.api._
  class Vendors(tag: Tag) extends Table[Vendor](tag, "VENDORS") {
    def id = column[Long]("vendor_id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("vendor_name")

    override def * = (id.?, name) <> ((Vendor.apply _).tupled, Vendor.unapply)
  }

}