# Accessibility verification

Accessibility of Retenqo's own UI is distinct from its optional Android Accessibility capture service.

Target inclusive Android usability: meaningful Compose semantics, descriptive labels, at least 48dp touch targets, focus order, accessible state changes, non-color-only cues, text scaling and sufficient contrast. Test TalkBack and Switch Access on supported devices; verify permissions, capture, task review, Ask, backup and errors without relying on gestures alone.

The implementation report documents labeled microphone and expandable settings controls. The current evidence covers implemented semantics; comprehensive manual TalkBack and Switch Access verification is a separate evaluation activity. Record device, OS, scenario and outcome for each run.

## Evidence navigation

Fresh browser verification at source revision `625f4f6` ran 110 Playwright tests with 74 expected project-specific skips. Chromium desktop and mobile ran axe checks for each website route in light and dark themes; Firefox and WebKit completed the configured cross-browser smoke paths. The synthetic workflow E2E verifies keyboard activation, focus moving to each step heading, and status announcements. This does not replace manual TalkBack, Switch Access, VoiceOver or zoom checks.

Existing control semantics can be inspected in [MnemeHomeScreen.kt](../android/mneme/app/src/main/java/com/mneme/app/ui/timeline/MnemeHomeScreen.kt) and [SettingsPane.kt](../android/mneme/app/src/main/java/com/mneme/app/ui/timeline/SettingsPane.kt). Website automation and manual verification boundaries are recorded in [website accessibility](../web/docs/ACCESSIBILITY.md) and [testing](TESTING.md). This documentation pass did not change application presentation or execute new assistive-technology tests.
