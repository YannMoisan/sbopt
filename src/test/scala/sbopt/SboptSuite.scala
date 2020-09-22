package sbopt

import java.nio.file.Paths

class WrappedString(val self: String)
class WrappedInt(val self: Int)

class SboptSuite extends munit.FunSuite {
  test("add one option with a value of type String") {
    assertEquals(args(opt("name", "value")), List("--name", "value"))
  }
  test("add one option with a value of type WrappedString") {
    implicit val wrappedStringEncoder: Encoder[WrappedString] = _.self
    assertEquals(args(opt("name", new WrappedString("value"))), List("--name", "value"))
  }
  test("add one option with a value of type WrappedInt") {
    implicit val wrappedIntEncoder: Encoder[WrappedInt] = Encoder.from(_.self)
    assertEquals(args(opt("name", new WrappedInt(42))), List("--name", "42"))
  }
  test("add one option with a value of type Int") {
    assertEquals(args(opt("name", 42)), List("--name", "42"))
  }
  test("add one option with a value of type Boolean") {
    assertEquals(args(opt("name", true)), List("--name", "true"))
  }
  test("add one option with a value of type Path") {
    assertEquals(args(opt("name", Paths.get("/root"))), List("--name", "/root"))
  }
  test("add one option with a value of type File") {
    assertEquals(args(opt("name", Paths.get("/root").toFile)), List("--name", "/root"))
  }
  test("add one option with a value of type List") {
    assertEquals(args(opt("name", List("value1", "value2"))), List("--name", "value1,value2"))
  }
  test("add one option with a value of type (String, Int)") {
    assertEquals(args(opt("name", ("key", 42))), List("--name", "key=42"))
  }
  test("add one option when the value is Some") {
    assertEquals(args(opt("name", Some("value"))), List("--name", "value"))
  }
  test("discard option when the value is None") {
    assertEquals(args(opt[String]("name", None)), List())
  }
  test("discard option when value is empty") {
    assertEquals(args(opt("name", "")), List())
  }
  test("add flag if the value is true") {
    assertEquals(args(flag("flag", true)), List("--flag"))
  }
  test("discard flag if the value is false") {
    assertEquals(args(flag("flag", false)), List())
  }
  test("add multiple options") {
    assertEquals(
      args(opts("name", List("value1", "value2"))),
      List("--name", "value1", "--name", "value2")
    )
  }
  test("add multiple options with heterogeneous values") {
    assertEquals(
      args(opts("name", "value1", 1)),
      List("--name", "value1", "--name", "1")
    )
  }
  test("add multiple options with heterogeneous tuple values") {
    assertEquals(
      args(opts("name", ("key1", "value1"), ("key2", 42))),
      List("--name", "key1=value1", "--name", "key2=42")
    )
  }
  test("add multiple options with tuples") {
    assertEquals(
      args(opts("name", List(("k1", 1), ("k2", 2)))),
      List("--name", "k1=1", "--name", "k2=2")
    )
  }
  test("add multiple options with a value of type WrappedString") {
    implicit val wrappedStringEncoder: Encoder[WrappedString] = _.self
    assertEquals(
      args(opts("name", List(new WrappedString("value1"), new WrappedString("value2")))),
      List("--name", "value1", "--name", "value2")
    )
  }
  test("add two options with a value of type String") {
    assertEquals(
      args(opt("name1", "value1"), opt("name2", "value2")),
      List("--name1", "value1", "--name2", "value2")
    )
  }
  test("add mixed arguments") {
    assertEquals(
      args(opt("hour", Some("18")), flag("dry-run", true)),
      List("--hour", "18", "--dry-run")
    )
  }
}
