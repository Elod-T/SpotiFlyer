import org.jetbrains.compose.compose

plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:data-models"))
                implementation(project(":common:database"))
                implementation(project(":fuzzywuzzy:app"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
                implementation(Ktor.clientCore)
                implementation(Ktor.clientSerialization)
                implementation(Ktor.clientLogging)
                implementation(Ktor.clientJson)
                implementation(Ktor.auth)
                // koin
                api(Koin.core)
                api(Koin.test)

                api(Extras.kermit)
                api(Extras.youtubeDownloader)
                api(Extras.mp3agic)
            }
        }
        androidMain {
            dependencies{
                implementation(compose.materialIconsExtended)
                implementation(Ktor.clientAndroid)
                implementation(Extras.Android.fetch)
                implementation(Koin.android)
                implementation(Extras.Android.razorpay)
                //api(files("$rootDir/libs/mobile-ffmpeg.aar"))
            }
        }
        desktopMain {
            dependencies{
                implementation(compose.materialIconsExtended)
                implementation(Ktor.clientApache)
                implementation(Ktor.slf4j)
            }
        }
        jsMain {
            dependencies {
                implementation(Ktor.clientJs)
                implementation(project(":common:data-models"))
            }
        }
    }
}
