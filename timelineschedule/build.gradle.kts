plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.fy.timelineschedule"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Publishing configuration for JitPack
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.fadhyyusuf"
                artifactId = "timelineschedule"
                version = "1.0.1"

                pom {
                    name.set("Timeline Schedule")
                    description.set("A flexible and customizable timeline view for Android applications")
                    url.set("https://github.com/fadhyyusuf/timelineschedule")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            id.set("fadhyyusuf")
                            name.set("Fadhy Yusuf")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/fadhyyusuf/timelineschedule.git")
                        developerConnection.set("scm:git:ssh://github.com/fadhyyusuf/timelineschedule.git")
                        url.set("https://github.com/fadhyyusuf/timelineschedule")
                    }
                }
            }
        }
    }
}
