# Food Delivery App

An Android food delivery application built with Jetpack Compose and Material Design 3.

## Features

- 🍕 Browse restaurants and menus
- 🛒 Add items to cart with quantity management
- 💳 Checkout with delivery information
- ✅ Order confirmation
- 📱 Modern Material Design 3 UI
- 🎨 Clean MVVM architecture

## Tech Stack

- **Kotlin** - Modern programming language for Android
- **Jetpack Compose** - Modern declarative UI toolkit
- **Material Design 3** - Latest Material Design components
- **Navigation Component** - Type-safe navigation with Compose
- **ViewModel** - Lifecycle-aware data management
- **StateFlow** - Reactive state management
- **MVVM Architecture** - Separation of concerns

## Project Structure

```
app/
├── src/main/java/com/fooddelivery/app/
│   ├── data/              # Data layer (repositories, sample data)
│   ├── model/             # Data models
│   ├── ui/
│   │   ├── navigation/    # Navigation routes
│   │   ├── screens/       # Compose screens
│   │   └── theme/         # App theming
│   ├── viewmodel/         # ViewModels
│   └── MainActivity.kt    # Entry point
```

## Screens

1. **Home Screen** - Display list of available restaurants
2. **Restaurant Detail** - Show restaurant info and menu items
3. **Cart Screen** - Review items, adjust quantities
4. **Checkout Screen** - Enter delivery details
5. **Order Confirmation** - Success message

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 8 or higher
- Android SDK with API level 24 or higher

### Building the App

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or device

```bash
./gradlew build
```

## Configuration

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34

## License

This project is open source and available under the MIT License.