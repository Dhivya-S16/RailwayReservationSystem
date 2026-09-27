import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrainDAO {

    public ArrayList<Train> getAllTrains() {

        ArrayList<Train> list = new ArrayList<>();

        String sql = "SELECT * FROM trains";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Train train = new Train(
                        resultSet.getInt("train_id"),
                        resultSet.getString("train_number"),
                        resultSet.getString("train_name"),
                        resultSet.getString("source_station"),
                        resultSet.getString("destination_station"),
                        resultSet.getString("departure_time"),
                        resultSet.getString("arrival_time"),
                        resultSet.getInt("available_seats")
                );

                list.add(train);
            }

        } catch (Exception e) {
            System.out.println("Unable to load trains!");
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Train> searchTrains(String source, String destination) {

        ArrayList<Train> list = new ArrayList<>();

        String sql = """
                SELECT * FROM trains
                WHERE source_station LIKE ?
                AND destination_station LIKE ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, "%" + source + "%");
            statement.setString(2, "%" + destination + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Train train = new Train(
                        resultSet.getInt("train_id"),
                        resultSet.getString("train_number"),
                        resultSet.getString("train_name"),
                        resultSet.getString("source_station"),
                        resultSet.getString("destination_station"),
                        resultSet.getString("departure_time"),
                        resultSet.getString("arrival_time"),
                        resultSet.getInt("available_seats")
                );

                list.add(train);
            }

        } catch (Exception e) {
            System.out.println("Unable to search trains!");
            e.printStackTrace();
        }

        return list;
    }
}
