package modules

object Modules {

    const val APP = ":app"
    const val DOMAIN = ":domain"
    const val CORE = ":core"
    object Data {
        private const val dir = ":data"
        const val DATA = ":$dir:data-core"
        const val DATA_REMOTE = "$dir:data-remote"
        const val DATA_LOCAL = "$dir:data-local"
    }
    object Feature {
        private const val dir = ":features"
        const val FEATURE_ONE = "$dir:featureOne"
    }
    object Utility {
        private const val dir = ":utility"
        const val BASES = "$dir:bases"
        const val BASES_ANDROID = "$dir:bases-android"
    }
    object Test {
        private const val dir = ":test"
        const val CORE = "$dir:core-test"

    }
}