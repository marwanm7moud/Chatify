object Versions {
    const val CORE_VERSION = "1.8.0"
    const val APPCOMPAT_VERSION = "1.6.1"
    const val Material_VERSION = "1.6.1"
    const val JUNIT_VERSION = "4.13.2"
    const val TEST_JUNIT_VERSION = "1.1.5"
    const val ESPRESSO_VERSION = "3.5.1"
    const val LIFECYCLE_VERSION = "2.3.1"
    const val ACTIVITY_VERSION = "1.5.1"
    const val BOM_VERSION = "2022.10.00"
}

object DependencyProject {
    const val androidxCore = "androidx.core:core-ktx:${Versions.CORE_VERSION}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.APPCOMPAT_VERSION}"
    const val material = "com.google.android.material:material:${Versions.Material_VERSION}"
    const val junit = "junit:junit:${Versions.JUNIT_VERSION}"
    const val test = "androidx.test.ext:junit:${Versions.TEST_JUNIT_VERSION}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_VERSION}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_VERSION}"
    const val activity = "androidx.activity:activity-compose:${Versions.ACTIVITY_VERSION}"
    const val bom = "androidx.compose:compose-bom:${Versions.BOM_VERSION}"
    const val ui = "androidx.compose.ui:ui"
    const val ui_graphics = "androidx.compose.ui:ui-graphics"
    const val preview = "androidx.compose.ui:ui-tooling-preview"
    const val ui_test = "androidx.compose.ui:ui-test-junit4"
    const val ui_tooling = "androidx.compose.ui:ui-tooling"
    const val test_manifest = "androidx.compose.ui:ui-test-manifest"
}