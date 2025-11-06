# 📍 Lokasi Konfigurasi Distribusi JitPack

## ✅ Konfigurasi JitPack Anda Terletak Di:

### 1️⃣ **File Utama: `/timelineschedule/build.gradle.kts`**

Ini adalah file **library module** yang berisi konfigurasi Maven Publishing untuk JitPack.

**Path Lengkap:**
```
/Users/fadhyyusuf/AndroidStudioProjects/timelineschedule/timelineschedule/build.gradle.kts
```

**Isi Konfigurasi:**

```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")  // ← Plugin untuk publishing
}

// ... android configuration ...

// Publishing configuration for JitPack
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.github.fadhyyusuf"
                artifactId = "timelineschedule"
                version = "1.0.0"
                
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
```

### 2️⃣ **File Tambahan: `/jitpack.yml`**

File ini mengatur environment JitPack (JDK version, build commands, dll).

**Path Lengkap:**
```
/Users/fadhyyusuf/AndroidStudioProjects/timelineschedule/jitpack.yml
```

**Isi:**
```yaml
jdk:
  - openjdk17

install:
  - echo "Building Timeline Schedule Library"
  - ./gradlew clean build publishToMavenLocal
```

---

## 📂 Struktur File

```
timelineschedule/
├── build.gradle.kts                           # Root project (tidak perlu maven-publish)
├── jitpack.yml                                # ✅ JitPack configuration (BARU DIBUAT)
├── settings.gradle.kts                        # Repository configuration
│
├── app/
│   └── build.gradle.kts                       # Demo app (tidak perlu maven-publish)
│
└── timelineschedule/                          # ← LIBRARY MODULE
    └── build.gradle.kts                       # ✅ KONFIGURASI JITPACK ADA DI SINI (SUDAH DIUPDATE)
```

---

## 🎯 Detail Konfigurasi

### Plugin Maven Publish
```kotlin
plugins {
    id("maven-publish")  // Diperlukan untuk JitPack
}
```

### Publishing Block
```kotlin
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                // Konfigurasi di sini
            }
        }
    }
}
```

### Informasi Penting

| Field | Value | Keterangan |
|-------|-------|------------|
| **groupId** | `com.github.fadhyyusuf` | Username GitHub Anda |
| **artifactId** | `timelineschedule` | Nama repository |
| **version** | `1.0.0` | Versi library (ubah sesuai kebutuhan) |

---

## 🚀 Cara Menggunakan

### Untuk Pengguna Library (di project lain):

```kotlin
// settings.gradle.kts
repositories {
    maven { url = uri("https://jitpack.io") }
}

// app/build.gradle.kts
dependencies {
    implementation("com.github.fadhyyusuf:timelineschedule:1.0.0")
}
```

### Untuk Publish Versi Baru:

1. **Update version** di `timelineschedule/build.gradle.kts`:
   ```kotlin
   version = "1.0.1"  // Ubah versi
   ```

2. **Commit & Push**:
   ```bash
   git add .
   git commit -m "Release v1.0.1"
   git push origin master
   ```

3. **Create Git Tag**:
   ```bash
   git tag -a v1.0.1 -m "Version 1.0.1"
   git push origin v1.0.1
   ```

4. **JitPack akan otomatis build** setelah tag di-push!

5. **Check build status** di:
   ```
   https://jitpack.io/#fadhyyusuf/timelineschedule
   ```

---

## ✅ Yang Sudah Saya Lakukan:

1. ✅ Menambahkan plugin `maven-publish` ke `timelineschedule/build.gradle.kts`
2. ✅ Menambahkan konfigurasi `publishing` block dengan info lengkap
3. ✅ Membuat file `jitpack.yml` di root project
4. ✅ Set groupId = `com.github.fadhyyusuf`
5. ✅ Set artifactId = `timelineschedule`
6. ✅ Set version = `1.0.0`
7. ✅ Tambahkan metadata (license, developer, scm)

---

## 📝 Catatan Penting:

1. **File yang DIBUTUHKAN untuk JitPack:**
   - ✅ `timelineschedule/build.gradle.kts` (dengan maven-publish)
   - ✅ `jitpack.yml` (opsional tapi recommended)

2. **File yang TIDAK perlu diubah:**
   - ❌ `app/build.gradle.kts` (ini demo app, bukan library)
   - ❌ Root `build.gradle.kts` (tidak perlu maven-publish)

3. **Version Control:**
   - Version di `build.gradle.kts` harus match dengan Git tag
   - Format tag: `v1.0.0`, `v1.0.1`, dll.

---

## 🔍 Cara Verify:

### Test Build Lokal:
```bash
./gradlew :timelineschedule:build
./gradlew :timelineschedule:publishToMavenLocal
```

### Check Published Artifact:
```bash
ls ~/.m2/repository/com/github/fadhyyusuf/timelineschedule/
```

---

## 📞 Troubleshooting:

### Jika JitPack Build Failed:

1. Check build log di JitPack website
2. Pastikan file `jitpack.yml` ada di root
3. Pastikan JDK version cocok (Java 11 atau 17)
4. Pastikan tidak ada syntax error di `build.gradle.kts`

### Jika Version Tidak Muncul:

1. Pastikan tag sudah di-push ke GitHub
2. Tunggu beberapa menit untuk JitPack build
3. Refresh halaman JitPack

---

**Lokasi File Konfigurasi:**
- 📁 **Library Build Config:** `/timelineschedule/build.gradle.kts` (baris 41-73)
- 📁 **JitPack Config:** `/jitpack.yml`

Semuanya sudah siap untuk di-publish ke JitPack! 🎉

---

Made with ❤️ and AI assistance

