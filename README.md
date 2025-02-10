# Money Transfer App

https://github.com/user-attachments/assets/4c0ff739-dfda-433b-9c7e-1cd1418ecbf2

## Overview

Money Transfer App is an Android application developed using Kotlin and MVVM architecture. The app simulates a money transfer system between different user accounts, allowing users to authenticate, manage accounts, transfer funds, and view transaction history without backend integration.

## Features

### 1. User Authentication

- Implements Firebase Authentication for email/password login and Google sign-in.
- Secure authentication handling with validation.
- Login screen dynamically updates based on authentication state:
  - If the user clicks "Login with Email," email and password fields appear.
  - Clicking "Sign In" attempts to log in with the provided credentials.
  - Clicking "Sign Up" creates a new account and proceeds.
  - If the user is already logged in with Google or email/password, the login screen is skipped.

### 2. Account Management:

- Simulates a list of user accounts with mock data.
- Displays accounts in a RecyclerView.
- Each account contains details such as balance and account number.

### 3. Transfer Interface:

- Provides a form for users to enter transfer details:
  - Source account
  - Destination account
  - Transfer amount
- Validates that the transfer amount does not exceed the source account balance.
- Shows a summary before confirming the transaction.
- Updates account balances upon successful transfer.

### 4. Transaction History:

- Uses the Room database to store local transaction history.
- Displays past transactions in a RecyclerView.

### 5. UI/UX:

- Clean and user-friendly interface with smooth navigation.
- Provides error and success messages for user feedback.

## Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM
- **Dependency Injection:** Koin
- **Database:** Room (for transaction history)
- **UI Components:** Jetpack Compose
- **Navigation:** Compose Navigation
- **Authentication:** Firebase Authentication (Email/Password, Google Sign-In)
- **Live Data Handling:** LiveData + ViewModel

## Prerequisites

- Android Studio (latest version recommended)
- Gradle
- Android device or emulator running API level 21 or higher
- Set up Firebase and add the `google-services.json` file inside the `app` folder.

## Getting Started

### Clone the Repository:

```bash
git clone https://github.com/yourusername/MoneyTransferApp.git
```

### Open in Android Studio:

1. Click on **File > Open** and select the project folder.
2. Sync Gradle dependencies and wait for the project to build.

### Run the App:

1. Connect a device or start an emulator.
2. Click the **Run** button in Android Studio to install and run the app.

## Known Issues & Future Improvements

- **UI Enhancements:** The UI/UX can be further optimized for a better experience.
- **Real Banking API Integration:** Currently, the app uses mock data, but it can be integrated with real banking APIs in the future.
- **Advanced Transaction Features:** Features like scheduled transfers, recurring payments, and transaction categorization can be added.

## Testing

- Unit tests are provided for key functionalities such as authentication, account balance validation, and transaction history updates.

## License

This project is licensed under the MIT License.

