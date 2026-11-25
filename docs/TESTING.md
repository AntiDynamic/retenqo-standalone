# Verification evidence — 25 November 2026

## Fresh repository verification — 26 November 2026

**Code source revision:** `625f4f6e86a339472f49ce2fc7991f71cc48fae3` in the standalone Retenqo repository. These results apply to that source snapshot; this documentation update does not change application code.

| Check | Result | Scope |
| --- | --- | --- |
| Android JVM, lint, debug build and AndroidTest APK | Passed | `:app:testDebugUnitTest :app:lintDebug :app:assembleDebug :app:assembleDebugAndroidTest`; 122 JVM tests, 0 failures/errors |
| Android script test entry point | Passed | `bash scripts/test-mneme.sh`; wrapper now invokes non-executable tracked scripts via Bash |
| Website formatting, lint, typecheck, unit tests and build | Passed | Prettier check; 0 Astro diagnostics; 26/26 Vitest; 13 static pages |
| Website browser/accessibility suite | 110 passed, 74 skipped | Playwright 1.65.1 Ubuntu image; Chromium, Pixel mobile, Firefox and WebKit. Axe scans cover Chromium desktop/mobile across routes and themes; remaining skips are project-specific by design. |
| Website coverage | 93.75% statements, 92.85% branches, 100% functions, 94.59% lines | Vitest V8; configured `src/lib/**/*.ts` scope only |
| Website dependency audit | No known vulnerabilities | `pnpm audit --audit-level=low`; web package graph only |
| Device instrumentation | Not run | No Android emulator/system image or connected device was available; AndroidTest APK compiled successfully. |

The Linux host is Arch-based and cannot launch Playwright's Ubuntu WebKit binary directly. The complete browser suite was therefore run in the official Playwright Ubuntu image, where all configured browser projects completed. Android TalkBack/Switch Access, device instrumentation and on-device performance remain unverified on this host.

**Release:** [Retenqo v0.1.4](https://github.com/priyanshuchawda/retenqo/releases/tag/v0.1.4)

The recorded evidence combines Android JVM XML results, device release verification and website test/build results. It is scoped to the tested development revisions and published artifact, not a fresh execution of this documentation revision.

## Quality verification

| Check | Recorded result | Scope |
| --- | --- | --- |
| Android JVM | 114/114 passed; 0 failures, 0 errors, 0 skipped | 21 JUnit XML result files |
| Android instrumentation | 6/6 passed | Earlier v0.1.4 device run, Android 16/API 36; no new device run in report |
| Android Lint | 0 errors, 34 warnings, 1 hint | Local XML |
| Android builds | Debug and AndroidTest APK assembly passed | Recorded development source |
| Signed release | Publisher signature verified; in-place upgrade and launch succeeded | Published APK, Android 16/API 36 |
| Website Vitest | 25/25 passed, 4 files | Local migrated worktree |
| Website browser tests | 98 passed, 68 skipped, 2 flaky tests passed on retry | Latest recorded development run; Chromium, Firefox, WebKit |
| Website production build | 12 static pages; 0 errors, 0 warnings, 0 hints | Local migrated worktree |
| Website lint and typecheck | Passed; type diagnostics 0 errors, warnings, hints | Local migrated worktree |
| Website dependency audit | No known vulnerabilities | pnpm dependency graph and lockfile only |
| Website formatting | Earlier Linux check passed; local combined verify stopped at formatting | Windows CRLF checkout: 61 files flagged; individual lint, type, test and build checks passed |

The two browser retry cases were dark-theme accessibility checks on the privacy desktop and FAQ mobile pages. An earlier browser baseline recorded 100 passes and 68 skips; the latest run supersedes it for current reporting. Full axe coverage was scoped to Chromium desktop/mobile, with smoke coverage on Firefox/WebKit.

## Measured coverage

The website library coverage summary below is separate from the Android JVM-only measurement record; neither represents whole-product coverage.

| Website V8 metric | Covered / total | Coverage |
| --- | --- | --- |
| Statements | 45 / 48 | 93.75% |
| Lines | 35 / 37 | 94.59% |
| Functions | 9 / 9 | 100% |
| Branches | 39 / 42 | 92.85% |

Website measurement: Vitest 5.1.1 with V8; configured scope `src/lib/**/*.ts`. It does not represent all website pages or Android source.

Android JVM-only coverage was measured with JaCoCo 0.8.13 using a temporary Gradle init script. The [detailed Android measurement record](https://github.com/priyanshuchawda/retenqo/blob/51ee2b1ea009ad1fe44d670f3181c4bde0824fce/docs/TESTING.md#measured-coverage) retains the original counts and percentages. This measurement excludes device instrumentation and native C/C++ code; the coverage configuration is not committed.

## Source provenance

- Earlier development verification revision: `317192923586f81007679a5cb6b0da642b310285` in the original development repository.
- Local Android test/lint artifacts: `49a24a902984c313cd4902148b70aa23e0e18608`; Android source was unchanged between the two earlier revisions.
- Local website run: development revision plus 16 repository-name/release-link edits; diff SHA-256 `b8c20d11e52e4743ffe1668d4cfb856038facf9bfacc48ec460fc8bd61c9cd24`.
- Clean submission baseline: `834b04e62dedc2cf412c7f5fc2c967489ecd9411`. Repository tree comparison confirms the Kotlin app and test files match the earlier development revision; two native Whisper JNI files and several website files differ. The published APK digest matches the previous release artifact.
- The above results were supplied in the 25 November verification report. No fresh runtime test was executed as part of documenting it.

## Device and workflow coverage

The signed v0.1.4 upgrade succeeded on Android 16/API 36 (Xiaomi 2406ERN9CI). The six earlier instrumentation tests include the pooled SQLCipher connection regression, migration and crypto tests. The release retains encrypted database protection. Earlier Galaxy M32/API 33 observations covered local model generation with connectivity disabled, database migration and selected controls. The declared compatibility minimum is API 33; v0.1.4 API 33 upgrade, Galaxy M34 and full voice, reminder delivery, picker/share, screen-context and encrypted backup restore workflows require additional physical-device verification. See [security review](SECURITY_REVIEW.md) and [traceability](REQUIREMENTS_TRACEABILITY.md).

## Published artifact identity

| Field | Value |
| --- | --- |
| Version | v0.1.4 (version code 5) |
| Package | `com.mneme.app` |
| Compatibility | Android 13+ / API 33+, ARM64 |
| APK | [Retenqo-v0.1.4-arm64-v8a.apk](https://github.com/priyanshuchawda/retenqo/releases/download/v0.1.4/Retenqo-v0.1.4-arm64-v8a.apk) |
| Size | 39,374,307 bytes |
| SHA-256 | `b31770666bf7b860e9dc3e562a97de7286c6ad31347f67a1ce8fa96fdabf8f98` |
| Signing certificate SHA-256 | `b975fe4b5d929586e9bf0133ebde25c03d0acf990ddbba5c14f0f2b31ae38cca` |

Source: [release notes](https://github.com/priyanshuchawda/retenqo/releases/tag/v0.1.4) and [generated website metadata](../web/src/data/release.generated.json). The release reports signature verification and successful in-place upgrade without uninstall or data reset.


## Toolchain recorded

| Area | Versions |
| --- | --- |
| Android | JDK 21, Gradle 8.14.3, AGP 8.13.2, Kotlin 2.4.10, compile/target API 36, minimum API 33 |
| Native | NDK 28.2.13676358, CMake 4.1.2 |
| Website local | Node 26.13.1, pnpm 12.21.0, Astro 7.4.13, Vitest 5.1.1, Playwright 1.65.1 |
| Coverage | JaCoCo 0.8.13, Vitest V8 |

## Reproduction

From `android/mneme/`:

```bash
./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug :app:assembleDebugAndroidTest
./gradlew :app:connectedDebugAndroidTest
```

From `web/`:

```bash
pnpm verify
pnpm test:coverage
pnpm test:e2e
pnpm audit --audit-level=low
pnpm release:sync
```

Record source revision, test variant, device/API, model, commands, pass/fail/skip counts and measurement scope. Android coverage reproduction requires the temporary init-script configuration or an equivalent committed setup.
