# EmCare - Emergency and Healthcare Monitoring App

<div align="center">
  <img src="wear/mobile/src/main/res/drawable/emcare_logo.jpg" alt="EmCare Logo" width="200"/>
  
  [![Android](https://img.shields.io/badge/Android-API%2028+-green.svg)](https://developer.android.com/)
  [![Wear OS](https://img.shields.io/badge/Wear%20OS-API%2028+-blue.svg)](https://developer.android.com/wear)
  [![Google Play Services](https://img.shields.io/badge/Google%20Play%20Services-17.1.0+-orange.svg)](https://developers.google.com/android/guides/overview)
</div>

## 📱 Overview

EmCare is a comprehensive healthcare monitoring application that provides real-time health monitoring, emergency alerts, and caregiver coordination. The app consists of two modules:

- **Mobile Module**: Main Android application for patients and caregivers
- **Wear Module**: Wear OS application for smartwatch integration

The system simulates smartwatch integration using the Wear OS emulator, capturing vital data like heart rate, blood pressure, and motion sensors, then transferring it to the mobile app using Google's Wearable Data Layer API for real-time syncing and emergency alerts.

## 🚀 Key Features

### 🏥 Health Monitoring
- **Real-time Vital Signs Tracking**: Heart rate, blood pressure, and motion sensor data
- **Smartwatch Integration**: Seamless data collection from Wear OS devices
- **Health Data History**: Comprehensive logging and analysis of health metrics
- **Automated Health Alerts**: Intelligent threshold-based emergency notifications

### 🚨 Emergency Response System
- **Instant SMS Alerts**: Automatic emergency notifications to multiple contacts
- **Caregiver Coordination**: Direct communication with assigned caregivers
- **Emergency Contact Management**: Configurable emergency contact system
- **Location Sharing**: GPS-based location tracking for emergency situations

### 👥 User Management
- **Dual User Types**: Separate interfaces for patients and caregivers
- **Secure Authentication**: User registration and login system
- **Profile Management**: Comprehensive user profile and health data management
- **Caregiver Assignment**: Patient-caregiver relationship management

### 📊 Data Management
- **Local SQLite Database**: Secure local storage of health and user data
- **Data Synchronization**: Real-time data transfer between wear and mobile modules
- **Health Analytics**: Historical data analysis and trends
- **SMS Rate Limiting**: Intelligent SMS sending to prevent spam

## 🏗️ Architecture

### Module Structure
```
EmCare/
├── mobile/                 # Main Android application
│   ├── src/main/java/
│   │   ├── common/         # Shared components
│   │   ├── patient/        # Patient-specific features
│   │   ├── caretaker/      # Caregiver features
│   │   ├── smartwatch/     # Wear integration
│   │   ├── databases/      # Local database management
│   │   └── helperclasses/  # Utility classes
│   └── src/main/res/       # UI resources
└── wear/                   # Wear OS application
    └── src/main/java/
        └── com/myproj/wear/ # Wear-specific logic
```

### Database Schema

#### Health Data Table
- **Name**: Patient identifier
- **Heart_Rate**: Heart rate readings
- **Pressure_Data**: Blood pressure data
- **Motion_Sensor**: Motion sensor readings
- **Time**: Timestamp of readings

#### Patient Table
- **NAME**: Primary key, patient name
- **EMAIL**: Patient email address
- **NUMBER**: Patient phone number
- **CTNAME**: Caregiver name
- **CTNUM**: Caregiver phone number
- **GENDER**: Patient gender
- **DOB**: Date of birth

#### Login Table
- **NAME**: Username (Primary key)
- **PASSWORD**: Encrypted password
- **ACTIVE**: Active session status

#### Emergency Numbers Table
- **Id**: Auto-increment primary key
- **Name**: Contact name
- **Number**: Emergency contact number

#### SMS Limit Table
- **COUNT**: SMS count per day
- **DATE**: Date of SMS sending
- **USERNAME**: User identifier

## 🔧 Technical Implementation

### Smartwatch Integration
- **Wearable Data Layer API**: Real-time communication between wear and mobile
- **Sensor Data Collection**: Heart rate, pressure, and motion sensors
- **Message-based Communication**: JSON data transfer via MessageClient
- **Background Processing**: Continuous health monitoring service

### Health Data Processing
```java
// Real-time health data analysis
if(averagePressure < 100 || averagePressure > 500 ||
   averageGravity > 0.1 || averageGravity < -0.1 ||
   averageHeartdata > 100 || averageHeartdata < 40) {
    // Trigger emergency alert
}
```

### Emergency Alert System
- **Multi-level Alerting**: Emergency contacts and caregiver notifications
- **SMS Rate Limiting**: Maximum 4 SMS per day per user
- **Intelligent Routing**: Different alert strategies based on user status
- **Location Integration**: GPS coordinates for emergency response

### Security Features
- **Local Data Storage**: Sensitive data stored locally
- **Permission Management**: Granular Android permissions
- **SMS Validation**: Rate limiting and validation
- **User Session Management**: Secure login/logout system

## 📱 Screenshots & UI

### Main Features
- **Onboarding**: Interactive tutorial with app features
- **Dashboard**: Real-time health metrics display
- **Emergency Contacts**: Manage emergency contact list
- **Health History**: Historical data visualization
- **User Profile**: Comprehensive profile management
- **Caregiver Interface**: Dedicated caregiver monitoring view

## 🛠️ Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 28+
- Wear OS Emulator or physical Wear OS device
- Google Play Services 17.1.0+

### Build Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/emcare.git
   cd emcare
   ```

2. **Open in Android Studio**
   - Open the project in Android Studio
   - Sync Gradle files
   - Install required dependencies

3. **Configure Wear OS Emulator**
   - Set up Wear OS emulator in Android Studio
   - Enable sensor simulation for testing

4. **Build and Run**
   ```bash
   # Build mobile module
   ./gradlew :mobile:assembleDebug
   
   # Build wear module
   ./gradlew :wear:assembleDebug
   ```

### Configuration

1. **Emergency Contacts**: Add emergency contact numbers in the app
2. **Caregiver Assignment**: Assign caregivers to patients
3. **Health Thresholds**: Configure health alert thresholds
4. **SMS Permissions**: Grant SMS permissions for emergency alerts

## 🔌 Dependencies

### Mobile Module
```gradle
implementation 'androidx.appcompat:appcompat:1.4.1'
implementation 'com.google.android.material:material:1.6.0'
implementation 'com.google.android.gms:play-services-wearable:17.1.0'
implementation 'com.fasterxml.jackson.core:jackson-databind:2.11.1'
implementation 'org.apache.commons:commons-lang3:3.0'
```

### Wear Module
```gradle
implementation 'com.google.android.gms:play-services-wearable:17.1.0'
implementation 'androidx.wear:wear:1.2.0'
implementation 'androidx.percentlayout:percentlayout:1.0.0'
```

## 🧪 Testing

### Unit Tests
- Database operations testing
- Health data processing validation
- SMS functionality testing

### Integration Tests
- Wear-mobile communication testing
- Emergency alert system testing
- User authentication flow testing

### Manual Testing
- Wear OS emulator sensor simulation
- Emergency contact management
- Health data visualization

## 📊 Performance & Optimization

- **Efficient Data Processing**: Stream-based health data analysis
- **Background Services**: Optimized for battery life
- **Local Storage**: Fast database operations
- **Memory Management**: Efficient resource utilization

## 🔒 Security & Privacy

- **Local Data Storage**: No cloud dependency for sensitive data
- **Permission-based Access**: Minimal required permissions
- **SMS Rate Limiting**: Prevents abuse of emergency system
- **User Session Management**: Secure authentication

## 🚀 Future Enhancements

- **Cloud Integration**: Remote health data backup
- **AI Health Analysis**: Machine learning-based health insights
- **Telemedicine Integration**: Video consultation features
- **IoT Device Support**: Additional health device integration
- **Multi-language Support**: Internationalization
- **Offline Mode**: Enhanced offline functionality

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/yourprofile)

## 🙏 Acknowledgments

- Google Wearable Data Layer API for smartwatch integration
- Android Wear OS for wearable platform
- SQLite for local database management
- Apache Commons for utility functions

## 📞 Support

For support and questions:
- Create an issue in the GitHub repository
- Email: your.email@example.com
- Documentation: [Wiki Link]

---

<div align="center">
  <p><strong>EmCare - We Care About You</strong></p>
  <p>Emergency healthcare monitoring made simple and reliable</p>
</div>
