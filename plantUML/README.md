# PlantUML Diagrams - Design Patterns

This directory contains PlantUML class diagrams showing the structure of the codebase before and after applying each design pattern.

## Diagram Files

### 1. Observer Pattern
- **observer_before.puml** - Current state without observer pattern
  - FlightOrder directly manages payment methods
  - No notification mechanism for booking events
  - Logging and notifications must be handled manually

- **observer_after.puml** - After applying observer pattern
  - BookingObserver interface for observing booking events
  - Concrete observers: EmailNotificationObserver, LoggingObserver, AnalyticsObserver
  - FlightOrder notifies all observers of important events
  - Loose coupling between order processing and notifications

### 2. Factory Pattern
- **factory_before.puml** - Current state with scattered type checking
  - Multiple aircraft types (PassengerPlane, Helicopter, PassengerDrone)
  - Instanceof type checking scattered throughout code
  - No polymorphic interface for aircraft
  - Difficult to add new aircraft types

- **factory_after.puml** - After applying factory pattern
  - Abstract Aircraft base class with common interface
  - Concrete implementations: PassengerPlaneImpl, HelicopterImpl, PassengerDroneImpl
  - AircraftFactory for centralized object creation
  - Eliminates scattered type checking
  - Easy to add new aircraft types

### 3. Adapter Pattern
- **adapter_before.puml** - Current state with incompatible payment interfaces
  - CreditCard: isValid(), getAmount(), setAmount()
  - PayPal: static DATA_BASE map with different structure
  - FlightOrder contains duplicate payment processing code
  - Hard to add new payment methods

- **adapter_after.puml** - After applying adapter pattern
  - PaymentProcessor interface as target interface
  - Adapters: CreditCardAdapter, PayPalAdapter, ApplePayAdapter
  - Each adapter converts incompatible interface to PaymentProcessor
  - Unified payment processing
  - Easy to add new payment methods without modifying FlightOrder

### 4. Builder Pattern
- **builder_before.puml** - Current state with multi-step setter-based creation
  - FlightOrder created with constructor
  - Multiple setters for customer, passengers, price
  - Order of operations matters but not enforced
  - No validation until usage
  - Mutable object allows invalid states

- **builder_after.puml** - After applying builder pattern
  - FlightOrderBuilder as separate construction class
  - Fluent interface: addPassenger(), addPassengers(), withPrice()
  - ImmutableFlightOrder as result (immutable after creation)
  - Validation happens in build() before object creation
  - Clear required vs optional fields
  - Prevents invalid intermediate states

### 5. Strategy Pattern
- **strategy_before.puml** - Current state with mixed payment algorithms
  - processOrderWithCreditCard() and processOrderWithPayPal() in same class
  - Different method signatures and logic
  - Code duplication for validation
  - No runtime algorithm selection
  - Hard to add new payment strategies

- **strategy_after.puml** - After applying strategy pattern
  - PaymentStrategy interface defining algorithm contract
  - Concrete strategies: CreditCardPaymentStrategy, PayPalPaymentStrategy, GooglePayPaymentStrategy
  - FlightOrderWithStrategies uses strategies polymorphically
  - Runtime strategy selection via setPaymentStrategy()
  - No code duplication
  - Easy to add new payment strategies

### 6. Chain of Responsibility Pattern
- **chain_before.puml** - Current state with mixed validation logic
  - Validation scattered in Customer.isOrderValid()
  - Multiple concerns mixed in single method
  - Hard to test individual validations
  - Difficult to modify or extend validation rules
  - Not reusable in different contexts

- **chain_after.puml** - After applying chain of responsibility pattern
  - FlightBookingRequest object passed through chain
  - Abstract FlightBookingValidator base class
  - Concrete validators: NoFlyListValidator, CapacityValidator, CustomerHistoryValidator, PriceValidator
  - Each validator has single responsibility
  - Validators linked in configurable chain
  - Request passed through chain until rejected or fully validated
  - Easy to add/remove validators
  - Validation order configurable

## How to Use These Diagrams

### Option 1: View in PlantUML Online Editor
1. Go to http://www.plantuml.com/plantuml/uml/
2. Copy and paste the contents of any .puml file
3. The diagram will render automatically

### Option 2: Use PlantUML CLI
```bash
# Install PlantUML (requires Java)
# On Ubuntu/Debian:
sudo apt-get install plantuml

# On macOS:
brew install plantuml

# Generate PNG from .puml file
plantuml observer_before.puml -o output_dir

# Generate SVG
plantuml observer_before.puml -tsvg -o output_dir
```

### Option 3: VS Code Extension
1. Install "PlantUML" extension by jebbs
2. Open .puml file in VS Code
3. Use Alt+D to preview diagram
4. Use Alt+Shift+E to export

### Option 4: IntelliJ IDEA / PyCharm
1. Install "PlantUML integration" plugin
2. Open .puml file
3. Right-click and select "Show PlantUML Preview"

## Diagram Legend

- **Class boxes**: Represent classes
- **Interfaces**: Shown with <<interface>> stereotype
- **Inheritance** (--|>): Solid line with triangle
- **Implementation** (--|>): Dashed line with triangle
- **Association** (-->): Arrow indicating usage/dependency
- **Composition** (--[contains]): Part of another object
- **Note boxes**: Additional context and explanations

## Pattern Application Guide

### Quick Reference:
| Pattern | Problem | Solution | Diagram Files |
|---------|---------|----------|--|
| Observer | Manual event handling | Automatic notifications | `observer_before.puml`, `observer_after.puml` |
| Factory | Scattered type checking | Centralized creation | `factory_before.puml`, `factory_after.puml` |
| Adapter | Incompatible interfaces | Unified interface | `adapter_before.puml`, `adapter_after.puml` |
| Builder | Complex multi-step creation | Step-by-step construction | `builder_before.puml`, `builder_after.puml` |
| Strategy | Mixed algorithms | Interchangeable strategies | `strategy_before.puml`, `strategy_after.puml` |
| Chain | Mixed validation | Sequential validation handlers | `chain_before.puml`, `chain_after.puml` |

## Related Documentation

For comprehensive details about each pattern, including:
- Reasoning and benefits/drawbacks
- Implementation code examples
- Usage scenarios
- Design principles

See: [../DESIGN_PATTERNS.md](../DESIGN_PATTERNS.md)

---

**Note**: These diagrams represent the architecture before and after applying the patterns. The actual implementation may have additional details not shown in the class diagrams for clarity.
