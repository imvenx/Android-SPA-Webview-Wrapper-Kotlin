# Android Kotlin WebView SPA Wrapper

## Overview

This project is an Android application that wraps a Single Page Application (SPA) using Kotlin and WebView. It provides a straightforward solution to serve and display a SPA within an Android app, addressing common challenges that arise when integrating SPAs.

## Features

- **Serve SPA Locally**: SPAs can't be loaded like static pages, so we serve them from a local server within the app.
- **Display in WebView**: Renders the SPA seamlessly inside a WebView component.
- **Bypass Network Security Errors**: Configures network settings to avoid security errors when accessing the locally served SPA.
- **Preserve WebView State**: Maintains the state of the WebView during orientation changes and resizes to prevent reloads.
- **Enable Camera Access** (Optional): Allows the SPA to access the device's camera if needed.
- **Fullscreen Mode**: Enables the app to display in fullscreen for an immersive experience.
- **Dev Mode**: Serve the app from your dev environment directly to android studio so you can view changes without rebuild.

## Why This Approach?

SPAs rely on dynamic content and routing, which static page loading doesn't support. Serving the SPA locally ensures all functionalities work as intended. Orientation changes in Android can destroy and recreate activities, so preserving WebView state is crucial to maintain user progress. Bypassing network security for localhost is necessary since the SPA is served internally, avoiding unnecessary security blocks.

## Motivation

There isn't a straightforward tutorial available for serving SPAs on Android devices. This project fills that gap by providing a ready-to-use solution that works out of the box with a Vue.js app but is adaptable to any SPA framework.

## Getting Started

1. **Clone the Repository**: Download or clone this project to your local machine.
2. **Integrate Your SPA**: Place your SPA build files into the designated assets directory.
3. **Build and Run**: Compile the app using Android Studio and run it on your device or emulator.

## Compatibility

- Designed for **Android** devices.
- Compatible with **Vue.js** and other SPA frameworks like React or Angular.

## License

This project is open-source and available under the [MIT License](LICENSE).
