package com.github.uryyyyyyy.kamon.aspectj

object Main {

  def main(args: Array[String]): Unit = {
    val executor = new Executor()
    executor.action(Array("hoge", "fuga"))
  }

}

class Executor {
  def action(args: Array[String]): Unit = {
    args.foreach(println)
  }
}