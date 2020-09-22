# sbopt

sbopt is a little command line options builder library.

## Motivation

Given the following values 

```
val hour : Option[String] = Some("18")
val dryRun : Boolean = true
```

You want to build arguments to call another process

```
val hourArgs   = hour.map(v => Seq("--hour", v)).getOrElse(Nil)
val dryRunArgs = if (dryRun) Seq("--flag") else Nil
val allArgs    = hourArgs ++ dryRunArgs
```

with this library, you can simply do

```
args(opt("hour", hour), flag("dry-run", dryRun))
```

# Built-in Supported Types

sbopt comes with support for many types, most of them from the standard Java and Scala libraries. When using those types, users don’t have to provide anything else in order to be able to build an argument.

The currently supported basic types are:

- `String`
- `Boolean`
- `Int`, `Long`, `Float`, `Double`
- `java.io.File`
- `java.nio.file.Path`
- `Tuple2`
- `Option`
- `List`

# Supporting New Types

sbopt can be extended to support those types. To do so, an instance for the `Encoder` type class must be provided. 

## by implementing a new `Encoder` yourself

```
class WrappedString(val self: String)
implicit val wrappedStringEncoder: Encoder[WrappedString] = _.self
```

## by using a from combinator

When you can convert your type to a type that has an instance of `Encoder`

```
class WrappedInt(val self: Int)
implicit val wrappedIntEncoder: Encoder[WrappedInt] = Encoder.from(_.self)
```
