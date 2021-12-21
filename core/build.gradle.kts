import androidDeps.groupDependencies.*
import groupConfigs.androidLib
import groupDependencyModuleLevel.baseDependenciesAndroid
import groupDependencyModuleLevel.featureModuleBaseDependencies
import kotlinDeps.groupDependencies.*
import modules.Modules

plugins {
    GradlePluginId.apply {
        id(ANDROID_LIBRARY)
        kotlin(ANDROID)
        kotlin(KAPT)
        id(NAVIGATION_SAFEARGS_KOTLIN)
        id(ANDROID_EXTENSIONS)
    }
}
androidLib(listOf(Pair("BASE_URL", "\"https://jsonplaceholder.typicode.com\"")))
dependencies {
    implementation(project(Modules.Data.DATA_LOCAL))
    implementation(project(Modules.Utility.BASES))

    baseDependenciesAndroid()
    featureModuleBaseDependencies()
    room()
    networking()
    glide()
}