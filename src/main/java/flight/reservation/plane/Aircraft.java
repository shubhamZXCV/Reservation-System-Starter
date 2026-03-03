package flight.reservation.plane;

/**
 * Interface for aircraft types.
 * Defines common methods for getting capacity and crew capacity.
 */
public interface Aircraft {
    String getModel();
    int getPassengerCapacity();
    int getCrewCapacity();
}
