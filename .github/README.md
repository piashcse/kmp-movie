# CI/CD Setup for KMP Movie App

This project uses GitHub Actions for continuous integration and deployment across all platforms: Android, iOS, Desktop, and Web.

## Workflows

### 1. Platform-Specific Workflows

- **Android** (`android.yml`): Builds and tests Android APKs
- **iOS** (`ios.yml`): Builds and tests iOS frameworks
- **Desktop** (`desktop.yml`): Builds desktop applications for Windows, macOS, and Linux
- **Web** (`web.yml`): Builds and deploys web application to GitHub Pages

### 2. Shared Workflows

- **CI** (`ci.yml`): Runs tests and builds for all platforms on push/PR
- **Tests** (`tests.yml`): Runs unit tests across all platforms
- **Deploy** (`deploy.yml`): Creates GitHub releases and deploys when tags are pushed

## Secrets Required

Add these secrets to your GitHub repository settings:

- `API_KEY`: The Movie Database API key for the app
- `GRADLE_ENCRYPTION_KEY`: Encryption key for Gradle cache (optional but recommended)

## Build Triggers

- **Push to main/develop**: Triggers CI workflow with tests and builds
- **Pull requests to main**: Triggers tests to ensure code quality
- **Tags (v*)**: Creates GitHub releases with artifacts for all platforms

## Deployment Strategy

- **Android**: APK files attached to GitHub releases
- **iOS**: Frameworks attached to GitHub releases (ready for Xcode integration)
- **Desktop**: Platform-specific installers attached to GitHub releases
- **Web**: Automatically deployed to GitHub Pages

## Caching Strategy

- Gradle cache is enabled to speed up builds
- Node.js/Yarn cache for web builds
- Java dependency caching

## Testing

- Common tests run on all platforms
- Platform-specific tests for each target
- Test reports are uploaded as artifacts for review