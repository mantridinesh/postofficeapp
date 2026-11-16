# Post Office Android Application 📮

An Android application for searching postal details, PIN codes, post office branch names, and saving favorite PIN codes. Built with Kotlin, Android Jetpack, ViewBinding, ViewPager2, and Firebase/Google Sign-In integration.

---

## ✨ Features

- 📍 **PIN Code Search**: Look up post offices and details by entering a PIN code.
- 🏢 **Branch Name Search**: Search post office details by branch name.
- ⭐ **Saved PINs**: Save and quickly access your frequently searched PIN codes.
- 🔐 **Google & Firebase Auth**: Google Sign-In and Firebase Authentication integration for user management.
- 📱 **Modern UI**: Clean Tabbed Navigation powered by `TabLayout` and `ViewPager2`.

---

## 🛠️ Tech Stack & Architecture

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: MVVM (Model-View-ViewModel) pattern
- **UI Components**:
  - `AppCompat`, `ConstraintLayout`, `Material Components`
  - `ViewPager2` with `TabLayoutMediator`
  - `ViewBinding`
- **Authentication**:
  - Firebase Auth (`firebase-auth`)
  - Google Sign-In (`play-services-auth`)
- **Android Jetpack**: ViewModel, LiveData, Activity KTX, Core KTX

---

## ⚙️ Requirements & Configuration

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36 / 37
- **Gradle Version**: 8.x+
- **JDK Version**: Java 11+

---

## 🚀 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/mantridinesh/postofficeapp.git
   cd postofficeapp
   ```

2. **Open in Android Studio**:
   - Open Android Studio.
   - Select **Open an Existing Project** and navigate to the project directory.

3. **Build & Run**:
   - Let Gradle sync complete.
   - Connect an emulator or Android device (API 24+).
   - Press **Run** (`Shift + F10`).

---

## 📝 License

This project is open-source and available under the [MIT License](LICENSE).
