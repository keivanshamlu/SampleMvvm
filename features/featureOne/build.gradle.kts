import androidDeps.groupDependencies.*
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
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.CORE))
    implementation(project(Modules.Utility.BASES))
    implementation(project(Modules.Utility.BASES_ANDROID))

    baseDependenciesAndroid()
    featureModuleBaseDependencies()
    naviagtion()
    databinding()
    daggerAssisted()
}