package sbopt

trait Encoder[A] {
  def encode(a: A): String
}

object Encoder {
  implicit val showString = new Encoder[String] {
    override def encode(a: String): String = a
  }
  implicit val showInt = new Encoder[Int] {
    override def encode(a: Int): String = String.valueOf(a)
  }
  implicit val showLong = new Encoder[Long] {
    override def encode(a: Long): String = String.valueOf(a)
  }
  implicit val showFloat = new Encoder[Float] {
    override def encode(a: Float): String = String.valueOf(a)
  }
  implicit val showDouble = new Encoder[Double] {
    override def encode(a: Double): String = String.valueOf(a)
  }
}
