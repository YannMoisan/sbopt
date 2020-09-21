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
