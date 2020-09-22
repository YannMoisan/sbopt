package sbopt

import java.io.File
import java.nio.file.Path

trait Encoder[A] {
  def encode(a: A): String
}

object Encoder {
  implicit val stringEncoder = new Encoder[String] {
    override def encode(a: String): String = a
  }
  implicit val intEncoder = new Encoder[Int] {
    override def encode(a: Int): String = String.valueOf(a)
  }
  implicit val longEncoder = new Encoder[Long] {
    override def encode(a: Long): String = String.valueOf(a)
  }
  implicit val floatEncoder = new Encoder[Float] {
    override def encode(a: Float): String = String.valueOf(a)
  }
  implicit val doubleEncoder = new Encoder[Double] {
    override def encode(a: Double): String = String.valueOf(a)
  }
  implicit val booleanEncoder = new Encoder[Boolean] {
    override def encode(a: Boolean): String = String.valueOf(a)
  }
  implicit val fileEncoder = new Encoder[File] {
    override def encode(a: File): String = a.getAbsolutePath()
  }
  implicit val pathEncoder = new Encoder[Path] {
    override def encode(a: Path): String = a.toAbsolutePath().toString
  }
  implicit def tuple2Encoder[A: Encoder, B: Encoder] =
    new Encoder[Tuple2[A, B]] {
      override def encode(a: (A, B)): String = {
        val encoderA = implicitly[Encoder[A]]
        val encoderB = implicitly[Encoder[B]]
        s"${encoderA.encode(a._1)}=${encoderB.encode(a._2)}"
      }
    }
  implicit def listEncoder[A: Encoder] =
    new Encoder[List[A]] {
      override def encode(a: List[A]): String = {
        val encoder = implicitly[Encoder[A]]
        a.map(encoder.encode).mkString(",")
      }
    }
  implicit def from[A, B: Encoder](f: A => B) =
    new Encoder[A] {
      override def encode(a: A): String = {
        val encoder = implicitly[Encoder[B]]
        encoder.encode(f(a))
      }
    }
}
