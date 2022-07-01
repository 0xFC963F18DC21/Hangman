package net.nergi.hangman.interfaces

trait Observer[E] {
  /** Notify the current observer of an event. */
  def notify(event: E): Unit =
    handleEvent(event)

  /** Dictates how this observer handles an event. */
  protected def handleEvent(event: E): Unit
}
