package com.gl.vendor.management

import akka.http.scaladsl.server.Route
import com.gl.vendor.management.resources.VendorResource
import com.gl.vendor.management.services.VendorService

import scala.concurrent.ExecutionContext

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val vendorService = new VendorService

  val routes: Route = vendorRoutes

}

trait Resources extends VendorResource

