package com.gl.vendor.management.services

import com.gl.vendor.management.entities.Vendor

import scala.concurrent.{ExecutionContext, Future}

class VendorService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) {
  import databaseService._
  import databaseService.driver.api._

  val vendors = TableQuery[daoType.Vendors]

  def getVendors(): Future[Seq[Vendor]] = db.run(vendors.result)

  def getVendor(id: Long): Future[Option[Vendor]] = db.run(vendors.filter(_.id === id).result.headOption)

  def getVendorsByName(name: String): Future[Seq[Vendor]] = db.run(vendors.filter(_.name === name).result)


  def createVendor(vendor: Vendor): Future[Vendor] = db.run(vendors returning vendors.map(_.id) into ((vendor, id) => vendor.copy(vendor_id=Some(id))) += vendor)

  def update(id: Long, toUpdate: Vendor): Future[Option[Vendor]] = getVendor(id).flatMap {
    case Some(vendor) => {
      val updatedVendor = Vendor(vendor.vendor_id, toUpdate.vendor_name)
      db.run(vendors.filter(_.id === id).update(updatedVendor)).map(_ => Some(updatedVendor))
    }
    case None => Future.successful(None)
  }

  def deleteVendor(id: Long):Future[Int] = db.run(vendors.filter(_.id === id).delete)
}
