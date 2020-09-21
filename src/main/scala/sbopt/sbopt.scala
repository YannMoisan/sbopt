package object sbopt {
  def args(args: Argument*): Seq[String] =
    args.flatMap(opt => opt.toSeq)

  def opt[A: Encoder](name: String, value: A): Opt =
    Opt(name, implicitly[Encoder[A]].encode(value))

  def opt[A: Encoder](name: String, value: Option[A]): Opt =
    Opt(name, value.map(implicitly[Encoder[A]].encode).getOrElse(""))

  def opts[A: Encoder](name: String, values: Seq[A]): Opts =
    Opts(name, values.map(implicitly[Encoder[A]].encode))

  def flag(name: String, value: Boolean): Flag =
    Flag(name, value)
}
