package sbopt

case class Foo(bar: String)

class SboptSuite extends munit.FunSuite {
  test("add one option with a value of type String") {
    assertEquals(args(opt("name", "value")), List("--name", "value"))
  }
  test("add one option with a value of type custom") {
    implicit val showFoo: Encoder[Foo] = _.bar
    assertEquals(args(opt("name", Foo("value"))), List("--name", "value"))
  }
  test("add one option with a value of type Int") {
    assertEquals(args(opt("name", 42)), List("--name", "42"))
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
  test("add multiple options with a value of type custom") {
    implicit val showFoo: Encoder[Foo] = _.bar
    assertEquals(
      args(opts("name", List(Foo("value1"), Foo("value2")))),
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
