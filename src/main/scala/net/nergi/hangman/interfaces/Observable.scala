package net.nergi.hangman.interfaces

import scala.collection.mutable

trait Observable[E] {
  private lazy val Observers: mutable.ListBuffer[Observer[E]] = new mutable.ListBuffer()

  def registerObserver(observer: Observer[E]): Unit = this.synchronized {
    Observers.addOne(observer)
  }

  def notifyAll(event: E): Unit = this.synchronized {
    Observers.foreach(_.notify(event))
  }
}
