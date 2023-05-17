# Fork notice
This project is forked from already existing project [Aikar IDB](https://github.com/aikar/db). This fork include some of the patches and including H2, Postgres SQL support and other stuff.

# IDB - Intuitive Database Wrapper

This is my JDBC Database Wrapper, with the intent on providing a clean intuitive API, for access to a JDBC database,

IDB handles connections, query, execution, result fetching and data return for all the common access operations without the boilerplate of making all of the JDBC API's yourself.

Most Database operations can be completed in a single API call.

Built currently on top of Hikari Connection Pool.

## Getting Started

**Version**: `1.1-SNAPSHOT` **(UNOFFICIAL/ FORKED PROJECT)**

Project Setup: [Maven](https://github.com/aikar/db/wiki/Maven-Setup), [Gradle](https://github.com/aikar/db/wiki/Gradle-Setup)

Getting Started: [Using IDB](https://github.com/aikar/db/wiki/Using-IDB)

API Documentation: [IDB API](https://github.com/aikar/db/wiki/IDB-API)

Real World Examples: [Examples](https://github.com/aikar/db/wiki/Real-World-Examples)

## Say Thanks
If this library has helped you, please consider donating as a way of saying thanks

[![PayPal Donate](https://aikar.co/donate.png "Donate with PayPal")](https://paypal.me/empireminecraft)

## Why does it require Java 8+?
Get off your dinosaur and get on this rocket ship!

Dinosaurs have been dead for a long time, so get off it before you start to smell.

[Download Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

# Java Docs
TODO

## Contributing
See Issues section. 

Join [#aikar on Spigot IRC - irc.spi.gt](https://aikarchat.emc.gs) to discuss. 

Or [Code With Aikar](https://aikardiscord.emc.gs) Discord.

## Other projects by Aikar / Empire Minecraft
 - [ACF](https://acf.emc.gs) - Powerful Java Command Framework
 - [TaskChain](https://taskchain.emc.gs) - Powerful context control to dispatch tasks Async, then access the result sync for API usage. Concurrency controls too.
 - [Minecraft Timings](https://github.com/aikar/minecraft-timings/) - Add Timings to your plugin in a safe way that works on all Bukkit platforms (CraftBukkit - no timings, Spigot - Timings v1, Paper and Paper forks - Timings v2)

## License
As with all my other public projects

IDB (c) Daniel Ennis (Aikar) 2014-2018.

IDB is licensed [MIT](https://tldrlegal.com/license/mit-license). See [LICENSE](LICENSE)

## Repo
This repo is available on a public maven repo server
### Gradle
```kotlin
repositories {
    maven { 
        url = uri("https://repo.voop.lv/repository/vooplvPublic/") 
    }
}
```
```kotlin
dependencies {
    api("co.aikar:idb-core:1.1-SNAPSHOT")
    //or
    implementation("co.aikar:idb-core:1.1-SNAPSHOT")
}
```
### Maven
```xml
<repositories>
    <repository>
        <id>vooplvPublic</id>
        <url>"https://repo.voop.lv/repository/vooplvPublic/</url>
    </repository>
</repositories>
```
```xml
<dependencies>
    <dependency>
        <groupId>co.aikar</groupId>
        <artifactId>idb-core</artifactId>
        <version>1.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

