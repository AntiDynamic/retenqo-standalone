# Evaluation baseline

## Source and environment

- Repository: `https://github.com/priyanshuchawda/retenqo`
- Base commit: `38857e4eb3a7bc539b29d2be04a35e1b6b55398e`
- Base branch: `main`; working branch: `codex/retenqo-quality-vercel-20261125`
- Base checkout was clean. The existing `retenqo-enterprise` directory was left unchanged.
- Baseline source verification recorded in `docs/TESTING.md` is historical, not a fresh run: 114 Android JVM tests; 6 Android instrumentation tests on API 36; 25 website unit tests; 98 browser passes, 68 documented skips and 2 retry passes.
- This host has JDK 21, Node 24.20.0 and pnpm 12.21.0. Android SDK 36 and the pinned NDK/CMake are installed. No Android device/emulator is attached. Vercel CLI is authenticated and the `retenqo` project is configured for Astro with `web/` as the deployment working directory.

## Initial observations

| Evaluation category | Baseline evidence and gap |
| --- | --- |
| Problem Statement Alignment | README has a self-defined Open Innovation statement, but no accessible Hack2Skill submission metadata confirms the selected challenge or evaluated SHA. Source/test evidence was not organized as direct requirement → code → test links. |
| Code Quality | Historical lint had zero errors and 34 warnings. `NoteRepository.retrieveScored` searches a recency-limited candidate set; task, reminder and screen behavior need further verification. |
| Security | SQLCipher, Keystore key protection and encrypted backups are present. Notification queue preferences contain app-private plaintext. Consent revocation is checked before processing but its in-flight save race needs separate review. |
| Efficiency | The 0.8B Q4_0 model is optional and downloaded separately; intake and retrieval are bounded. Current-device latency and memory were not measured at this base. |
| Testing | Historical results are documented, but Android CI builds instrumentation APKs without running a connected device suite. A clean local Android test initially could not configure until pinned native checkouts were bootstrapped. |
| Accessibility | Website keyboard/axe coverage exists; no new TalkBack device run is available. Android semantics and task confirmation still need stronger automated coverage. |

## Baseline commands and results

- `pnpm install --frozen-lockfile` stopped at pnpm's ignored build-script gate for `esbuild`. The repository CI command, `pnpm install --frozen-lockfile --allow-build esbuild`, completed successfully.
- Fresh baseline website: `pnpm lint && pnpm typecheck && pnpm test && pnpm build` — lint/typecheck passed with zero Astro diagnostics, 25/25 Vitest passed, and 12 static pages built.
- Android first attempt could not resolve `:llama-runtime` before native dependencies were bootstrapped. `bash scripts/bootstrap-native-deps.sh` then provisioned the exact pinned Whisper and llama.cpp revisions. Final matrix `:app:testDebugUnitTest :app:lintDebug :app:assembleDebug :app:assembleDebugAndroidTest` passed. JVM reports contain 122 tests, 0 failures/errors. No emulator/device was attached, so connected instrumentation tests were not run.
- Fresh website gate: formatting, lint, typecheck, unit tests, build, and dependency audit passed; Astro diagnostics were 0 errors/warnings/hints, 26/26 Vitest tests passed, and 13 static pages built. The full Playwright run passed 110 tests with 74 project-specific skips in the official Ubuntu browser image. The walkthrough test asserts keyboard-triggered progression, focus movement, and status announcements.
- Vercel preview at `https://retenqo-kgnymqxwc-gayaladitya9-3172s-projects.vercel.app` reached READY and its `/demo` content was checked with authenticated Vercel curl. Production is live at `https://retenqo.vercel.app`; `/`, `/demo`, `/robots.txt`, `/sitemap-index.xml` all return HTTP 200 with the configured security headers. Preview deployment protection requires authentication, production alias is publicly reachable.
- Connected Android instrumentation tests are not run: `adb devices -l` found no device/emulator. Existing APK v0.1.4 and its signing identity were not modified.

## Acceptance ledger

| Change | Regression risk | Acceptance evidence |
| --- | --- | --- |
| Event-scoped notification deduplication | Duplicate intake or loss of independent same-text events | Source-event retry dedupes; other sources and updated content remain separate; unit tests pass. |
| Indian slash-date parsing | Misinterpreting an existing deadline | Explicit/ambiguous slash dates use the documented India-first convention; date tests pass. |
| Completion and Ask guards | False refusal or false completion | Negated/future completion is rejected; contradictory cited time/amount is rejected; visible screen facts can be answered; unit tests pass. |
| Confirmed task transaction | Stale suggestion reports success or cancels an active alarm | Stale suggestions do not mutate task/suggestion state; device test source compiles and is run when a compatible device is available. |
| Vercel configuration and walkthrough | Security headers drift or browser demo misrepresents Android | Parity test passes; browser flow is labeled synthetic, approval gated, keyboard accessible and E2E tested. |
| README and evidence synchronization | Claims exceed the published APK or available test evidence | Direct source/test links resolve; historical and fresh results are revision-scoped; APK identity remains v0.1.4. |
