package com.github.uryyyyyyy.kamon.future

import kamon.Kamon
import kamon.context.{Context, Key}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main {

  private val logger = LoggerFactory.getLogger(classOf[Main])

  def main(args: Array[String]): Unit = {
    val key = Key.local("key", "")
    val context = Context.create(key, "value")
    val f = Kamon.withContext(context) {
      Future("Hello Kamon!")
        .map(_.length)
        .flatMap(len ⇒ Future(len.toString))
        .map(s => s"${Kamon.currentContext().get(key)} ${s}")
    }
    val str = Await.result(f, Duration.Inf)
    logger.info(str)
    Kamon.scheduler().shutdown()
    System.exit(0) // 何かkamonでスレッドを使っていて止まらないので終了させる
  }

}

class Main