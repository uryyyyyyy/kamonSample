package com.github.uryyyyyyy.kamon.simple.reporter

import com.typesafe.config.Config
import kamon.MetricReporter
import kamon.metric.TickSnapshot
import org.slf4j.LoggerFactory

class MyReporter extends MetricReporter {

  private val logger = LoggerFactory.getLogger(classOf[MyReporter])

  override def reportTickSnapshot(snapshot: TickSnapshot): Unit = {
    logger.info("reportTickSnapshot")
    snapshot.metrics.counters.foreach(metric => {
      logger.info(s"name: ${metric.name}, tags: ${metric.tags}, unit: ${metric.unit}, value: ${metric.value}")
    })
    snapshot.metrics.histograms.foreach(metric => {
      logger.info(s"name: ${metric.name}, tags: ${metric.tags}, unit: ${metric.unit}, value-sum: ${metric.distribution.sum}")
    })
    snapshot.metrics.minMaxCounters.foreach(metric => {
      logger.info(s"name: ${metric.name}, tags: ${metric.tags}, unit: ${metric.unit}, value-max: ${metric.distribution.max}")
    })
  }

  override def start(): Unit = {
    logger.info("MyReporter start")
  }

  override def stop(): Unit = {
    logger.info("MyReporter stop")
  }

  override def reconfigure(config: Config): Unit = {
    logger.info("MyReporter reconfigure")
  }
}
