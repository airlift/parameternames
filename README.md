# Parameter Names
[![Maven Central](https://img.shields.io/maven-central/v/io.airlift/parameternames.svg?label=Maven%20Central)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.airlift%22%20AND%20a%3A%22parameternames%22)
[![Build Status](https://travis-ci.org/airlift/parameternames.svg?branch=master)](https://travis-ci.org/airlift/parameternames)

Parameter Names contains utility methods for fetching the parameter names for a
method or constructor.

Java 8 added the ability to access the parameter names of a method or 
constructor, but this only works if the code was compiled with the Java 8 
version of `javac` with the `-parameters` option enabled.  This utility
falls back to reading parameters from the debug symbols in the method
bytecode for classes compiled without the `-parameters` option enabled.

## Usage

Simply, pass `java.lang.reflect.Method` or `java.lang.reflect.Constructor` to 
`ParameterNames.getParameterNames`. For example:

```java
    Method method = ParameterNames.class.getMethod("getParameterNames", Executable.class);
    List<String> parameterNames = ParameterNames.getParameterNames(method);
```

Parameter names are loaded in the following order:

1. Java 8 reflection names created by `javac` with the `-parameters` option
2. Bytecode debug symbols created by `javac` with the `-g` option
3. Default name of `argN` where `N` is the index of the parameter starting from zero

## Maven Dependency

```xml 
<dependency>
    <groupId>io.airlift</groupId>
    <artifactId>parameternames</artifactId>
    <version>1.0</version>
</dependency>
```

## License

Parameter Names is released under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
