package com.github.uryyyyyyy.kamon.system_metrics

import kamon.Kamon
import kamon.system.SystemMetrics

object Main {

  def main(args: Array[String]): Unit = {
    Kamon.loadReportersFromConfig()
    SystemMetrics.startCollecting()
    Thread.sleep(10000)
    SystemMetrics.stopCollecting()
    Kamon.stopAllReporters()
    Kamon.scheduler().shutdown()
    System.exit(0) // 何かkamonでスレッドを使っていて止まらないので終了させる
  }

}
