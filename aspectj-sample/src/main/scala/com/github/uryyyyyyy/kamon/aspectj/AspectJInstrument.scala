package com.github.uryyyyyyy.kamon.aspectj

import org.aspectj.lang.annotation.{After, Aspect, Pointcut}

@Aspect
class AspectJInstrument {

  @Pointcut("execution(* com.github.uryyyyyyy.kamon.aspectj.Executor.action(*)) && this(executor) && args(args)")
  def actionPointCut(executor: Executor, args: Array[String]): Unit = {}

  @After("actionPointCut(executor, args)")
  def afterCreation(executor: Executor, args: Array[String]): Unit = {
    args.foreach(str => println(s"after: ${str}"))
    println("after")
  }

}
