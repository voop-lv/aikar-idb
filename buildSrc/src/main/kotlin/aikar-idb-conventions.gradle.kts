plugins {
    `java-library`
    `maven-publish`
    id("idea")
}

repositories {
    maven { url = uri("https://repo.voop.lv/repository/vooplvPublic/") }
    maven { url = uri("https://repo.aikar.co/content/groups/aikar/") }
    maven { url = uri("https://jitpack.io/") }
    maven { url = uri("https://repo.ranull.com/maven/external/") }
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}