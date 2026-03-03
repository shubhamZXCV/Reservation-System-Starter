package flight.reservation.plane;

/**
 * Factory for creating Aircraft instances.
 * Centralizes aircraft creation logic and handles model-to-type mapping.
 */
public class AircraftFactory {

    /**
     * Create an aircraft by type and model.
     *
     * @param type the aircraft type (e.g., "passengerplane", "helicopter", "drone")
     * @param model the model name
     * @return an Aircraft instance
     * @throws IllegalArgumentException if type is not recognized
     */
    public static Aircraft createAircraft(String type, String model) {
        if (type == null || model == null) {
            throw new IllegalArgumentException("Type and model cannot be null");
        }

        switch (type.toLowerCase()) {
            case "passengerplane":
                return new PassengerPlane(model);
            case "helicopter":
                return new Helicopter(model);
            case "drone":
            case "passengerdrone":
                return new PassengerDrone(model);
            default:
                throw new IllegalArgumentException("Aircraft type '" + type + "' is not recognized");
        }
    }

    /**
     * Create an aircraft by model name only.
     * Automatically determines the type based on model.
     *
     * @param model the model name
     * @return an Aircraft instance
     * @throws IllegalArgumentException if model is not recognized
     */
    public static Aircraft createFromModel(String model) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }

        // Try PassengerPlane models
        if (isPassengerPlaneModel(model)) {
            return new PassengerPlane(model);
        }
        // Try Helicopter models
        if (isHelicopterModel(model)) {
            return new Helicopter(model);
        }
        // Try PassengerDrone models
        if (isPassengerDroneModel(model)) {
            return new PassengerDrone(model);
        }

        throw new IllegalArgumentException("Model '" + model + "' is not recognized");
    }

    private static boolean isPassengerPlaneModel(String model) {
        return model.matches("A380|A350|Embraer 190|Antonov AN2");
    }

    private static boolean isHelicopterModel(String model) {
        return model.matches("H1|H2");
    }

    private static boolean isPassengerDroneModel(String model) {
        return model.equals("HypaHype");
    }
}
