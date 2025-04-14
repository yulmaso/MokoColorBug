plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.mokoResources)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            export(libs.mokoResources.core)
            export(libs.mokoGraphics)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            api(libs.mokoResources.core)
        }
        iosMain.dependencies {
            api(libs.mokoGraphics)
        }
    }
}

multiplatformResources {
    resourcesPackage.set("org.example.project") // required
    resourcesClassName.set("SharedRes") // optional, default MR
}