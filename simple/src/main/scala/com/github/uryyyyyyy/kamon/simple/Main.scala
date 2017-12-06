package com.github.uryyyyyyy.kamon.simple

import com.github.uryyyyyyy.kamon.simple.collector.MyCollector
import kamon.Kamon

object Main {

  def main(args: Array[String]): Unit = {
    Kamon.loadReportersFromConfig()
    MyCollector.startCollecting()
    Thread.sleep(10000)
    MyCollector.stopCollecting()
    Kamon.stopAllReporters()
    Kamon.scheduler().shutdown()
    System.exit(0) // 何かkamonでスレッドを使っていて止まらないので終了させる
  }

}
