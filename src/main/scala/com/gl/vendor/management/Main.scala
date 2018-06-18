package com.gl.vendor.management

import akka.actor._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object Main extends App with VMSRestInterface {
  val config = ConfigFactory.load()
  val vms_host = config.getString("http.host")
  val vms_port = config.getInt("http.port")

  implicit val system = ActorSystem("vendor-management-service")
  implicit val materializer = ActorMaterializer()


  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val api = routes

  Http().bindAndHandle(handler = api, interface = vms_host, port = vms_port) map { binding =>
    println(s"VMS REST interface bound to ${binding.localAddress}") } recover { case ex =>
    println(s"VMS REST interface could not bind to $vms_host:$vms_port", ex.getMessage)
  }
}
