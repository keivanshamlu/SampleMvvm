package groupDependencyModuleLevel

import androidDeps.groupDependencies.*
import kotlinDeps.groupDependencies.kotlinCoroutines
import org.gradle.api.artifacts.dsl.DependencyHandler
import test.groupDependencies.test
import test.groupDependencies.testAndroid

fun DependencyHandler.baseDependencies() {
    kotlinCoroutines()
    daggerKotlin()
    test()
    arch()
}
fun DependencyHandler.baseDependenciesAndroid() {
    baseDependencies()
    daggerAndroid()
    testAndroid()
}
fun DependencyHandler.featureModuleBaseDependencies() {
    androidDefault()
    fragment()
    lifeCycle()
    arch()
}