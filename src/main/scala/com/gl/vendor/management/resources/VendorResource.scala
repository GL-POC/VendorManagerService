package com.gl.vendor.management.resources

import akka.http.scaladsl.server.Route

import com.gl.vendor.management.entities.{Vendor, VendorUpdate}
import com.gl.vendor.management.routing.ResourceRouting
import com.gl.vendor.management.services.VendorService

trait VendorResource extends ResourceRouting {

  val vendorService: VendorService

  def vendorRoutes: Route = pathPrefix("vendors") {
    pathEnd {
      post {
        entity(as[Vendor]) { vendor =>
          completeWithLocationHeader(
            resourceId = vendorService.createVendor(vendor),
            ifDefinedStatus = 201, ifEmptyStatus = 409)
          }
        }
    } ~
    path(Segment) { id =>
      get {
        complete(vendorService.getVendorList(id))
      } ~
      put {
        entity(as[VendorUpdate]) { update =>
          complete(vendorService.updateVendor(id, update))
        }
      } ~
      delete {
        complete(vendorService.deleteVendor(id))
      }
    }

  }
}

