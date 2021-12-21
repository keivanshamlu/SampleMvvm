import androidDeps.AppConfig
import androidDeps.groupDependencies.*
import groupDependencyModuleLevel.featureModuleBaseDependencies
import kotlinDeps.groupDependencies.networking
import modules.Modules
import groupConfigs.androidApp
import groupDependencyModuleLevel.baseDependenciesAndroid

plugins {
    GradlePluginId.apply {
        id(ANDROID_APPLICATION)
        kotlin(ANDROID)
        kotlin(KAPT)
    }
}
androidApp(AppConfig.applicationId)
dependencies {

    Modules.run {
        implementation(project(DOMAIN))
        implementation(project(CORE))
        implementation(project(Modules.Utility.BASES))
    }
    Modules.Data.run {
        implementation(project(DATA))
        implementation(project(DATA_REMOTE))
        implementation(project(DATA_LOCAL))
    }
    Modules.Feature.run {
        implementation(project(FEATURE_ONE))
    }
    baseDependenciesAndroid()
    featureModuleBaseDependencies()
    room()
    networking()
    daggerAssisted()
    glide()
}