import androidDeps.groupDependencies.room
import groupConfigs.androidLib
import groupDependencyModuleLevel.baseDependenciesAndroid
import groupDependencyModuleLevel.featureModuleBaseDependencies
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
androidLib()
dependencies {
    implementation(project(Modules.Data.DATA))
    implementation(project(Modules.Utility.BASES))

    baseDependenciesAndroid()
    featureModuleBaseDependencies()
    room()
}