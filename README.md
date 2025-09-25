# 🎓 Engineering & Computer Science Portfolio

Welcome to my GitHub repository containing various projects from my Electronics and ICT studies at UGent. This collection showcases practical implementations across multiple domains including embedded systems, web development, data processing, and autonomous systems.

## 📋 Table of Contents

- [🖥️ Server Application Frameworks](#️-server-application-frameworks)
- [🌱 Vertical Farming System (Engineering Project 1)](#-vertical-farming-system-engineering-project-1)
- [📊 Signal Processing & Fourier Analysis (PO1)](#-signal-processing--fourier-analysis-po1)
- [🚗 Autonomous Vehicle System (PO2)](#-autonomous-vehicle-system-po2)
- [⚡ Embedded Systems - 8051 Assembly Programming](#-embedded-systems---8051-assembly-programming)
- [📈 Distributed Data Processing](#-distributed-data-processing)
- [📊 Statistical Analysis & Mathematical Modeling](#-statistical-analysis--mathematical-modeling)
- [⚡ Electronics Laboratory Work](#-electronics-laboratory-work)
- [💻 Core Technologies](#-core-technologies)

---

## 🖥️ Server Application Frameworks

**Technologies:** TypeScript, Node.js, Express, TypeORM, WebSockets, Linux

### Key Learning Outcomes
- **Data Layer with ORM**: Object-relational mapping using TypeORM
- **Database Relationships**: Implementation of 1-1, 1-n, n-n relationships with cascade operations
- **Inheritance Patterns**: Applied inheritance concepts in data layer architecture
- **DAO Layer**: CRUD operations through Data Access Object pattern
- **Data Retrieval**: Understanding lazy vs eager loading strategies
- **REST API Design**: API-first approach with comprehensive endpoint implementation
- **Real-time Communication**: WebSocket integration for live functionality
- **Deployment**: Linux server deployment using SSH, SCP, and Git

### Project Structure
```
├── src/
│   ├── entities/          # TypeORM entities
│   ├── dao/              # Data Access Objects
│   ├── routes/           # Express REST endpoints
│   ├── websockets/       # WebSocket handlers
│   └── services/         # Business logic
└── deploy/               # Deployment scripts
```

---

## 🌱 Vertical Farming System (Engineering Project 1)

**Technologies:** Arduino IDE (C/C++), Arduino Mega 2560, Various sensors and actuators

### System Overview
An automated vertical farming system integrating electronics, mechanical construction, and software control for optimal plant growth conditions.

### Technical Components
- **Microcontroller**: Arduino Mega 2560
- **Sensors**: DHT11 (temperature/humidity), RTC module
- **Actuators**: Submersible pump, LED grow lights, MOSFET modules
- **Data Storage**: SD card module for environmental logging
- **Control Method**: Pulse Width Modulation (PWM)

### Key Features
- Automated water regulation system
- Time-controlled lighting (RTC-based scheduling)
- Environmental monitoring and data logging
- Multi-container mechanical integration
- Real-time sensor data collection

### Learning Outcomes
- Electronics circuit design and testing
- Mechanical construction with integrated systems
- Embedded programming and sensor interfacing
- System integration and troubleshooting
- Sustainability impact analysis (SDG alignment)
- Team collaboration using Belbin roles methodology

---

## 📊 Signal Processing & Fourier Analysis (PO1)

**Technologies:** Python, NumPy, SciPy, Matplotlib

### Theoretical Foundations
- **Fourier Series**: Approximation of periodic functions using trigonometric series
- **Fourier Transform**: Extension to non-periodic functions
- **Orthogonality**: Linear combinations and function basis concepts
- **Amplitude-Phase Representation**: Signal analysis in frequency domain

### Practical Implementations
- Custom Fourier series and complex Fourier implementations
- Fast Fourier Transform (FFT) algorithms
- Audio file processing (.wav format)
- Data compression using Fourier coefficients
- Signal reconstruction and analysis

### Technical Skills Developed
- Mathematical algorithm implementation
- Audio signal processing
- Data visualization and analysis
- Version control challenges and solutions
- Scientific computing with Python

---

## 🚗 Autonomous Vehicle System (PO2)

**Technologies:** LabVIEW, NI MyRIO, Various sensors

### System Requirements
Design and implementation of an autonomous vehicle capable of:
- Line following navigation
- Traffic light recognition and response
- Obstacle detection and avoidance
- Stop line recognition and compliance

### Hardware Architecture
- **Microcontroller**: NI MyRIO (chosen over Raspberry Pi for real-time requirements)
- **Sensors**: Reflectance sensors, ultrasonic distance sensors, color sensors
- **Actuators**: DC motors, servo motors
- **Indicators**: LED status indicators
- **Chassis**: Custom 3D-modeled vehicle platform

### Software Implementation
- LabVIEW programming for sensor integration
- Calibration algorithms for sensor accuracy
- Logic flow implementation for autonomous navigation
- Real-time decision-making algorithms

### Project Management Skills
- Requirements analysis and technical specification translation
- Component selection and budget management
- Hardware-software integration
- Technical documentation and reporting

---

## ⚡ Embedded Systems - 8051 Assembly Programming

**Technologies:** 8051 Assembly, Hardware simulators

### Core Competencies

#### I/O Management
- Digital input/output control
- Button polling and interrupt handling
- LED control and pattern generation
- 7-segment display control

#### Timing Systems
- Timer0/Timer1 configuration (16-bit)
- Precise timing calculations
- Clock frequency management
- Delay generation algorithms

#### Memory Management
- Data memory organization
- Lookup table implementation
- Array indexing and manipulation
- BCD representation and conversion

#### Advanced Programming Concepts
- Subroutine design and implementation
- Stack management and CPU context preservation
- Parameter passing mechanisms
- C-to-Assembly code translation
- Recursive function implementation

### Example Projects
- LED sequencing and control systems
- 7-segment display multiplexing
- Button-controlled state machines
- Timer-based real-time applications

---

## 📈 Distributed Data Processing

**Technologies:** Apache Spark, Apache Kafka, QuestDB, Python (Pandas, NumPy), Matplotlib, Seaborn

### Lab 1: Data Analysis & Visualization
- Large dataset analysis using Pandas/NumPy
- Statistical computations and data grouping
- Time series analysis for trend identification
- Advanced data visualization techniques
- Filtering, aggregation, and top-N analyses

### Lab 2: Batch Processing with PySpark
- Large-scale data processing using Spark DataFrames
- Complex calculations (Air Quality Index via breakpoints)
- Correlation analysis between multiple variables
- Window functions for streak detection
- Data cleaning and outlier detection algorithms

### Lab 3: Stream Processing
- Real-time data processing with Kafka + Spark Streaming
- ETL pipeline development for continuous data streams
- Windowed operations and real-time aggregations
- JSON parsing and dynamic schema management
- Watermarking strategies for handling late-arriving data

### Lab 4: Time-Series Database Integration
- QuestDB implementation for time-series data storage
- Geospatial data processing with geohash encoding
- Kafka Connect for automated data ingestion
- Materialized views for query optimization
- Time-based data partitioning strategies

### Architecture Patterns Implemented
- **Lambda Architecture**: Batch and stream processing integration
- **ETL/ELT Pipelines**: Extract, Transform, Load operations
- **Real-time Analytics**: Low-latency data processing
- **Data Lake**: Centralized data storage and processing
- **Distributed Computing**: Scalable processing architectures

---

## 📊 Statistical Analysis & Mathematical Modeling

**Technologies:** MATLAB, Python, Statistical Computing

### Mathematical Foundations
- **Statistical Data Analysis**: Comprehensive statistical modeling and hypothesis testing
- **Mathematical Modeling**: Development of mathematical models for engineering applications
- **Data Visualization**: Advanced plotting and visualization techniques
- **Numerical Methods**: Implementation of numerical algorithms for engineering problems

### MATLAB Competencies
- Statistical toolbox utilization for complex data analysis
- Custom algorithm development for mathematical modeling
- Data import/export and preprocessing workflows
- Advanced plotting and visualization techniques
- Integration with other engineering analysis tools

### Applications
- Engineering data analysis and interpretation
- Statistical validation of experimental results
- Mathematical model verification and validation
- Trend analysis and predictive modeling

---

## ⚡ Electronics Laboratory Work

**Focus Areas:** Electronics 1 & 2, Practical Circuit Implementation

### Laboratory Skills Developed
- **Circuit Analysis**: Hands-on experience with analog and digital circuits
- **Component Testing**: Practical testing and characterization of electronic components
- **Measurement Techniques**: Use of oscilloscopes, multimeters, function generators
- **PCB Design**: Understanding of printed circuit board layout principles
- **Signal Analysis**: Time and frequency domain analysis of electronic signals

### Practical Applications
- Building and testing various electronic circuits
- Troubleshooting and debugging electronic systems
- Integration of theoretical knowledge with practical implementation
- Component selection and circuit optimization
- Laboratory report writing and technical documentation

### Integration with Projects
These laboratory skills directly supported other projects:
- Circuit design for the Vertical Farming system
- Hardware interfacing in the Autonomous Vehicle project
- Sensor integration across multiple embedded systems projects

---

## 💻 Core Technologies

### Programming Languages
- **TypeScript/JavaScript**: Full-stack web development, REST APIs, real-time applications
- **Python**: Data analysis, signal processing, scientific computing, machine learning workflows
- **C/C++**: Embedded systems programming, Arduino development, system-level programming
- **Assembly (8051)**: Low-level hardware programming, microcontroller optimization
- **LabVIEW**: Graphical programming for hardware integration and automation
- **MATLAB**: Statistical analysis, mathematical modeling, engineering computations
- **SQL**: Relational database management, complex queries, database optimization

### Database Technologies
- **Relational Databases**: PostgreSQL, SQL Server, MySQL
- **Time-Series Databases**: QuestDB for high-performance time-series data
- **ORM Technologies**: TypeORM for object-relational mapping
- **Database Design**: Normalization, indexing, query optimization

### Frameworks & Tools
- **Backend**: Node.js, Express, TypeORM
- **Data Processing**: Apache Spark, Apache Kafka, Pandas, NumPy
- **Databases**: PostgreSQL, QuestDB, SQL Server
- **Hardware**: Arduino, NI MyRIO, 8051 microcontroller
- **Version Control**: Git, GitHub
- **Deployment**: Linux servers, SSH, Docker

### Specialized Skills
- **Embedded Systems**: Real-time programming, sensor integration, microcontroller optimization
- **Signal Processing**: Fourier analysis, digital signal processing, audio processing
- **Data Engineering**: Big data processing, stream analytics, ETL pipeline development
- **Web Development**: REST APIs, WebSocket communication, full-stack applications
- **Hardware Integration**: Circuit design, sensor calibration, system integration
- **Statistical Analysis**: Mathematical modeling, hypothesis testing, data interpretation
- **Electronics Design**: Circuit analysis, component selection, PCB layout principles

---

## 🎯 Project Highlights

Each project in this repository demonstrates:
- **Problem-solving skills**: Breaking down complex engineering challenges into manageable components
- **Technical implementation**: Practical application of theoretical concepts across multiple domains
- **System integration**: Combining hardware, software, networking, and database components
- **Mathematical modeling**: Application of statistical analysis and mathematical foundations
- **Laboratory skills**: Hands-on experience with electronic circuits and measurement equipment
- **Documentation**: Comprehensive technical documentation and professional reporting
- **Team collaboration**: Working effectively in multidisciplinary engineering teams
- **Continuous learning**: Adapting to new technologies, frameworks, and methodologies
- **Quality assurance**: Testing, validation, and optimization of engineering solutions

---

