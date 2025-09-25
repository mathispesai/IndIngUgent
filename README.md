# 🎓 Engineering & Computer Science Portfolio

Welcome to my GitHub repository containing various projects from my Electronics and ICT studies at UGent. This collection showcases practical implementations across multiple domains including embedded systems, web development, data processing, and autonomous systems.

## 📋 Table of Contents

- [🖥️ Server Application Frameworks](#️-server-application-frameworks)
- [🌱 Vertical Farming System (Engineering Project 1)](#-vertical-farming-system-engineering-project-1)
- [📊 Signal Processing & Fourier Analysis (PO1)](#-signal-processing--fourier-analysis-po1)
- [🚗 Autonomous Vehicle System (PO2)](#-autonomous-vehicle-system-po2)
- [⚡ Embedded Systems - 8051 Assembly Programming](#-embedded-systems---8051-assembly-programming)
- [📈 Distributed Data Processing](#-distributed-data-processing)
- [🌐 Software Development Projects](#-software-development-projects)
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

### 1. Problem Analysis & Client Requirements
- **Requirements Translation**: Converting client needs into technical specifications (line following, stop line detection, traffic light interpretation, collision avoidance)
- **System Analysis**: Understanding autonomous system functionality in a model city environment
- **Specification Development**: Defining clear technical requirements and system boundaries

### 2. Hardware Design & Component Selection
- **Component Selection**: Systematic selection and justification of chassis, wheels, motors, microcontroller (NI MyRIO), sensors (reflectance, distance, color sensors), and LEDs
- **Alternative Analysis**: Comparative evaluation of options (Raspberry Pi vs. NI MyRIO, camera vs. color sensor)
- **Physical Assembly**: Component mounting and integration using 3D models for preparation
- **System Architecture**: Designing robust hardware architecture for autonomous operation

### 3. Software Design & Implementation
- **LabVIEW Programming**: Developing communication protocols between sensors, microcontroller, and motors
- **Sensor Calibration**: Experimental validation and calibration of sensor outputs (black/white/gray differentiation, red/green light detection, distance measurements)
- **Logic Programming**: Creating flowchart-based programs for vehicle behavior control (driving, decelerating, stopping at obstacles or red lights)
- **Real-time Control**: Implementing responsive control algorithms for autonomous navigation

### 4. Hardware-Software Integration
- **System Integration**: Connecting sensors and motors through microcontroller interface
- **Testing & Refinement**: Iterative testing and optimization to achieve autonomous driving requirements
- **Performance Validation**: Ensuring system meets all specified requirements and safety standards

### 5. Engineering Project Skills
- **Team Collaboration**: Working effectively in groups on realistic engineering challenges
- **Budget Management**: Financial oversight and cost optimization within component procurement constraints
- **Technical Documentation**: Comprehensive documentation of design choices, experiments, and results
- **Project Planning**: Timeline management and milestone tracking for complex engineering deliverables

### Core Competencies Developed
**Technical Skills:**
- Advanced sensor integration and calibration
- LabVIEW graphical programming proficiency
- Real-time system design and implementation
- Hardware-software interface development
- Autonomous system behavior programming

**Engineering Skills:**
- Problem-solving methodology and systematic approach
- Design decision justification and documentation
- Collaborative engineering project execution
- Budget-conscious component selection and procurement
- Technical report writing and presentation skills

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

## 🌐 Software Development Projects

### Sporters Connect (Team 2, 2023-2024)

**Technologies:** Firebase, JavaScript, HTML/CSS, Google Maps API

#### Project Overview
A social platform connecting sports enthusiasts through intelligent matching algorithms and location-based services for finding training partners and sports communities.

#### Technical Implementation

**Backend Architecture**
- Complete Firebase integration for serverless backend services
- Firestore NoSQL database design and implementation
- Firebase Authentication for secure user registration and management
- Firebase Storage for media file handling and user content

**Frontend Development**
- Responsive web design with modern HTML, CSS, and JavaScript
- Interactive real-time user interfaces with dynamic content updates
- Google Maps API integration for location-based matching services
- Cross-platform compatibility ensuring consistent user experience

**Algorithm Development**
- Complex matching algorithms for connecting compatible sports partners
- Graph structures implementation for user relationship mapping
- Weighted scoring systems for accurate profile matching
- Performance optimization for real-time matching capabilities

**Data Management**
- JSON data processing and manipulation
- Browser session storage implementation for user state management
- Data persistence strategies across sessions
- User data privacy compliance and security protocols

#### Project Management & Soft Skills
- Effective task distribution among 4 team members
- Code review processes and collaborative development
- Requirements engineering through user story mapping
- Quality assurance through systematic testing approaches

---

### SolarSim (Team 2, 2025)

**Technologies:** Advanced JavaScript (ES6+), HTML5 Canvas, Jest, Node.js

#### Project Overview
An interactive solar system simulation implementing advanced physics algorithms and educational visualization for astronomical learning and scientific computation.

#### Advanced Technical Implementation

**Scientific Programming & Algorithms**
- **Barnes-Hut Algorithm**: Implemented O(n log n) N-body simulation replacing naive O(n²) approach
- **Quadtree Data Structures**: Spatial indexing for efficient collision detection and force calculations
- **Numerical Physics**: Real-time gravitational force calculations and Newton's laws implementation
- **Performance Optimization**: Theta-parameter tuning balancing accuracy with computational efficiency

**Advanced JavaScript Architecture**
- **Object-Oriented Design**: Complex ES6 class hierarchy for celestial bodies, simulation engine, and UI components
- **HTML5 Canvas Mastery**: Real-time rendering of dynamic astronomical visualizations
- **Animation Systems**: Smooth 60fps animations using requestAnimationFrame optimization
- **Module Architecture**: Clean separation of concerns with ES6 import/export systems
- **Asynchronous Programming**: File operations, data loading, and non-blocking UI updates
- **Event-Driven Architecture**: Responsive user interaction handling for simulation controls

**User Experience & Accessibility**
- **Accessibility Features**: Support for 4 types of color blindness with dynamic color scheme switching
- **Internationalization (i18n)**: Multi-language support for 4 languages with dynamic content switching
- **Persistent User Preferences**: Local storage implementation for customized user experiences
- **Responsive Design**: Adaptive interfaces for various screen sizes and devices

**Data Systems**
- **Configuration Management**: JSON-based solar system templates and parameter files
- **Export Functionality**: CSV data export for scientific analysis and education
- **Cross-Platform Compatibility**: Consistent data handling across different browsers and devices

#### Scientific & Mathematical Competencies
- **Physics Implementation**: Gravitational laws, orbital mechanics, and collision physics
- **Numerical Methods**: Integration techniques for real-time simulation accuracy
- **Algorithm Analysis**: Complexity optimization and performance profiling
- **Mathematical Modeling**: Celestial body interactions and trajectory calculations

#### Quality Assurance & Testing
- **Unit Testing Framework**: Comprehensive Jest testing suite for Node.js environment
- **Test Coverage**: Systematic validation of physics calculations and edge cases
- **Performance Testing**: Optimization validation with varying object counts
- **Cross-Browser Testing**: Compatibility verification across multiple platforms

---

### JavaScript Evolution & Expertise Development

**Progressive Skill Development:**
- **Sporters Connect (2023-2024)**: Foundation in JavaScript with Firebase SDK, DOM manipulation, and basic event handling
- **SolarSim (2025)**: Advanced JavaScript mastery with complex OOP, Canvas API, real-time physics, and sophisticated animations

**Core JavaScript Competencies Demonstrated:**
- **ES6+ Features**: Classes, modules, arrow functions, destructuring, template literals
- **Asynchronous Programming**: Promises, async/await, event loops, and non-blocking operations  
- **Canvas API Mastery**: Real-time graphics, animations, and interactive visualizations
- **Performance Optimization**: Algorithm efficiency, memory management, and smooth frame rates
- **Architecture Design**: Modular, maintainable, and scalable code structures

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
- **JavaScript (ES6+)**: Advanced full-stack development, Canvas API, real-time simulations, Firebase integration
- **TypeScript**: Full-stack web development, REST APIs, real-time applications, type-safe programming
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
- **Backend**: Node.js, Express, TypeORM, Firebase (Firestore, Authentication, Storage)
- **Frontend**: HTML5 Canvas, responsive design, Google Maps API, modern JavaScript frameworks
- **Data Processing**: Apache Spark, Apache Kafka, Pandas, NumPy
- **Databases**: PostgreSQL, QuestDB, SQL Server, Firestore NoSQL
- **Testing**: Jest, unit testing, performance testing, cross-browser testing
- **Hardware**: Arduino, NI MyRIO, 8051 microcontroller
- **Version Control**: Git, GitHub, collaborative development workflows
- **Deployment**: Linux servers, SSH, Docker, Firebase Hosting

### Specialized Skills
- **Embedded Systems**: Real-time programming, sensor integration, microcontroller optimization
- **Signal Processing**: Fourier analysis, digital signal processing, audio processing
- **Data Engineering**: Big data processing, stream analytics, ETL pipeline development
- **Web Development**: Full-stack applications, REST APIs, WebSocket communication, Firebase integration
- **Scientific Programming**: N-body simulations, algorithm optimization, numerical methods, physics modeling
- **Algorithm Development**: Complex matching systems, spatial data structures, performance optimization
- **Hardware Integration**: Circuit design, sensor calibration, system integration
- **Statistical Analysis**: Mathematical modeling, hypothesis testing, data interpretation
- **Electronics Design**: Circuit analysis, component selection, PCB layout principles
- **User Experience**: Accessibility features, internationalization, responsive design

---

## 🎯 Project Highlights

Each project in this repository demonstrates:
- **Problem-solving skills**: Breaking down complex engineering challenges into manageable components
- **Technical implementation**: Practical application of theoretical concepts across multiple domains
- **System integration**: Combining hardware, software, networking, and database components
- **Algorithm development**: From basic matching systems to advanced N-body simulations
- **Mathematical modeling**: Application of statistical analysis and mathematical foundations
- **Laboratory skills**: Hands-on experience with electronic circuits and measurement equipment
- **Software architecture**: Designing scalable, maintainable, and efficient code structures
- **User experience focus**: Creating accessible, intuitive, and responsive applications
- **Documentation**: Comprehensive technical documentation and professional reporting
- **Team collaboration**: Working effectively in multidisciplinary engineering teams
- **Continuous learning**: Adapting to new technologies, frameworks, and methodologies
- **Quality assurance**: Testing, validation, and optimization of engineering solutions
- **Performance optimization**: From O(n²) to O(n log n) algorithm improvements and real-time systems

---


*This portfolio represents a comprehensive journey through modern engineering practices, from low-level embedded programming to high-level distributed systems, showcasing both theoretical understanding and practical implementation skills.*
