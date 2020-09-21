package sbopt

sealed trait Argument extends Product with Serializable {
  def toSeq: Seq[String]
}

case class Opt(name: String, value: String) extends Argument {
  override def toSeq: Seq[String] =
    if (value.isEmpty) Seq.empty else Seq(s"--$name", value)
}

case class Opts(name: String, values: Seq[String]) extends Argument {
  override def toSeq: Seq[String] =
    values.flatMap(value => Seq(s"--$name", value))
}

case class Flag(name: String, value: Boolean) extends Argument {
  override def toSeq: Seq[String] = if (value) Seq(s"--$name") else Seq.empty
}
