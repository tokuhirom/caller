caller
======

Get the caller information really fast!

```
Score:

normal:  1 wallclock secs ( 1.03 usr +  0.01 sys =  1.04 CPU) @ 20189.94/s (n=21049)
Reflection:  1 wallclock secs ( 1.10 usr +  0.01 sys =  1.11 CPU) @ 169244.37/s (n=187681)

Comparison chart:

                  Rate  normal  Reflection
      normal   20190/s      --        -88%
  Reflection  169244/s    738%          --
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.806 sec
```


## Motivation

`(new Throwable()).getStackTrace()` aggregates all stack information. It makes slow if your code
makes deep calling stack.

But, there is a private API for getting specific stack. It's faster than normal approach.
This library wraps the private API calling. And if it's unavailable, this library fallback to
normal approach.

## SYNOPSIS

		System.out.println(Caller.caller(0).getClassName());

## How it works?

This library calls private API in JDK. If it's unavailable, Caller class fallbacks to normal `new Throwable().getStackTrace()`.
