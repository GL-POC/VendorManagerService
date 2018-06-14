package com.gl.vendor.management.services

import com.gl.vendor.management.entities.{Vendor, VendorUpdate}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class VendorService(implicit val executionContext: ExecutionContext) {

  var vendors = Vector.empty[Vendor]

  def createVendor(vendor: Vendor): Future[Option[String]] = Future {
    vendors.find(_.vendor_id == vendor.vendor_id) match {
      case Some(q) => None
      case None =>
        vendors = vendors :+ vendor
        Some(vendor.vendor_id)
    }
  }

  def getVendorList(id: String): Future[Vector[Vendor]] = Future {
    id match {
      case "all" => vendors
      case s: String if !s.isInt => vendors.find(_.vendor_name == id) match {
        case Some(b) => Vector(b)
        case None => Vector.empty[Vendor]
      }

      case _ => vendors.find(_.vendor_id == id) match {
        case Some(a) => Vector(a)
        case None => Vector.empty[Vendor]
      }
    }
  }

  def getVendor(id: String): Future[Option[Vendor]] = Future {
    vendors.find(_.vendor_id == id)
  }

  def updateVendor(vendor_id: String, update: VendorUpdate): Future[Option[Vendor]] = {

    def updateEntity(vendor: Vendor): Vendor = {
      val name = update.vendor_name.getOrElse(vendor.vendor_name)
      Vendor(vendor_id, name)
    }

    getVendor(vendor_id).flatMap { maybeVendor =>
      maybeVendor match {
        case None => Future { None }
        case Some(vendor) =>
          val updatedVendor = updateEntity(vendor)
          deleteVendor(vendor_id).flatMap { _ =>
            createVendor(updatedVendor).map(_ => Some(updatedVendor))
          }
      }
    }
  }

  def deleteVendor(id: String): Future[Unit] = Future {
    vendors = vendors.filterNot(_.vendor_id == id)
  }

  implicit class IsInt(i: String){
    def isInt: Boolean = Try(i.toInt).isSuccess
  }
}

