import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookingDAO {

    public boolean bookTicket(int userId, int trainId,
                              String passengerName, int age,
                              String gender) {

        String seatSql =
                "SELECT available_seats FROM trains WHERE train_id = ?";

        String bookingSql =
                "INSERT INTO bookings " +
                        "(user_id, train_id, passenger_name, age, gender, " +
                        "seat_number, booking_status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, 'Confirmed')";

        String updateSeatSql =
                "UPDATE trains SET available_seats = available_seats - 1 " +
                        "WHERE train_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement seatStatement =
                     connection.prepareStatement(seatSql)) {

            seatStatement.setInt(1, trainId);

            ResultSet resultSet = seatStatement.executeQuery();

            if (!resultSet.next()) {
                return false;
            }

            int availableSeats =
                    resultSet.getInt("available_seats");

            if (availableSeats <= 0) {
                return false;
            }

            int seatNumber = 0;

            try (PreparedStatement lastSeatStatement =
                         connection.prepareStatement(
                                 "SELECT MAX(seat_number) " +
                                         "FROM bookings WHERE train_id = ?")) {

                lastSeatStatement.setInt(1, trainId);

                ResultSet seatResult =
                        lastSeatStatement.executeQuery();

                if (seatResult.next()) {
                    seatNumber = seatResult.getInt(1) + 1;
                }

                if (seatNumber == 1) {
                    seatNumber = 1;
                }
            }

            try (PreparedStatement bookingStatement =
                         connection.prepareStatement(bookingSql);
                 PreparedStatement updateStatement =
                         connection.prepareStatement(updateSeatSql)) {

                bookingStatement.setInt(1, userId);
                bookingStatement.setInt(2, trainId);
                bookingStatement.setString(3, passengerName);
                bookingStatement.setInt(4, age);
                bookingStatement.setString(5, gender);
                bookingStatement.setInt(6, seatNumber);

                bookingStatement.executeUpdate();

                updateStatement.setInt(1, trainId);
                updateStatement.executeUpdate();

                return true;
            }

        } catch (Exception e) {
            System.out.println("Booking failed!");
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Booking> getBookings(int userId) {

        ArrayList<Booking> list = new ArrayList<>();

        String sql =
                "SELECT * FROM bookings " +
                        "WHERE user_id = ? " +
                        "ORDER BY booking_date DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Booking booking = new Booking(
                        resultSet.getInt("booking_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("train_id"),
                        resultSet.getString("passenger_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("gender"),
                        resultSet.getInt("seat_number"),
                        resultSet.getString("booking_date"),
                        resultSet.getString("booking_status")
                );

                list.add(booking);
            }

        } catch (Exception e) {
            System.out.println("Unable to load bookings!");
            e.printStackTrace();
        }

        return list;
    }

    public boolean cancelBooking(int bookingId, int userId) {

        String findSql =
                "SELECT train_id, booking_status " +
                        "FROM bookings " +
                        "WHERE booking_id = ? AND user_id = ?";

        String cancelSql =
                "UPDATE bookings " +
                        "SET booking_status = 'Cancelled' " +
                        "WHERE booking_id = ? AND user_id = ?";

        String updateSeatSql =
                "UPDATE trains " +
                        "SET available_seats = available_seats + 1 " +
                        "WHERE train_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement findStatement =
                     connection.prepareStatement(findSql)) {

            findStatement.setInt(1, bookingId);
            findStatement.setInt(2, userId);

            ResultSet resultSet =
                    findStatement.executeQuery();

            if (!resultSet.next()) {
                return false;
            }

            int trainId = resultSet.getInt("train_id");
            String status = resultSet.getString("booking_status");

            if ("Cancelled".equalsIgnoreCase(status)) {
                return false;
            }

            try (PreparedStatement cancelStatement =
                         connection.prepareStatement(cancelSql);
                 PreparedStatement seatStatement =
                         connection.prepareStatement(updateSeatSql)) {

                cancelStatement.setInt(1, bookingId);
                cancelStatement.setInt(2, userId);
                cancelStatement.executeUpdate();

                seatStatement.setInt(1, trainId);
                seatStatement.executeUpdate();

                return true;
            }

        } catch (Exception e) {
            System.out.println("Cancellation failed!");
            e.printStackTrace();
            return false;
        }
    }
}