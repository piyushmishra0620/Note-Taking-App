# Implementation Plan - Add Room and KSP Dependencies

This plan adds the necessary plugins and dependencies for Room database and KSP (Kotlin Symbol Processing) to the project, as per the recent updates in the version catalog.

## User Review Required

> [!IMPORTANT]
> **KSP Version:** I have set the KSP version to `2.3.10` to maintain compatibility with Kotlin `2.4.10` as per current release mappings.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/piyus/AndroidStudioProjects/Note/gradle/libs.versions.toml)
- Replace the placeholder `<kotlinCompatibleKspVersion>` with `2.3.10`.

#### [MODIFY] [build.gradle.kts (root)](file:///C:/Users/piyus/AndroidStudioProjects/Note/build.gradle.kts)
- Add `ksp`, `androidx-room`, and `kotlin-serialization` plugins to the top-level `plugins` block with `apply false`. This centralizes version management for all modules.

#### [MODIFY] [build.gradle.kts (app)](file:///C:/Users/piyus/AndroidStudioProjects/Note/app/build.gradle.kts)
- Add Room dependencies: `androidx-room-runtime`, `androidx-room-compiler` (via KSP), and `androidx-sqlite-bundled`.
- Configure the `room` extension to set the schema directory, which is required by the Room Gradle plugin.

## Verification Plan

### Automated Tests
- Run `./gradlew build` to ensure all dependencies are correctly resolved and the annotation processing works.
- Verify that the Room schema directory is created/accessible during the build.

### Manual Verification
- Sync the project with Gradle files in Android Studio and check for any sync errors.
