# GitHub Issues for Design Pattern Refactoring

This document contains all GitHub issues to be created and resolved. Run these commands using GitHub CLI:

```bash
gh auth login  # Login first if not already authenticated
```

## Issue #1: Factory Pattern - Aircraft Creation

```bash
gh issue create \
  --title "Refactor: Implement Factory Pattern for Aircraft Creation" \
  --body "## Description
Eliminate scattered aircraft type-checking by implementing the Factory Pattern.

## Problem
Aircraft creation logic is scattered throughout the codebase with multiple instanceof checks:
- ScheduledFlight.getCapacity() uses instanceof for PassengerPlane, Helicopter, PassengerDrone
- Capacity determination logic duplicated across multiple methods
- Aircraft models don't share a common interface

## Solution
Implement Factory Pattern to:
- Centralize aircraft creation and object handling
- Eliminate type-checking duplication
- Create common Aircraft interface for polymorphic handling
- Improve maintainability and extensibility

## Acceptance Criteria
✅ All 20 tests pass  
✅ No public API changes  
✅ Aircraft behavior unchanged  
✅ Backward compatible (existing constructors still work)  
✅ New Aircraft interface with getCapacity() and getCrewCapacity() methods

## Files to Modify
- src/main/java/flight/reservation/plane/PassengerPlane.java
- src/main/java/flight/reservation/plane/Helicopter.java
- src/main/java/flight/reservation/plane/PassengerDrone.java
- src/main/java/flight/reservation/flight/ScheduledFlight.java

## New Files to Create
- src/main/java/flight/reservation/plane/Aircraft.java (interface)
- src/main/java/flight/reservation/plane/AircraftFactory.java (factory)

## Implementation Notes
- Keep existing constructors for backward compatibility
- Have classes implement Aircraft interface
- Update ScheduledFlight to use polymorphism instead of instanceof checks"
```

## Issue #2: Adapter Pattern - Payment Methods

```bash
gh issue create \
  --title "Refactor: Implement Adapter Pattern for Payment Methods" \
  --body "## Description
Unify payment method interfaces using the Adapter Pattern.

## Problem
Payment methods have incompatible interfaces:
- CreditCard: has methods isValid(), getAmount(), setAmount()
- PayPal: static DATA_BASE map with email→password mapping
- FlightOrder.processOrderWithCreditCard() and processOrderWithPayPal() have different signatures
- Payment processing logic is duplicated and inconsistent

## Solution
Implement Adapter Pattern to:
- Create PaymentProcessor interface as the target interface
- Create CreditCardAdapter and PayPalAdapter
- Unify payment processing with consistent interface
- Simplify FlightOrder payment logic

## Acceptance Criteria
✅ All 20 tests pass  
✅ No public API changes  
✅ Legacy payment methods still work  
✅ New PaymentProcessor interface created  
✅ Adapters handle CreditCard and PayPal payment processing

## Files to Modify
- src/main/java/flight/reservation/order/FlightOrder.java
- src/main/java/flight/reservation/payment/CreditCard.java
- src/main/java/flight/reservation/payment/Paypal.java

## New Files to Create
- src/main/java/flight/reservation/payment/PaymentProcessor.java (interface)
- src/main/java/flight/reservation/payment/CreditCardAdapter.java
- src/main/java/flight/reservation/payment/PayPalAdapter.java

## Implementation Notes
- Keep existing processOrderWithCreditCard() and processOrderWithPayPal() methods
- Have them delegate to new adapter-based implementation internally
- PaymentProcessor should have: isPaymentValid(), processPayment(amount), getPaymentMethodName()"
```

## Issue #3: Strategy Pattern - Payment Algorithms

```bash
gh issue create \
  --title "Refactor: Implement Strategy Pattern for Payment Algorithms" \
  --body "## Description
Separate payment processing algorithms using the Strategy Pattern.

## Problem
Payment algorithm logic is mixed within FlightOrder class:
- CreditCard payment strategy: card validation, amount checking, balance deduction
- PayPal payment strategy: email/password validation, balance lookup
- Different validation and processing logic for each method
- Difficult to add new payment methods without modifying FlightOrder

## Solution
Implement Strategy Pattern to:
- Create PaymentStrategy interface with validate() and execute() methods
- Create CreditCardPaymentStrategy and PayPalPaymentStrategy
- Encapsulate algorithm implementations separately
- Make FlightOrder algorithm-agnostic

## Acceptance Criteria
✅ All 20 tests pass  
✅ No public API changes  
✅ Existing payment methods work identically  
✅ New PaymentStrategy interface created  
✅ Strategy implementations for CreditCard and PayPal  
✅ Easy to add new payment strategies

## Files to Modify
- src/main/java/flight/reservation/order/FlightOrder.java

## New Files to Create
- src/main/java/flight/reservation/payment/PaymentStrategy.java (interface)
- src/main/java/flight/reservation/payment/CreditCardPaymentStrategy.java
- src/main/java/flight/reservation/payment/PayPalPaymentStrategy.java

## Implementation Notes
- PaymentStrategy interface: boolean validate(), boolean execute(amount), String getStrategyName()
- Each strategy encapsulates its validation and processing logic
- FlightOrder can use strategies for different payment types
- Pair with Adapter Pattern for unified processing"
```

## Issue #4: Chain of Responsibility - Validator Chain

```bash
gh issue create \
  --title "Refactor: Implement Chain of Responsibility for Booking Validation" \
  --body "## Description
Reorganize validation logic using Chain of Responsibility Pattern.

## Problem
Validation logic is mixed and scattered:
- Customer.isOrderValid() checks: no-fly list, passenger names, flight capacity
- Multiple conditions combined in single method
- Difficult to add new validators without modifying existing code
- Validation order and logic unclear

## Solution
Implement Chain of Responsibility Pattern to:
- Create FlightBookingValidator abstract handler
- Create concrete validators: NoFlyListValidator, CapacityValidator, PriceValidator
- Build validator chain for sequential processing
- Improve validation clarity and extensibility

## Acceptance Criteria
✅ All 20 tests pass  
✅ No public API changes  
✅ Validation logic unchanged (same results)  
✅ New FlightBookingValidator abstract class  
✅ Concrete validators for each validation rule  
✅ Easy to add new validators to chain

## Files to Modify
- src/main/java/flight/reservation/Customer.java

## New Files to Create
- src/main/java/flight/reservation/validator/FlightBookingValidator.java (abstract handler)
- src/main/java/flight/reservation/validator/NoFlyListValidator.java
- src/main/java/flight/reservation/validator/CapacityValidator.java
- src/main/java/flight/reservation/validator/PriceValidator.java
- src/main/java/flight/reservation/validator/FlightBookingRequest.java (request object)

## Implementation Notes
- FlightBookingRequest: encapsulate customer, passengerNames, flights, price
- Validators check specific rules and pass request to next validator
- If validation fails, set rejectionReason and return
- Customer.isOrderValid() delegates to validator chain internally"
```

## Issue #5: Builder Pattern - Order Construction

```bash
gh issue create \
  --title "Refactor: Implement Builder Pattern for FlightOrder Construction" \
  --body "## Description
Improve FlightOrder construction using the Builder Pattern.

## Problem
FlightOrder creation is error-prone:
- Requires constructor followed by multiple setters
- No validation until object is used
- Price must be calculated separately
- Object is mutable with no guaranteed consistency

## Solution
Implement Builder Pattern to:
- Create FlightOrderBuilder for flexible, readable construction
- Ensure all required fields are set before object creation
- Validate object state before instantiation
- Optional parameters handled elegantly
- Immutable FlightOrder once created

## Acceptance Criteria
✅ All 20 tests pass  
✅ No public API changes  
✅ Existing FlightOrder constructors still work  
✅ FlightOrderBuilder created with fluent interface  
✅ Validation happens at build() time  
✅ Optional parameters supported

## Files to Modify
- src/main/java/flight/reservation/order/FlightOrder.java
- src/main/java/flight/reservation/order/Order.java

## New Files to Create
- src/main/java/flight/reservation/order/FlightOrderBuilder.java

## Implementation Notes
- Builder has setters that return 'this' for method chaining
- Required fields: customer, flights, passengers
- Optional fields: price (calculated if not set)
- build() method validates and creates immutable FlightOrder
- Legacy FlightOrder constructors and setters still work"
```

## Issue #6: Observer Pattern - Booking Notifications

```bash
gh issue create \
  --title "Refactor: Implement Observer Pattern for Booking Notifications" \
  --body "## Description
Add event notification system using the Observer Pattern.

## Problem
No mechanism for booking events:
- Application doesn't notify about booking confirmations
- Payment processing events have no handlers
- Cancellations aren't communicated
- No audit trail or logging for events

## Solution
Implement Observer Pattern to:
- Create BookingObserver interface for event notifications
- Create EmailNotificationObserver and LoggingObserver
- Notify observers when booking events occur
- Decouple event producers from consumers

## Acceptance Criteria
✅ All 20 tests pass  
✅ No public API changes  
✅ Existing payment flow unchanged  
✅ BookingObserver interface created  
✅ Observers can be added/removed at runtime  
✅ Events: onBookingConfirmed(), onPaymentProcessed(), onBookingCancelled()

## Files to Modify
- src/main/java/flight/reservation/order/FlightOrder.java

## New Files to Create
- src/main/java/flight/reservation/observer/BookingObserver.java (interface)
- src/main/java/flight/reservation/observer/EmailNotificationObserver.java
- src/main/java/flight/reservation/observer/LoggingObserver.java

## Implementation Notes
- FlightOrder maintains list of BookingObserver instances
- addObserver() and removeObserver() methods to manage observers
- Notify in processOrderWithCreditCard() and processOrderWithPayPal()
- Observers are optional (payment works even with no observers)
- Events passed to observers: order reference and relevant data"
```

---

## How to Create All Issues

Save these commands to a file and run them:

```bash
# 1. Authenticate first
gh auth login

# 2. Copy each issue creation command above and run it in the repository

# Or use this combined script:
#!/bin/bash
cd /home/shubham/Reservation-System-Starter

gh issue create --title "Refactor: Implement Factory Pattern for Aircraft Creation" --body "Issue #1 body..."
gh issue create --title "Refactor: Implement Adapter Pattern for Payment Methods" --body "Issue #2 body..."
gh issue create --title "Refactor: Implement Strategy Pattern for Payment Algorithms" --body "Issue #3 body..."
gh issue create --title "Refactor: Implement Chain of Responsibility for Booking Validation" --body "Issue #4 body..."
gh issue create --title "Refactor: Implement Builder Pattern for FlightOrder Construction" --body "Issue #5 body..."
gh issue create --title "Refactor: Implement Observer Pattern for Booking Notifications" --body "Issue #6 body..."
```

## Status

- [ ] Issue #1: Factory Pattern - Aircraft Creation
- [ ] Issue #2: Adapter Pattern - Payment Methods
- [ ] Issue #3: Strategy Pattern - Payment Algorithms
- [ ] Issue #4: Chain of Responsibility - Validation
- [ ] Issue #5: Builder Pattern - Order Construction
- [ ] Issue #6: Observer Pattern - Notifications
