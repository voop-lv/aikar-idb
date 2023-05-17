plugins {
    id("aikar-idb-conventions")
}

description = "core"

dependencies {
    compileOnly("com.zaxxer:HikariCP:2.4.1")
    compileOnly("org.jetbrains:annotations:15.0")
}
/*
tasks.`publishVooplv-publicPublicationToVoopLVPublicRepository` {
    dependsOn(":core:jar")
}

tasks.`publishVooplv-publicPublicationToMavenLocal` {
    dependsOn(":core:jar")
}

 */

tasks.shadowJar {
    archiveBaseName.set(project.description)
}

var projectVer = version.toString();

tasks.shadowJar {
    archiveBaseName.set("AikarIDB")
    archiveVersion.set(projectVer)
    archiveClassifier.set("")
}

val jar: Jar by tasks
jar.enabled = true

publishing {
    publications.create<MavenPublication>("vooplv-public") {
        artifact(tasks["shadowJar"])
    }
    repositories {
        maven {
            name = "voopLVPublic"
            url = uri("https://repo.voop.lv/repository/vooplv-public/")
            credentials(PasswordCredentials::class)
        }
    }
}