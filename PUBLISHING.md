# Publishing Guide

> **⚠️ AI-Generated Project Disclaimer**  
> This project was created with the assistance of Artificial Intelligence (AI). While the code has been reviewed and tested, users should verify functionality for their specific use cases.

## Publishing to JitPack

JitPack is the easiest way to publish Android libraries. It builds your library directly from your GitHub repository.

### Prerequisites

1. **GitHub Account**: Your code must be in a GitHub repository
2. **Git Tags**: Releases are based on Git tags
3. **Gradle Setup**: Proper build configuration

### Step 1: Prepare Your Library Module

Ensure your library module (`timelineschedule/build.gradle.kts`) has the correct configuration.

#### Current Configuration

```kotlin
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.fy.timelineschedule"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
}

// Publishing configuration
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.fadhyyusuf"
                artifactId = "timelineschedule"
                version = "1.0.0"
            }
        }
    }
}
```

### Step 2: Create a Release

#### 2.1. Commit Your Changes

```bash
git add .
git commit -m "Release version 1.0.0"
git push origin master
```

#### 2.2. Create a Git Tag

```bash
# Create an annotated tag
git tag -a v1.0.0 -m "Release version 1.0.0"

# Push the tag to GitHub
git push origin v1.0.0
```

#### 2.3. Create GitHub Release (Optional but Recommended)

1. Go to your repository on GitHub
2. Click "Releases" → "Create a new release"
3. Choose the tag you just created (v1.0.0)
4. Add release title: "Version 1.0.0"
5. Add release notes (see example below)
6. Click "Publish release"

**Example Release Notes:**

```markdown
## Version 1.0.0

### Features
- Vertical timeline view for appointments
- Support for overlapping appointments
- Current time indicator
- Customizable colors and styles
- 12-hour and 24-hour format support
- Custom time labels
- Grid lines and dividers
- Click listeners for appointments

### Installation
```gradle
dependencies {
    implementation 'com.github.fadhyyusuf:timelineschedule:1.0.0'
}
```

### Requirements
- Min SDK: 21 (Android 5.0+)
- Kotlin 1.9+
```

### Step 3: Build on JitPack

#### 3.1. Trigger JitPack Build

Visit: `https://jitpack.io/#fadhyyusuf/timelineschedule`

JitPack will automatically detect your new tag and start building.

#### 3.2. Check Build Status

- **Green**: Build successful ✅
- **Red**: Build failed ❌
- **Yellow**: Building in progress 🔄

#### 3.3. If Build Fails

Click on the build log to see errors. Common issues:

1. **Missing maven-publish plugin**: Add to library's `build.gradle.kts`
2. **Incorrect publishing configuration**: Check `afterEvaluate` block
3. **Dependency issues**: Ensure all dependencies are available

### Step 4: Verify Installation

Test the library in a new project:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

// app/build.gradle.kts
dependencies {
    implementation("com.github.fadhyyusuf:timelineschedule:1.0.0")
}
```

### Step 5: Update README

Add JitPack badge to your README.md:

```markdown
[![](https://jitpack.io/v/fadhyyusuf/timelineschedule.svg)](https://jitpack.io/#fadhyyusuf/timelineschedule)
```

## Version Naming Convention

Follow semantic versioning: `MAJOR.MINOR.PATCH`

- **MAJOR**: Breaking changes (e.g., 2.0.0)
- **MINOR**: New features, backward compatible (e.g., 1.1.0)
- **PATCH**: Bug fixes, backward compatible (e.g., 1.0.1)

### Examples

```bash
# New features
git tag -a v1.1.0 -m "Add week view support"

# Bug fixes
git tag -a v1.0.1 -m "Fix current time indicator positioning"

# Breaking changes
git tag -a v2.0.0 -m "Refactor API with breaking changes"
```

## Publishing to Maven Central (Advanced)

For official Maven Central publishing:

### Prerequisites

1. Sonatype OSSRH account
2. GPG key for signing
3. Maven Central group ID verification

### Configuration

Add to `build.gradle.kts`:

```kotlin
plugins {
    id("maven-publish")
    id("signing")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["release"])
            
            groupId = "io.github.fadhyyusuf"
            artifactId = "timelineschedule"
            version = "1.0.0"
            
            pom {
                name.set("Timeline Schedule")
                description.set("A flexible timeline view for Android")
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
                        email.set("your.email@example.com")
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
    
    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.findProperty("ossrhUsername") as String? ?: ""
                password = project.findProperty("ossrhPassword") as String? ?: ""
            }
        }
    }
}

signing {
    sign(publishing.publications["release"])
}
```

## Continuous Integration

### GitHub Actions for Automatic Publishing

Create `.github/workflows/publish.yml`:

```yaml
name: Publish to JitPack

on:
  release:
    types: [created]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Build with Gradle
      run: ./gradlew build
    
    - name: Publish to Maven Local
      run: ./gradlew publishToMavenLocal
```

## Best Practices

### 1. Changelog

Maintain a [CHANGELOG.md](CHANGELOG.md) file:

```markdown
# Changelog

## [1.0.1] - 2025-01-15
### Fixed
- Current time indicator positioning bug

## [1.0.0] - 2025-01-10
### Added
- Initial release
- Timeline view with appointments
- Custom time labels support
```

### 2. Documentation

Before releasing:
- [ ] Update README.md
- [ ] Update version in all documentation
- [ ] Add migration guide for breaking changes
- [ ] Update example code

### 3. Testing

Before releasing:
- [ ] Run all unit tests
- [ ] Test in sample app
- [ ] Test on different Android versions
- [ ] Test ProGuard/R8 configuration

### 4. Versioning

```bash
# Update version in build.gradle.kts
# Then create tag
git tag -a v1.0.1 -m "Version 1.0.1"
git push origin v1.0.1
```

## Troubleshooting

### JitPack Build Fails

1. **Check build log**: Click on the log icon on JitPack
2. **Verify dependencies**: Ensure all dependencies are publicly available
3. **Test locally**: Run `./gradlew build` locally first
4. **Check JDK version**: JitPack uses JDK 11 by default

Add `jitpack.yml` to specify JDK version:

```yaml
jdk:
  - openjdk17
```

### Tag Already Exists

```bash
# Delete local tag
git tag -d v1.0.0

# Delete remote tag
git push origin :refs/tags/v1.0.0

# Create new tag
git tag -a v1.0.0 -m "Version 1.0.0"
git push origin v1.0.0
```

### Authentication Issues

For Maven Central, store credentials in `~/.gradle/gradle.properties`:

```properties
ossrhUsername=your-username
ossrhPassword=your-password
signing.keyId=your-key-id
signing.password=your-key-password
signing.secretKeyRingFile=/path/to/secring.gpg
```

## Quick Release Checklist

- [ ] Update version number in `build.gradle.kts`
- [ ] Update CHANGELOG.md
- [ ] Update README.md with new version
- [ ] Run tests: `./gradlew test`
- [ ] Build locally: `./gradlew build`
- [ ] Commit changes: `git commit -am "Release vX.Y.Z"`
- [ ] Push to GitHub: `git push origin master`
- [ ] Create tag: `git tag -a vX.Y.Z -m "Version X.Y.Z"`
- [ ] Push tag: `git push origin vX.Y.Z`
- [ ] Create GitHub Release with notes
- [ ] Verify JitPack build
- [ ] Test installation in new project

## Support

For publishing issues:
- [JitPack Documentation](https://jitpack.io/docs/)
- [Maven Publishing Guide](https://docs.gradle.org/current/userguide/publishing_maven.html)
- Open an issue on GitHub

---

Made with ❤️ and AI assistance

