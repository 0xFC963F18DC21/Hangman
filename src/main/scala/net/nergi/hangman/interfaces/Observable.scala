package net.nergi.hangman.interfaces

import scala.collection.mutable

trait Observable[E] {
  private lazy val Observers: mutable.ListBuffer[Observer[E]] = new mutable.ListBuffer()

  /** Register an observer to notify if an event occurs. */
  def registerObserver(observer: Observer[E]): Unit = this.synchronized {
    Observers.addOne(observer)
  }

  /** Notify all observers of an event that occurred. */
  def notifyAll(event: E): Unit = this.synchronized {
    Observers.foreach(_.notify(event))
  }

  /** Wrap a computation in a notification wrapper that notifies all observers after all is done. */
  final protected def notifyWrap[R](event: E)(lmb: => R): R = {
    try {
      lmb
    } finally {
      notifyAll(event)
    }
  }
}
