import java.time.LocalDate;
import java.util.List;

import model.Car;
import model.Customer;
import model.Reservation;

public class App {
    public static void main(String[] args) throws Exception {
        RentalSystem rentalSystem = new RentalSystem();
        Car car1 = new Car("Toyata", "Glanza", 2020, "ABCD1234", 600, true);
        Car car2 = new Car("Honda", "Civic", 2019, "DEFG5678", 700, true);
        Car car3 = new Car("Ford", "Mustang", 2023, "HIJK8907", 1200, true);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        //Create customer
        Customer customer = new Customer("ABC", "7558555541", "DL123");

        //make reservation

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);

        List<Car> availableCars = rentalSystem.searchCars("Toyata", "Glanza", startDate, endDate);

        if(!availableCars.isEmpty()){
            Car selectedCar = availableCars.get(0);
            Reservation reservation = rentalSystem.makeReservation(customer, selectedCar, startDate, endDate);
            if(reservation != null){
                boolean isPaymentSuccessful = rentalSystem.processPayment(reservation);
                if(isPaymentSuccessful == true){
                    System.out.println("Reservation successful with Reservation is Id: " + reservation.getReservationId());
                }
                else{
                    System.out.println("Payment Failed, Reservation Cancelled" + reservation.getReservationId());
                    rentalSystem.cancelReservation(reservation.getReservationId());
                }
            }
            else{
                System.out.println("Selected Car not available for given Dates");
            }
        }else{
            System.out.println("No car available with given specification");
        }


    }
}
