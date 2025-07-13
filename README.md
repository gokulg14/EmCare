# EmCare - Emergency and Healthcare Monitoring App

<div align="center">
  <img src="mobile/src/main/res/drawable/emcare_logo.jpg" alt="EmCare Logo" width="200"/>
  
  [![Android](https://img.shields.io/badge/Android-API%2028+-green.svg)](https://developer.android.com/)
  [![Wear OS](https://img.shields.io/badge/Wear%20OS-API%2028+-blue.svg)](https://developer.android.com/wear)
  [![Google Play Services](https://img.shields.io/badge/Google%20Play%20Services-17.1.0+-orange.svg)](https://developers.google.com/android/guides/overview)
</div>


## 📱 Overview

EmCare is a comprehensive healthcare monitoring system that integrates Wear OS smartwatch functionality with a mobile application to provide real-time health monitoring and emergency alert capabilities. The system uses Google's Wearable Data Layer API to facilitate seamless communication between wearable devices and mobile phones.

## 🏗️ Architecture

The project consists of two main modules:

### 1. **Wear Module** (`wear/`)
- **Platform**: Wear OS (Android Wear)
- **Purpose**: Smartwatch application that captures vital health data
- **Key Features**:
  - Real-time sensor data collection (Heart Rate, Blood Pressure, Motion)
  - Data transmission to mobile app via Wearable Data Layer API
  - Standalone operation capability

### 2. **Mobile Module** (`mobile/`)
- **Platform**: Android Mobile
- **Purpose**: Main healthcare application for patients and caretakers
- **Key Features**:
  - Patient registration and authentication
  - Real-time health data monitoring
  - Emergency alert system with SMS notifications
  - Caretaker management
  - Health data history and analytics

## 🚀 Key Features

### Health Monitoring
- **Real-time Vital Signs Tracking**:
  - Heart Rate monitoring
  - Blood Pressure (pressure sensor simulation)
  - Motion/Accelerometer data
  - Timestamp tracking for all measurements

### Emergency Alert System
- **Automated Emergency Detection**:
  - Abnormal heart rate detection (< 40 or > 100 BPM)
  - Blood pressure threshold monitoring (< 100 or > 500)
  - Motion sensor anomaly detection
  - Automatic SMS alerts to emergency contacts

### User Management
- **Patient Features**:
  - User registration and login
  - Profile management
  - Health data visualization
  - Real-time health status updates
  - Caretaker assignment

- **Caretaker Features**:
  - Patient health data monitoring
  - Emergency notification system
  - Health history tracking
  - Automated SMS alerts

### Communication System
- **Wearable Data Layer Integration**:
  - Real-time data synchronization between wear and mobile
  - Message-based communication protocol
  - Automatic reconnection handling

- **SMS Alert System**:
  - Emergency contact notifications
  - Caretaker alerts
  - SMS rate limiting (max 4 SMS per day per user)
  - Automated credential sharing for new caretakers

## 🛠️ Technical Stack

### Dependencies
```gradle
// Wear Module
implementation 'com.google.android.gms:play-services-wearable:17.1.0'
implementation 'androidx.wear:wear:1.2.0'
implementation 'androidx.percentlayout:percentlayout:1.0.0'

// Mobile Module
implementation 'com.google.android.gms:play-services-wearable:17.1.0'
implementation 'com.fasterxml.jackson.core:jackson-databind:2.11.1'
implementation 'de.hdodenhof:circleimageview:3.1.0'
implementation 'com.google.android.material:material:1.6.0'
implementation 'org.apache.commons:commons-lang3:3.0'
```

### Database Architecture
- **SQLite Databases**:
  - `Login.db` - User authentication and session management
  - `PatientData.db` - Patient and caretaker information
  - `HealthData.db` - Health monitoring data storage
  - `EmNum.db` - Emergency contact numbers
  - `SMSLimit.db` - SMS usage tracking and rate limiting

### Core Components

#### Wear Module Components
- **MainActivity**: Sensor data collection and transmission
- **Sensor Integration**: Heart rate, pressure, and accelerometer sensors
- **Data Transmission**: Wearable Data Layer API implementation

#### Mobile Module Components
- **HealthDataProcessor**: Processes incoming health data and triggers alerts
- **HealthListener**: WearableListenerService for receiving wear data
- **SmsHelperClass**: SMS functionality and rate limiting
- **Database Classes**: SQLite database operations
- **UI Components**: Patient and caretaker interfaces

## 📋 Prerequisites

### Development Environment
- Android Studio Arctic Fox or later
- Android SDK 28+ (API Level 28)
- Google Play Services
- Wear OS Emulator or physical Wear OS device

### Required Permissions
```xml
<!-- Wear Module -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.BODY_SENSORS" />
<uses-feature android:name="android.hardware.type.watch" />
<uses-feature android:name="android.hardware.sensor.heartrate" />

<!-- Mobile Module -->
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.READ_SMS" />
```

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd wear
```

### 2. Open in Android Studio
- Open Android Studio
- Select "Open an existing Android Studio project"
- Navigate to the `wear` folder and select it

### 3. Configure Emulators
- **Mobile Emulator**: Create an Android phone emulator (API 28+)
- **Wear Emulator**: Create a Wear OS emulator with heart rate sensor support

### 4. Build and Run
```bash
# Build the project
./gradlew build

# Run mobile module
./gradlew :mobile:installDebug

# Run wear module
./gradlew :wear:installDebug
```

## 🔧 Configuration

### Emergency Contacts Setup
1. Launch the mobile application
2. Navigate to emergency contacts section
3. Add up to 3 emergency contact numbers
4. These numbers will receive SMS alerts during emergencies

### Caretaker Assignment
1. Register as a patient
2. Add caretaker information during registration
3. System automatically sends SMS with login credentials to caretaker
4. Caretaker can monitor patient health data through dedicated interface

## 📊 Health Monitoring Parameters

### Threshold Values
- **Heart Rate**: Normal range 40-100 BPM
- **Blood Pressure**: Normal range 100-500 (simulated values)
- **Motion Sensor**: Threshold ±0.1 (accelerometer readings)

### Alert Triggers
- Heart rate outside normal range
- Blood pressure exceeding thresholds
- Abnormal motion patterns
- System automatically sends SMS alerts to configured contacts

## 🔄 Data Flow

### Wear to Mobile Communication
1. **Sensor Data Collection**: Wear app continuously monitors sensors
2. **Data Processing**: Raw sensor data is processed and formatted
3. **Message Transmission**: Data sent via Wearable Data Layer API
4. **Mobile Reception**: HealthListener service receives data
5. **Data Processing**: HealthDataProcessor analyzes and stores data
6. **Alert Generation**: Automatic alerts triggered if thresholds exceeded

### Emergency Alert Flow
1. **Threshold Detection**: System detects abnormal health parameters
2. **User Verification**: Checks if user is logged in
3. **Contact Selection**: Determines appropriate emergency contacts
4. **SMS Transmission**: Sends emergency alerts via SMS
5. **Rate Limiting**: Ensures SMS limits are not exceeded

## 🧪 Testing

### Wear OS Emulator Testing
- Use Android Studio's Wear OS emulator
- Enable heart rate sensor simulation
- Test data transmission between wear and mobile modules
- Verify emergency alert functionality

### SMS Testing
- Configure test phone numbers for emergency contacts
- Test SMS rate limiting functionality
- Verify caretaker notification system

## 📱 Screenshots



## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## 👥 Authors

- **Gokul** - *Initial work* - [gokul14](https://github.com/gokulg14)

## 🙏 Acknowledgments

- Google Wearable Data Layer API documentation
- Android Wear OS development guidelines
- Android sensor framework documentation

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact: gokulgnair777@gmail.com

## 🔮 Future Enhancements

- [ ] Integration with real medical devices
- [ ] Cloud-based data storage
- [ ] Machine learning for predictive health analytics
- [ ] Integration with healthcare provider systems
- [ ] Multi-language support
- [ ] Advanced notification system
- [ ] Health data export functionality
- [ ] Integration with fitness tracking apps

---

<div align="center">
  <p>Made with ❤️ for healthy living</p>
  <p>⭐ Star this repository if you found it helpful!</p>
</div>
