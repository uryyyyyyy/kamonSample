package com.github.uryyyyyyy.kamon.aspectj.akka

import akka.actor.{Actor, ActorSystem, Props}
import com.github.uryyyyyyy.kamon.aspectj.Executor
import kamon.Kamon

object Main {

  def main(args: Array[String]): Unit = {
    Kamon.loadReportersFromConfig()

    val system = ActorSystem("mySystem")
    val actor1 = system.actorOf(Props[MyActor])

    (1 to 10).foreach(_ => actor1 ! "hello")
    Thread.sleep(5000)
    (1 to 10).foreach(_ => actor1 ! "hello")
    Thread.sleep(20000)

    Kamon.stopAllReporters()
    Kamon.scheduler().shutdown()
    system.terminate()
    System.exit(0) // 何かkamonでスレッドを使っていて止まらないので終了させる
  }

}

class MyActor extends Actor {

  def receive = {
    case s: String => {
      println("received: %s" format s)
      Thread.sleep(1000)
    }
    case _ => {
    }
  }
}