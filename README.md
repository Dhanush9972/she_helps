# 🛡️ She Protect - Women's Safety & SOS App

**She Protect** is a native Android application designed to provide immediate assistance and evidence collection for women in distress. Built with **Kotlin** and **Modern Android Architecture**, it serves as a personal safety companion that triggers SOS alerts, records evidence, and simulates fake calls to deter potential threats.

---

## 🌟 Key Features

### 🚨 SOS & Dual-Trigger System
* **Shake to Trigger:** Instantly activates the SOS mode when the phone is shaken vigorously (Accelerometer detection), even if the app is minimized.
* **Shield Mode:** A Foreground Service ensures the app stays active and vigilant 24/7 without being killed by the system.

### 📹 Stealth Evidence Collection
* **Hidden Recording:** Automatically captures **video and audio** using the front camera immediately after an SOS trigger.
* **Silent Operation:** Records in the background while the screen displays the emergency interface.

### 📍 Live Location Sharing
* **Real-Time GPS:** Fetches high-accuracy location coordinates.
* **Smart Fallback:** If GPS is unavailable, it retrieves the "Last Known Location" to ensure an alert is always sent.
* **SMS Alert:** Sends an emergency SMS

* ### 📞 Fake Call Simulation
* **Deterrence Tool:** Simulates a realistic incoming call (ringtone + UI) to help users excuse themselves from uncomfortable or unsafe situations.
* **Timer:** Schedules the call to ring after a 5-second delay.

### 📢 Panic Siren
* **Loud Alarm:** Plays a high-volume alarm siren during the SOS phase to attract attention and scare off attackers.

---

## 🛠️ Tech Stack

* **Language:** Kotlin
* **UI:** XML with Material Design 3 Components
* **Architecture:** MVVM (Model-View-ViewModel)
* **Camera:** CameraX API (Video & Audio Capture)
* **Location:** Google Play Services (FusedLocationProviderClient)
* **Background Tasks:** Android Foreground Services & Broadcast Receivers
* **Storage:** SharedPreferences (Local Data Persistence)

---
## ⚙️ Installation & Setup

### Prerequisites
* Android Studio Iguana (or newer)
* Android Device (Minimum SDK: API 26 / Android 8.0)
* *Note: Emulators cannot effectively test the Shake Sensor or Camera features.*

### Steps to Run
1.  **Clone the Repository:**
    ```bash
    git clone(https://github.com/Dhanush9972/SheProtect.git)
    ```
2.  **Open in Android Studio:**
    * Launch Android Studio -> `File` -> `Open` -> Select the cloned folder.
3.  **Sync Gradle:**
    * Wait for the project to download dependencies.
4.  **Connect Device:**
    * Enable **USB Debugging** on your physical Android phone.
    * Connect via USB.
5.  **Run:**
    * Click the green **Play** button (Run 'app').

---

## 🔒 Permissions

The app requires the following runtime permissions to function:
* `ACCESS_FINE_LOCATION`: To share precise location.
* `SEND_SMS`: To send the SOS alert.
* `CAMERA`: To record evidence.
* `RECORD_AUDIO`: To capture sound during evidence recording.
* `FOREGROUND_SERVICE`: To keep the shake detection active.

---

## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

---

## 📄 License

This project is open-source and available under the **MIT License**.
