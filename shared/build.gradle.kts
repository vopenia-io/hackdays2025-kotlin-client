plugins {
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.android.library)
    alias(additionals.plugins.jetbrains.compose)
    alias(additionals.plugins.kotlin.serialization)
    alias(additionals.plugins.kotlin.cocoapods)
    alias(additionals.plugins.compose.compiler)
    id("jvmCompat")
}

val sampleNamespaceShared = "io.vopenia.meet.shared"

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../appIos/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
            embedBitcode("disable")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)

            api(additionals.kotlinx.serialization.json)

            api(additionals.multiplatform.precompose)
            api(additionals.multiplatform.safearea)
            api(additionals.multiplatform.widgets.compose)
            api(additionals.multiplatform.permissions)
            api(additionals.multiplatform.platform)
            api(additionals.multiplatform.http.client)
            api(additionals.multiplatform.viewmodel)
            api(additionals.multiplatform.file.access)

            api(additionals.hotpreview)

            api(libs.meet.sdk)
            api(libs.meet.sdk.compose)
            implementation(libs.vopenia.utils)

            api(projects.konfig)
        }
        commonTest.dependencies {
            implementation(additionals.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = sampleNamespaceShared
}

compose.resources {
    publicResClass = true
    packageOfResClass = "$sampleNamespaceShared.res"
    generateResClass = always
}
