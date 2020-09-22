import sbopt.Encoder

// stupid varargs trick by Gavin Bisesi
trait WithEnc {
  type A
  def value: A
  def instance: Encoder[A]
}

trait WithEncImplicits {
  implicit def build[A0](a: A0)(implicit enc: Encoder[A0]): WithEnc =
    new WithEnc { type A = A0; val value = a; val instance = enc }
}

package object sbopt extends WithEncImplicits {
  def args(args: Argument*): Seq[String] =
    args.flatMap(opt => opt.toSeq)

  def opt[A: Encoder](name: String, value: A): Opt =
    Opt(name, implicitly[Encoder[A]].encode(value))

  def opt[A: Encoder](name: String, value: Option[A]): Opt =
    Opt(name, value.map(implicitly[Encoder[A]].encode).getOrElse(""))

  def opts[A: Encoder](name: String, values: Seq[A]): Opts =
    Opts(name, values.map(implicitly[Encoder[A]].encode))

  def opts(name: String, values: WithEnc*): Opts =
    Opts(name, values.map(v => v.instance.encode(v.value)))

  def flag(name: String, value: Boolean): Flag =
    Flag(name, value)
}
