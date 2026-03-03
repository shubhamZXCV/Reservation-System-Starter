# Flight Reservation System - Documentation

## Project Overview

The **Flight Reservation System** is a Java-based application designed to manage flight and helicopter bookings. It provides functionality to schedule flights between airports, create customer orders, book passengers on scheduled flights, and process payments via credit card or PayPal.

### Key Capabilities
- **Flight Management**: Create flights between airports with specific aircraft
- **Schedule Management**: Schedule flights for specific dates and times
- **Booking System**: Book reservations for customers on scheduled flights
- **Payment Processing**: Handle payments via credit card or PayPal
- **Capacity Management**: Track and enforce aircraft capacity limits
- **Validation**: Aircraft compatibility with airport restrictions

---

## Project Structure

```
Reservation-System-Starter/
├── pom.xml                          # Maven configuration file
├── README.md                         # Project overview
├── documentation.md                 # This file
├── mvnw / mvnw.cmd                  # Maven wrapper scripts
├── src/
│   ├── main/java/                   # Source code
│   │   ├── Runner.java              # Entry point (main class)
│   │   └── flight/reservation/      # Main application package
│   │       ├── Airport.java         # Airport entity
│   │       ├── Customer.java        # Customer entity
│   │       ├── Passenger.java       # Passenger entity
│   │       ├── flight/              # Flight-related classes
│   │       │   ├── Flight.java      # Flight definition
│   │       │   ├── Schedule.java    # Flight schedule manager
│   │       │   └── ScheduledFlight.java  # Scheduled flight instance
│   │       ├── order/               # Order/booking classes
│   │       │   ├── Order.java       # Base order class
│   │       │   └── FlightOrder.java # Flight-specific order
│   │       ├── payment/             # Payment processing classes
│   │       │   ├── CreditCard.java  # Credit card payment
│   │       │   └── Paypal.java      # PayPal payment
│   │       └── plane/               # Aircraft types
│   │           ├── PassengerPlane.java  # Commercial passenger plane
│   │           ├── Helicopter.java  # Helicopter aircraft
│   │           └── PassengerDrone.java  # Passenger drone
│   └── test/java/                   # Test code
│       └── flight.reservation/
│           ├── ScenarioTest.java    # Acceptance-level booking tests
│           ├── ScheduleTest.java    # Unit tests for scheduler
│           └── TestUtil.java        # Test utilities
└── target/                          # Compiled output (auto-generated)
```

---

## Core Components

### 1. **Airport** ([src/main/java/flight/reservation/Airport.java](src/main/java/flight/reservation/Airport.java))
Represents an airport with:
- Name, airline code, and location
- List of allowed aircraft types
- Aircraft compatibility validation for routes

### 2. **Flight** ([src/main/java/flight/reservation/flight/Flight.java](src/main/java/flight/reservation/flight/Flight.java))
Defines a flight route with:
- Flight number
- Departure and arrival airports
- Aircraft type
- Validation that aircraft is allowed at both airports

### 3. **Schedule** ([src/main/java/flight/reservation/flight/Schedule.java](src/main/java/flight/reservation/flight/Schedule.java))
Manages all scheduled flights:
- Add/remove flights to/from schedule
- Search for scheduled flights by flight number
- Maintain list of scheduled flight instances

### 4. **ScheduledFlight** 
Represents a specific flight instance:
- Flight details (number, route, aircraft)
- Scheduled departure date/time
- Passenger list and capacity tracking

### 5. **Customer** ([src/main/java/flight/reservation/Customer.java](src/main/java/flight/reservation/Customer.java))
Represents a customer with:
- Name and email
- Order history
- Method to create flight orders with validation

### 6. **Order / FlightOrder** ([src/main/java/flight/reservation/order](src/main/java/flight/reservation/order))
- `Order`: Base class with price, customer, and passenger information
- `FlightOrder`: Extends Order with flight-specific logic
  - Validates no-fly list (certain customers cannot fly)
  - Manages payment processing (credit card and PayPal)

### 7. **Aircraft Types** ([src/main/java/flight/reservation/plane](src/main/java/flight/reservation/plane))
Hierarchy of aircraft with passenger/crew capacity:
- **PassengerPlane**: Commercial aircraft (A380, A350, Embraer 190, Antonov AN2)
- **Helicopter**: Helicopter aircraft (model types)
- **PassengerDrone**: Modern passenger drone option

### 8. **Payment Processing** ([src/main/java/flight/reservation/payment](src/main/java/flight/reservation/payment))
- **CreditCard**: Credit card payment with basic validation
- **Paypal**: PayPal payment method

---

## Technology Stack

- **Java Version**: 11+
- **Build Tool**: Maven
- **Testing Framework**: JUnit 5 (Jupiter)
- **Mocking**: Mockito
- **Architecture**: N-tier with entity, order, and payment layers

---

## Prerequisites

Before running this project, ensure you have:

1. **Java Development Kit (JDK) 11 or higher**
   - Check installation: `java -version`

2. **Maven 3.6.0 or higher**
   - Check installation: `mvn -version`
   - Or use the included Maven wrapper

---

## How to Build and Run

### Option 1: Using Maven Wrapper (Recommended)

#### On Linux/Mac:
```bash
# Navigate to project directory
cd /home/shubham/Reservation-System-Starter

# Clean and build
./mvnw clean install

# Run tests
./mvnw test

# Run the main application
./mvnw exec:java -Dexec.mainClass="Runner"
```

#### On Windows:
```bash
# Navigate to project directory
cd \path\to\Reservation-System-Starter

# Clean and build
mvnw.cmd clean install

# Run tests
mvnw.cmd test

# Run the main application
mvnw.cmd exec:java -Dexec.mainClass="Runner"
```

### Option 2: Using Installed Maven

```bash
# Clean and build
mvn clean install

# Run tests
mvn test

# Run the main application
mvn exec:java -Dexec.mainClass="Runner"
```

### Build Output
After successful build:
- **Compiled classes**: `target/classes/`
- **Test classes**: `target/test-classes/`
- **JAR file**: `target/flight-reservation-1.0-SNAPSHOT.jar`

---

## Running Tests

### Run All Tests
```bash
./mvnw test
```

### Run Specific Test Class
```bash
# Run schedule tests only
./mvnw test -Dtest=ScheduleTest

# Run scenario tests only
./mvnw test -Dtest=ScenarioTest
```

### Run Tests with Verbose Output
```bash
./mvnw test -X
```

### Test Coverage
Two main test suites are included:

1. **ScheduleTest** ([src/test/java/flight.reservation/ScheduleTest.java](src/test/java/flight.reservation/ScheduleTest.java))
   - Unit tests for flight scheduling functionality
   - Tests adding/removing flights, searching for scheduled flights
   - Covers schedule edge cases

2. **ScenarioTest** ([src/test/java/flight.reservation/ScenarioTest.java](src/test/java/flight.reservation/ScenarioTest.java))
   - Acceptance-level tests for complete booking scenarios
   - Tests customer bookings, payment processing
   - Tests no-fly list enforcement
   - Tests aircraft capacity validation

---

## How the System Works

### Workflow: Creating and Processing a Flight Booking

1. **Setup Phase**
   ```
   Create Airports → Create Flights → Schedule Flights
   ```

2. **Booking Phase**
   ```
   Customer Creates Order → Specify Passengers → Set Price
   ```

3. **Payment Phase**
   ```
   Process Payment (Credit Card or PayPal) → Validate → Finalize Order
   ```

### Example Flow

```
1. Create Airports
   - Berlin Airport (BER)
   - Frankfurt Airport (FRA)

2. Create Flight
   - Flight #1 from Berlin to Frankfurt with Airbus A380

3. Schedule Flight
   - Schedule Flight #1 for 2026-03-10 10:00 AM

4. Create Customer
   - Customer: "Max Mustermann" (max@example.com)

5. Create Order
   - Add Passengers: "Alice Smith", "Bob Johnson"
   - Set Price: 500 EUR

6. Process Payment
   - Use Credit Card: 4532123456789010
   - Expiry: 2027-12-31
   - CVV: 123

7. Finalize Order
   - Order is closed and confirmed
```

---

## Key Validations and Rules

### Aircraft Compatibility
- Each airport has a list of allowed aircraft types
- Flights cannot be created if aircraft is not allowed at departure OR arrival airport
- `IllegalArgumentException` is thrown for invalid aircraft-airport combinations

### No-Fly List
- Certain customers are on the no-fly list (default: "Peter", "Johannes")
- These customers cannot create orders
- Passengers on this list cannot be booked
- `IllegalStateException` is thrown when attempting to book blacklisted passengers

### Capacity Constraints
- Each aircraft has defined passenger and crew capacity
- Aircraft cannot be overbooked
- Available capacity is checked before confirming orders
- System throws `IllegalStateException` if capacity exceeded

### Payment Validation
- **Credit Card**: Validates card number length, expiration date, and CVV
- **PayPal**: Validates account balance against order price (basic dummy validation)
- Orders can only be closed once successfully paid

---

## Important Classes and Methods

### Customer Methods
- `createOrder(List<String> passengerNames, List<ScheduledFlight> flights, double price)`
  - Creates a new flight order with validation
  - Returns `FlightOrder` object

### Schedule Methods
- `scheduleFlight(Flight flight, Date date)` - Add flight to schedule
- `searchScheduledFlight(int flightNumber)` - Find scheduled flight by number
- `removeFlight(Flight flight)` - Remove flight from schedule
- `getScheduledFlights()` - Get all scheduled flights

### FlightOrder Methods
- `processOrderWithCreditCard(CreditCard creditCard)` - Pay with credit card
- `processOrderWithCreditCardDetail(String number, Date expiryDate, String cvv)` - Pay with card details
- `processOrderWithPaypal(Paypal paypal)` - Pay with PayPal
- `isClosed()` - Check if order is finalized

---

## Extensibility

The system is designed to be extended with:

1. **New Aircraft Types**: Add new classes extending the aircraft hierarchy
2. **New Payment Methods**: Implement additional payment processors
3. **New Validation Rules**: Extend Customer and FlightOrder validation logic
4. **User Interface**: Add servlet/REST layer for web access
5. **Database Integration**: Add persistence layer for data storage
6. **Notification System**: Add email/SMS notifications for bookings

---

## Troubleshooting

### Build Fails - Java Version Incompatible
```
Error: ... unsupported class version 55.0 ...
```
**Solution**: Install Java 11 or higher. Update `JAVA_HOME` environment variable.

### Tests Fail - ClassNotFoundException
```
Error: Could not find or load main class
```
**Solution**: Run `./mvnw clean compile` to rebuild, then `./mvnw test`

### Maven Wrapper Permission Denied (Linux/Mac)
```
Error: Permission denied
```
**Solution**: Make wrapper executable
```bash
chmod +x mvnw
./mvnw clean install
```

### Cannot Find Maven (Using `mvn` command)
```
Error: mvn: command not found
```
**Solution**: Either install Maven globally OR use the included Maven wrapper with `./mvnw`

---

## Project Quality Notes

⚠️ **Note**: This project is a prototype demonstrating reservation system concepts. The code quality is intentionally left at a level suitable for educational purposes and can be improved for production use.

Potential improvements for production:
- Better error handling and exception types
- Database persistence instead of in-memory storage
- API layer (REST/GraphQL)
- Enhanced validation and business rules
- Comprehensive logging
- Configuration management
- Authentication and authorization
- Transaction management

---

## References

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Java 11 Features](https://docs.oracle.com/en/java/javase/11/)

---

## Contact & Support

For questions or issues with this project, review the existing test cases in `ScenarioTest.java` and `ScheduleTest.java` for usage examples.

---

**Last Updated**: March 2026  
**Java Version**: 11+  
**Maven Version**: 3.6.0+
