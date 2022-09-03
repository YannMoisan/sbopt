![Build](https://github.com/YannMoisan/sbopt/actions/workflows/build.yml/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.yannmoisan/sbopt_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.yannmoisan/sbopt_2.12)
[![License](https://img.shields.io/github/license/yannmoisan/sbopt)](http://www.apache.org/licenses/LICENSE-2.0.html)

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

# Usage

Here are some usages for building arguments :

- with a `String` option

```
@ args(opt("name", "value"))
ArrayBuffer("--name", "value")
```

- with an `Int` option

```
@ args(opt("name", 42))
ArrayBuffer("--name", "42")
```

- with an `Option` option

```
@ args(opt("name", Some("value")))
ArrayBuffer("--name", "value")
```

- with a `List` option (values are separated by a comma)

```
@ args(opt("name", List("A", "B")))
ArrayBuffer("--name", "A,B")
```

- with a `Tuple` option (values are separated by an equal)

```
@ args(opt("name", ("key", "value")))
ArrayBuffer("--name", "key=value")
```

- with 2 options

```
@ args(opt("name", "value"), opt("other", "XXX"))
ArrayBuffer("--name", "value", "--other", "XXX")
```

- with a repeated option

```
@ args(opt("name", "A"), opt("name", "B"))
ArrayBuffer("--name", "A", "--name", "B")
```

- with a repeated option, by using a dedicated helper

```
@ args(opts("name", List("A", "B")))
ArrayBuffer("--name", "A", "--name", "B")
```

- with an heterogeneous repeated option

```
@ args(opts("name", "value", 42))
ArrayBuffer("--name", "value", "--name", "42")
```

- putting it all together (a.k.a. calling spark-submit in a programmatic way)

```
@ args(opts("conf", ("verbose", true), ("spark.yarn.maxAppAttempts", 1), ("spark.yarn.jars", List(java.nio.file.Paths.get("/path1"), java.nio.file.Paths.get("/path2")))))
ArrayBuffer("--conf", "verbose=true", "--conf", "spark.yarn.maxAppAttempts=1", "--conf", "spark.yarn.jars=/path1,/path2")
```

- with a discardable (empty String or None) value

```
@ args(opt("name", ""))
ArrayBuffer()

@ args(opt[String]("name", None))
ArrayBuffer()
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
