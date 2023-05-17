plugins {
    id("aikar-idb-conventions")
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

allprojects {
    apply(plugin = "aikar-idb-conventions")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "java")
    version = "1.1-SNAPSHOT"

    group = "co.aikar.idb"

    tasks.compileJava {
        options.encoding = "UTF-8"
    }
}