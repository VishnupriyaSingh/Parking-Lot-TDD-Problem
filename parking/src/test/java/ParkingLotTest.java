import static org.junit.jupiter.api.Assertions.*;

import com.example.Car;
import com.example.ParkingLot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParkingLotTest {
    private ParkingLot parkingLot;

    @BeforeEach
    public void setUp() {
        // Initialize a new parking lot with a capacity of 5 for each test
        parkingLot = new ParkingLot("TestLot", 5, 1);
    }

    @Test
    public void testParkCar() {
        Car car = new Car("CAR001", "Blue", "Toyota", "Medium", false);
        assertTrue(parkingLot.parkCar(car, "0"));
        assertEquals(1, parkingLot.getCarsParked());
    }

    @Test
    public void testUnparkCar() {
        Car car = new Car("CAR001", "Blue", "Toyota", "Medium", false);
        parkingLot.parkCar(car, "0");
        assertTrue(parkingLot.unparkCar("CAR001"));
        assertEquals(0, parkingLot.getCarsParked());
    }

    @Test
    public void testIsFull() {
        for (int i = 1; i <= 5; i++) {
            Car car = new Car("CAR001", "Blue", "Toyota", "Medium", false);
            parkingLot.parkCar(car, "i");
        }
        assertTrue(parkingLot.isFull());
    }

    @Test
    public void testFindCarLocation() {
        Car car = new Car("CAR001", "Blue", "Toyota", "Medium", false);
        parkingLot.parkCar(car, "2");
        assertEquals("Your car is parked at space 0.", parkingLot.findCarLocation("CAR001"));
    }
}
