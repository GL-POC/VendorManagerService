package com.gl.vendor.management

import akka.actor._
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.gl.vendor.management.services.{CloudFoundryHelper, DatabaseService, VendorService}
import com.typesafe.config.ConfigFactory
import http.VendorResource
import slick.jdbc.MySQLProfile

object Main extends App {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  val cfServicesHelper = new CloudFoundryHelper(sys.env)
  val databaseService = if (!cfServicesHelper.inCfCloud()) {
    val dbConfig = config.getConfig("vmsdb")
    new DatabaseService(
      dbConfig.getString("url"), dbConfig.getString("user"), dbConfig.getString("password"), MySQLProfile)
  } else {
    val dbConfig = cfServicesHelper.getConfigFor("p-mysql", "mydb")
    new DatabaseService(
      dbConfig.getString("jdbcUrl"), dbConfig.getString("username"), dbConfig.getString("password"), MySQLProfile)
  }

  val vendorService = new VendorService(databaseService)
  val vendorRoutes = new VendorResource(vendorService)
  val routes = vendorRoutes.route

  val port = if (sys.env.contains("PORT")) sys.env("PORT").toInt else config.getInt("http.port")
  Http().bindAndHandle(routes, config.getString("http.host"), port)
}
