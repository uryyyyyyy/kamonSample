package akka.uryyyyyyy.kamon.aspectj

import akka.actor.{ActorCell, Cell}
import kamon.Kamon
import org.aspectj.lang.annotation._

@Aspect
class AkkaActorInstrumentation {

  @Pointcut("execution(* akka.actor.ActorCell.sendMessage(*)) && this(cell) && args(*)")
  def sendMessageToActor(cell: Cell): Unit = {}

  @Before("sendMessageToActor(cell)")
  def beforeSendMessageToActor(cell: Cell): Unit = {
    val mb = cell.asInstanceOf[ActorInstrumentationBox].mailBoxSizeValue
    mb.increment()
  }

  @Pointcut("execution(* akka.actor.ActorCell.invoke(*)) && this(cell) && args(*)")
  def invokingActorCell(cell: ActorCell): Unit = {}

  @After("invokingActorCell(cell)")
  def afterInvokingActorCell(cell: ActorCell): Unit = {
    val mb = cell.asInstanceOf[ActorInstrumentationBox].mailBoxSizeValue
    mb.decrement()
  }
}

@Aspect
class MetricsIntoActorCellsMixin {
  @DeclareMixin("akka.actor.ActorCell")
  def mixinActorCellMetricsToActorCell: ActorInstrumentationBox = ActorInstrumentationBox()
}

trait ActorInstrumentationBox {
  def mailBoxSizeValue: MailBoxSizeValue
}

object ActorInstrumentationBox {
  def apply(): ActorInstrumentationBox = new ActorInstrumentationBox(){
    var mb = new MailBoxSizeValue()
    override def mailBoxSizeValue: MailBoxSizeValue = mb
  }
}

class MailBoxSizeValue(){
  var boxSize: Int = 0
  val mailboxSize = Kamon.rangeSampler("akka.actor.mailbox-size")

  def increment(): Unit ={
    boxSize += 1
    mailboxSize.increment()
  }

  def decrement(): Unit ={
    boxSize -= 1
    mailboxSize.decrement()
  }
}