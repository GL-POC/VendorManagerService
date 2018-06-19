package http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import com.gl.vendor.management.entities.Vendor
import com.gl.vendor.management.services.VendorService
import spray.json.DefaultJsonProtocol

trait Protocols extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val vendorFormat = jsonFormat2(Vendor)
}

class VendorResource(val vendorService: VendorService) extends Protocols {

  val route = logRequestResult("VendorResource") {
    pathPrefix("vendors") {
      pathEndOrSingleSlash {
        get {
          complete {
            vendorService.getVendors()
          }
        } ~
          post {
            entity(as[Vendor]) { vendorForCreate =>
              complete {
                vendorService.createVendor(vendorForCreate)
              }
            }
          }
      } ~
        pathPrefix(LongNumber) { id =>
          get {
            complete {
              vendorService.getVendor(id)
            }
          } ~
            put {
              entity(as[Vendor]) { vendorForUpdate =>
                complete {
                  vendorService.update(id, vendorForUpdate)
                }
              }
            } ~
            delete {
              onSuccess(vendorService.deleteVendor(id)) { _ =>
                complete(NoContent)
              }
            }
        }
    }
  }

}
