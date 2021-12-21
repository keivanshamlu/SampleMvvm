package groupConfigs

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import androidDeps.AppConfig
import androidDeps.PackagingOptions

const val test = "test"
const val androidTest = "androidTest"
const val sharedTestDir = "src/sharedTest/java"

val Project.android: BaseExtension
    get() = extensions.findByName("android") as? BaseExtension ?: error("$name is not an android module")

fun Project.androidApp(appId: String) {
    androidLib {
        applicationId = appId
    }
}

fun Project.androidLib(
    fields: List<Pair<String, String>>? = null,
    default: (DefaultConfig.() -> Unit)? = null) {
    android.run {
        compileSdkVersion(AppConfig.compileSdk)
        defaultConfig {

            AppConfig.run {
                versionCode = vCode
                versionName = vName
                default?.invoke(this@defaultConfig)
                minSdk = minimumSdkVersion
                targetSdk = targettSdkVersion
                fields?.map { buildConfigField("String", it.first, it.second) }
                testInstrumentationRunner = androidTestInstrumentation
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        dataBinding {
            isEnabled = true
        }
        sourceSets {
            getByName(test).java.srcDirs(sharedTestDir)
            getByName(androidTest).java.srcDirs(sharedTestDir)
        }
        excludePackages()
    }
}

fun Project.excludePackages(){
    android.run {
        PackagingOptions.run {
            packagingOptions.excludes.add(DEPENDENCIES)
            packagingOptions.excludes.add(LICENSE)
            packagingOptions.excludes.add(LICENSE_TEXT)
            packagingOptions.excludes.add(LICENSE_TEXT_2)
            packagingOptions.excludes.add(NOTICE)
            packagingOptions.excludes.add(NOTICE_TEXT)
            packagingOptions.excludes.add(NOTICE_TEXT_2)
            packagingOptions.excludes.add(ASL2)
            packagingOptions.excludes.add(AL2)
            packagingOptions.excludes.add(KOTLIN)
            packagingOptions.excludes.add(LGPL2)
        }
    }
}
