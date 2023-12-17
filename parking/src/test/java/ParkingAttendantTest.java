import static org.junit.jupiter.api.Assertions.*;

import com.example.Car;
import com.example.ParkingAttendant;
import com.example.ParkingLot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParkingAttendantTest {
    private ParkingAttendant attendant;
    private ParkingLot parkingLot;

    @BeforeEach
    public void setUp() {
        // Initialize a parking lot and an attendant for each test
        parkingLot = new ParkingLot("TestLot", 5, 1);
        attendant = new ParkingAttendant(parkingLot, "2");
    }

    @Test
    public void testParkCar() {
        Car car = new Car("CAR001", "Blue", "Toyota", "Medium", false);
        assertTrue(attendant.parkCar(car));
        assertEquals(1, parkingLot.getCarsParked());
    }

    @Test
    public void testFindCarLocation() {
        Car car = new Car("CAR001", "Blue", "Toyota", "Medium", false);
        attendant.parkCar(car);
        assertEquals("Your car is parked at space 0.", attendant.findCarLocation("CAR001"));
    }

    @Test
    public void testUnparkCar() {
        Car car = new Car("CAR001", "Blue", "Toyota", "Medium", false);
        attendant.parkCar(car);
        assertTrue(attendant.unparkCar("CAR001"));
        assertEquals(0, parkingLot.getCarsParked());
    }
}

