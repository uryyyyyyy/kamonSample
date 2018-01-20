package com.github.uryyyyyyy.kamon.akka

import akka.actor.{Actor, ActorSystem, Props}
import kamon.Kamon
import org.slf4j.LoggerFactory

object Main {

  def main(args: Array[String]): Unit = {
    Kamon.loadReportersFromConfig()
    val system = ActorSystem("ask-pattern-timeout-warning")
    val dumbActor = system.actorOf(Props[DumbActor], "dumb-actor")
    implicit val ec = system.dispatcher

    for (_ <- 0 to 100) {
      Thread.sleep(50)
      dumbActor ! "hello"
    }

    system.terminate()
    Kamon.scheduler().shutdown()
    Kamon.stopAllReporters()
    System.exit(0) // 何かkamonでスレッドを使っていて止まらないので終了させる
  }
}

class DumbActor extends Actor {
  private val logger = LoggerFactory.getLogger(classOf[DumbActor])

  def receive = {
    case "hello" => logger.info("hello world")
    case other =>
  }
}