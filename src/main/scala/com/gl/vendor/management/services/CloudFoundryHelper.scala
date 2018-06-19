package com.gl.vendor.management.services

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._

class CloudFoundryHelper(env: Map[String, String]) {

  def getConfigFor(serviceType: String, name: String): Config = {
    val vcapServices = env("VCAP_SERVICES")
    val rootConfig = ConfigFactory.parseString(vcapServices)
    val configs = rootConfig.getConfigList(serviceType).asScala
      .filter(_.getString("name") == name)
      .map(instance => instance.getConfig("credentials"))

    if (configs.length > 0) configs.head
    else ConfigFactory.empty()
  }


  def inCfCloud(): Boolean = {
    env.contains("VCAP_APPLICATION")
  }
}
