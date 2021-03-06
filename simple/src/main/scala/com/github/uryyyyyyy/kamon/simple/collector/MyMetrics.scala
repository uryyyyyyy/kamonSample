package com.github.uryyyyyyy.kamon.simple.collector

import kamon.Kamon
import org.slf4j.LoggerFactory

class MyMetrics {

  private val logger = LoggerFactory.getLogger(classOf[MyMetrics])

  val hist1    = Kamon.histogram("my-metrics.hist1")
  val counter1    = Kamon.counter("my-metrics.counter1")
  val sampler1    = Kamon.rangeSampler("my-metrics.sampler1")

  def update() = {
    logger.info("MyMetrics record")

    hist1.record(10)
    counter1.increment(1)
    counter1.increment(1)
    sampler1.increment(2)
  }
}
