# Amazon Sample 🛒

A modern, modularized Android application demonstrating an e-commerce platform using the latest Android development practices and Jetpack Compose.

## 🚀 Overview

Amazon Sample is a showcase project built with **Clean Architecture** and **MVVM** principles. It features a highly modularized structure to ensure scalability, maintainability, and testability.

## 🛠 Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for a modern, declarative UI.
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for robust dependency management.
- **Database**: [Room](https://developer.android.com/training/data-storage/room) for local persistence.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) for API communication.
- **Async & Streams**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html).
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) for performant image loading.
- **Architecture**: Modular Clean Architecture with Feature-based modules.

## 🏗 Project Structure

The project is divided into three main layers:

- **`:app`**: The entry point, stitching all modules together.
- **`:feature`**: Contains independent business features.
  - `:feature:home` - Product listing and discovery.
  - `:feature:auth` - User registration and login.
  - `:feature:product` - Detailed product information.
  - `:feature:cart` - Shopping cart management.
  - `:feature:orders` - Order history and tracking.
  - `:feature:wishlist` - User's saved items.
- **`:core`**: Shared modules used across the application.
  - `:core:ui` - Design system and reusable UI components.
  - `:core:network` - API client and remote data sources.
  - `:core:database` - Room database and local data sources.
  - `:core:common` - Shared utilities, models, and interfaces.

## 🌟 Key Features

- ✅ **Product Discovery**: Browse a wide range of products with categories and ratings.
- ✅ **Shopping Cart**: Add/remove products and manage quantities.
- ✅ **Wishlist**: Save your favorite items for later.
- ✅ **Authentication**: Secure login and registration flow.
- ✅ **Order Management**: View past orders and status.
- ✅ **Offline Support**: Local caching using Room for a seamless experience.

## 🏁 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/amazon-sample.git
   ```
2. **Open in Android Studio**:
   Use **Android Studio Ladybug (or newer)** for the best experience.
3. **Build & Run**:
   Select the `app` configuration and hit the **Run** button.

## 🧪 Testing

The project includes unit tests and instrumentation tests to ensure reliability:
- **Unit Tests**: Business logic and ViewModels.
- **UI Tests**: Component testing with Compose.

## 📜 License

```text
Copyright 2024 Amazon Sample Contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```
