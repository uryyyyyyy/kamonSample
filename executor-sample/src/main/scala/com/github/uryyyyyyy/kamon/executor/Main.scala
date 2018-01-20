package com.github.uryyyyyyy.kamon.executor

import java.util.concurrent.Executors

import kamon.Kamon
import kamon.executors.{Executors => KamonExecutor}
import org.slf4j.LoggerFactory

object Main {

  def main(args: Array[String]): Unit = {
    Kamon.loadReportersFromConfig()
    val fixedThreadPool = Executors.newFixedThreadPool(2)
    val singleThreadExecutor = Executors.newSingleThreadExecutor
    val cachedThreadPool = Executors.newCachedThreadPool
    val workStealingPool = Executors.newWorkStealingPool()

    KamonExecutor.register("fixed-thread-pool", fixedThreadPool)
    KamonExecutor.register("single-thread-executor", singleThreadExecutor)
    KamonExecutor.register("cached-thread-pool", cachedThreadPool)
    KamonExecutor.register("work-stealing-pool", KamonExecutor.instrument(workStealingPool))

    for (_ <- 0 to 20) {
      fixedThreadPool.submit(HeavyWeightTask("fixed-thread-pool"))
      singleThreadExecutor.submit(HeavyWeightTask("single-thread-executor"))
      cachedThreadPool.submit(HeavyWeightTask("cached-thread-pool"))
      workStealingPool.submit(HeavyWeightTask("scheduled-thread-pool"))
    }
    Thread.sleep(1000000)

    fixedThreadPool.shutdown()
    Kamon.scheduler().shutdown()
    Kamon.stopAllReporters()
    System.exit(0) // 何かkamonでスレッドを使っていて止まらないので終了させる
  }

}


class HeavyWeightTask(val str: String) extends Runnable {
  private val logger = LoggerFactory.getLogger(classOf[HeavyWeightTask])
  def run() = {
    Thread.sleep(500)
    logger.info(s"HeavyWeightTask done: ${str}")
  }
}

object HeavyWeightTask {
  def apply(str: String): HeavyWeightTask = new HeavyWeightTask(str)
}