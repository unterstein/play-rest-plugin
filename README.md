# Play REST Plugin

Simple plugin enabling convenience functionality to build RESTful APIs for the playframework 2.4.

I got the idea because we do implement often RESTful APIs with the playframework and i felt something is missing. 

So here we go, i created this plugin :)

## Current Version

**0.1.0**

* kickstart the project

## Installation (using sbt)

You will need to add the following resolver in your `build.sbt` file:

```scala
resolvers += "unterstein.github.io" at "http://unterstein.github.io/repo"

```

Add a dependency on the following artifact:

```scala
libraryDependencies += "com.github.unterstein" %% "play-rest-plugin" % "0.1.0"
```

## Usage

```scala
  def examplePost(id: Long) = RESTAction(Some(Class[ExampleUser]), objectRequired = true) {
    implicit request =>
      Ok(s"ok: $id ${request.givenObject}")
  }

  case class ExampleUser(id: Long, name: String)
```

## TODO

* Enable route scanning and validation
* Enable basic auth and other authentication methods


## Old Versions

None