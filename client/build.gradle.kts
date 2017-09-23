import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

val WEBSCENE_CLIENT_VER = "0.1-SNAPSHOT"


buildscript {
    extra["kotlin-ver"] = "1.1.4-3"

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin(module = "gradle-plugin", version = "${extra["kotlin-ver"]}"))
    }
}

val KOTLIN_VER = "${extra["kotlin-ver"]}"

apply {
    plugin("kotlin2js")
}

repositories {
    maven { url = uri("libs") }
}

dependencies {
    "compile"(kotlin(module = "stdlib-js", version = KOTLIN_VER))
    "compile"("org.webscene:webscene-client:$WEBSCENE_CLIENT_VER")
}

val compileKotlin2Js by tasks.getting(Kotlin2JsCompile::class) {
    val fileName = "webstack-client.js"

    kotlinOptions.outputFile = "${projectDir.absolutePath}/web/js/$fileName"
    kotlinOptions.sourceMap = true
    doFirst { File("${projectDir.absolutePath}/web/js").deleteRecursively() }
}
val build by tasks
val assembleWeb by tasks.creating(Copy::class) {
    dependsOn("classes")
    configurations["compile"].forEach { file ->
        from(zipTree(file.absolutePath)) {
            includeEmptyDirs = false
            include { fileTreeElement ->
                val path = fileTreeElement.path

                path.endsWith(".js") && path.startsWith("META-INF/resources/") || !path.startsWith("META_INF/")
            }
        }
    }
    from(compileKotlin2Js.destinationDir)
    into("${projectDir.absolutePath}/web/js")
}

task<Sync>("deployClientToServer") {
    dependsOn(compileKotlin2Js, assembleWeb)
    from("${projectDir.absolutePath}/web")
    into("${projectDir.parent}/server/src/main/resources/public")
}