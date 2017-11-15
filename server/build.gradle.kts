@file:Suppress("PropertyName")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val VERTX_VER = "3.5.0"
val MAIN_CLASS = "io.vertx.core.Launcher"
val MAIN_VERTICLE = "org.example.webstack.server.Server"
val WEBSCENE_SERVER_VER = "0.1-SNAPSHOT"


buildscript {
    extra["shadow-ver"] = "2.0.1"
    extra["kotlin-ver"] = "1.1.60"

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:${extra["shadow-ver"]}")
    }
}

val KOTLIN_VER = "${extra["kotlin-ver"]}"

plugins {
    kotlin(module = "jvm", version = "1.1.60")
    application
}

application.mainClassName = MAIN_CLASS

repositories {
    maven { url = uri("libs") }
}

apply {
    plugin("com.github.johnrengelman.shadow")
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$KOTLIN_VER")
    compile("io.vertx:vertx-core:$VERTX_VER")
    compile("io.vertx:vertx-web:$VERTX_VER")
    compile("io.vertx:vertx-lang-kotlin:$VERTX_VER")
    compile("org.webscene:webscene-server:$WEBSCENE_SERVER_VER")
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "1.8"
}
val run by tasks.getting(JavaExec::class) {
    val watcherPath = "src/**/*.kt"
    val watcherAction = "./gradlew classes"

    args(
            "run",
            MAIN_VERTICLE,
            "--redeploy=$watcherPath",
            "--launcher-class=$MAIN_CLASS",
            "--on-redeploy=$watcherAction"
    )
}
val shadowJar by tasks.getting(ShadowJar::class) {
    baseName = "webstack"
    version = "0.1-SNAPSHOT"
    classifier = ""
    append("src/main/resources/public")
    manifest {
        attributes["Main-Verticle"] = MAIN_VERTICLE
    }
}
