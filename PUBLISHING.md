# Publishing Guide

## Publishing to JitPack

### Prerequisites
1. Create a GitHub repository
2. Push your code to GitHub
3. Create a release tag

### Steps

#### 1. Update build.gradle.kts

Replace your `timelineschedule/build.gradle.kts` with `build.gradle.kts.publish`:

```bash
cd timelineschedule
cp build.gradle.kts.publish build.gradle.kts
```

#### 2. Create jitpack.yml

Create a file named `jitpack.yml` in the root directory:

```yaml
jdk:
  - openjdk11
before_install:
  - sdk install java 11.0.10-open
  - sdk use java 11.0.10-open
```

#### 3. Push to GitHub

```bash
git init
git add .
git commit -m "Initial commit: Timeline Schedule Library"
git remote add origin https://github.com/YOUR_USERNAME/timelineschedule.git
git push -u origin main
```

#### 4. Create a Release

```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

Or create a release on GitHub:
1. Go to your repository on GitHub
2. Click "Releases" → "Create a new release"
3. Tag: `v1.0.0`
4. Title: `Version 1.0.0`
5. Description: Initial release with features...
6. Click "Publish release"

#### 5. Build on JitPack

1. Go to https://jitpack.io
2. Enter your repository URL: `https://github.com/YOUR_USERNAME/timelineschedule`
3. Click "Look up"
4. Click "Get it" next to v1.0.0
5. Wait for the build to complete (green checkmark)

### Usage After Publishing

Users can add your library:

#### In settings.gradle.kts (or settings.gradle):

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

#### In app/build.gradle.kts:

```kotlin
dependencies {
    implementation("com.github.YOUR_USERNAME:timelineschedule:1.0.0")
}
```

## Publishing to Maven Central

### Prerequisites
1. Create a Sonatype JIRA account
2. Set up GPG keys
3. Configure credentials

### Steps

#### 1. Create Sonatype Account

Go to https://issues.sonatype.org and create an account

#### 2. Create New Project Ticket

Create a ticket requesting a new project:
- Group Id: `com.github.fadhyyusuf`
- Project URL: `https://github.com/fadhyyusuf/timelineschedule`

#### 3. Set up GPG Keys

```bash
# Generate GPG key
gpg --gen-key

# List keys
gpg --list-keys

# Export public key
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID

# Export private key
gpg --export-secret-keys YOUR_KEY_ID > secring.gpg
```

#### 4. Configure gradle.properties

Add to `~/.gradle/gradle.properties`:

```properties
signing.keyId=YOUR_KEY_ID
signing.password=YOUR_KEY_PASSWORD
signing.secretKeyRingFile=/path/to/secring.gpg

ossrhUsername=YOUR_SONATYPE_USERNAME
ossrhPassword=YOUR_SONATYPE_PASSWORD
```

#### 5. Update build.gradle.kts

Add publishing and signing:

```kotlin
plugins {
    // ... existing plugins
    id("maven-publish")
    id("signing")
}

// ... android configuration

publishing {
    publications {
        create<MavenPublication>("release") {
            // ... maven configuration
        }
    }
    
    repositories {
        maven {
            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            
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

#### 6. Publish

```bash
./gradlew :timelineschedule:publishReleasePublicationToMavenRepository
```

#### 7. Release on Sonatype

1. Go to https://oss.sonatype.org
2. Login
3. Click "Staging Repositories"
4. Find your repository
5. Click "Close"
6. Wait for validation
7. Click "Release"

## Version Numbering

Follow Semantic Versioning (SemVer):

- **MAJOR**: Incompatible API changes (1.0.0 → 2.0.0)
- **MINOR**: Add functionality (backward-compatible) (1.0.0 → 1.1.0)
- **PATCH**: Bug fixes (backward-compatible) (1.0.0 → 1.0.1)

## Release Checklist

- [ ] Update version in build.gradle.kts
- [ ] Update CHANGELOG.md
- [ ] Update README.md if needed
- [ ] Run all tests: `./gradlew test`
- [ ] Build release: `./gradlew :timelineschedule:assembleRelease`
- [ ] Commit changes
- [ ] Create git tag
- [ ] Push to GitHub
- [ ] Create GitHub release
- [ ] Publish to JitPack/Maven
- [ ] Update documentation

## Troubleshooting

### JitPack Build Failed

1. Check build logs on JitPack.io
2. Ensure `jitpack.yml` is correct
3. Verify JDK version compatibility
4. Check gradle version compatibility

### Maven Central Upload Failed

1. Verify credentials in gradle.properties
2. Check GPG key is valid
3. Ensure all POM requirements are met
4. Verify repository URL is correct

## Sample CHANGELOG.md

```markdown
# Changelog

## [1.0.0] - 2024-11-06

### Added
- Initial release
- Timeline view with automatic overlap handling
- Customizable appearance via TimelineConfig
- Support for click and long-click listeners
- Material Design components
- Comprehensive documentation

### Features
- Displays appointments in timeline format
- Automatic overlap detection and positioning
- Side-by-side display for overlapping appointments
- Customizable colors, sizes, and formats
- Support for Android API 21+
```

## Resources

- [JitPack Documentation](https://jitpack.io/docs/)
- [Maven Central Guide](https://central.sonatype.org/publish/)
- [Semantic Versioning](https://semver.org/)
- [Android Library Publishing](https://developer.android.com/studio/projects/android-library)

