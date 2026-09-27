import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DashboardGUI extends JFrame {

    private User user;

    public DashboardGUI(User user) {

        this.user = user;

        setTitle("Railway Reservation System - Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel =
                new JLabel("Welcome, " + user.getUsername());

        welcomeLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        JButton viewTrainsButton =
                new JButton("View Trains");

        JButton searchTrainsButton =
                new JButton("Search Trains");

        JButton bookTicketButton =
                new JButton("Book Ticket");

        JButton myBookingsButton =
                new JButton("My Bookings");

        JButton cancelTicketButton =
                new JButton("Cancel Ticket");

        JButton logoutButton =
                new JButton("Logout");

        JPanel panel = new JPanel(
                new GridLayout(3, 2, 15, 15)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 50, 30, 50
                )
        );

        panel.add(viewTrainsButton);
        panel.add(searchTrainsButton);
        panel.add(bookTicketButton);
        panel.add(myBookingsButton);
        panel.add(cancelTicketButton);
        panel.add(logoutButton);

        add(welcomeLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // View all trains
        viewTrainsButton.addActionListener(e -> {

            TrainDAO trainDAO = new TrainDAO();

            ArrayList<Train> trains =
                    trainDAO.getAllTrains();

            if (trains.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No trains available."
                );

                return;
            }

            StringBuilder message =
                    new StringBuilder();

            for (Train train : trains) {

                message.append("Train No: ")
                        .append(train.getTrainNumber())
                        .append("\n");

                message.append("Train Name: ")
                        .append(train.getTrainName())
                        .append("\n");

                message.append("Route: ")
                        .append(train.getSourceStation())
                        .append(" → ")
                        .append(train.getDestinationStation())
                        .append("\n");

                message.append("Departure: ")
                        .append(train.getDepartureTime())
                        .append("\n");

                message.append("Arrival: ")
                        .append(train.getArrivalTime())
                        .append("\n");

                message.append("Available Seats: ")
                        .append(train.getAvailableSeats())
                        .append("\n");

                message.append("-----------------------------\n");
            }

            JTextArea textArea =
                    new JTextArea(message.toString());

            textArea.setEditable(false);

            JScrollPane scrollPane =
                    new JScrollPane(textArea);

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Available Trains",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Search trains
        searchTrainsButton.addActionListener(e -> {

            String source = JOptionPane.showInputDialog(
                    this,
                    "Enter source station:"
            );

            if (source == null || source.trim().isEmpty()) {
                return;
            }

            String destination = JOptionPane.showInputDialog(
                    this,
                    "Enter destination station:"
            );

            if (destination == null ||
                    destination.trim().isEmpty()) {
                return;
            }

            TrainDAO trainDAO = new TrainDAO();

            ArrayList<Train> trains =
                    trainDAO.searchTrains(
                            source.trim(),
                            destination.trim()
                    );

            if (trains.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No trains found."
                );

                return;
            }

            StringBuilder message =
                    new StringBuilder();

            for (Train train : trains) {

                message.append("Train No: ")
                        .append(train.getTrainNumber())
                        .append("\n");

                message.append("Train Name: ")
                        .append(train.getTrainName())
                        .append("\n");

                message.append("Route: ")
                        .append(train.getSourceStation())
                        .append(" → ")
                        .append(train.getDestinationStation())
                        .append("\n");

                message.append("Departure: ")
                        .append(train.getDepartureTime())
                        .append("\n");

                message.append("Arrival: ")
                        .append(train.getArrivalTime())
                        .append("\n");

                message.append("Available Seats: ")
                        .append(train.getAvailableSeats())
                        .append("\n");

                message.append("-----------------------------\n");
            }

            JTextArea textArea =
                    new JTextArea(message.toString());

            textArea.setEditable(false);

            JScrollPane scrollPane =
                    new JScrollPane(textArea);

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Book Ticket
        bookTicketButton.addActionListener(e -> {

            String trainNumber =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter train number:"
                    );

            if (trainNumber == null ||
                    trainNumber.trim().isEmpty()) {
                return;
            }

            TrainDAO trainDAO = new TrainDAO();

            ArrayList<Train> trains =
                    trainDAO.getAllTrains();

            Train selectedTrain = null;

            for (Train train : trains) {

                if (train.getTrainNumber()
                        .equalsIgnoreCase(trainNumber.trim())) {

                    selectedTrain = train;
                    break;
                }
            }

            if (selectedTrain == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Train not found."
                );

                return;
            }

            if (selectedTrain.getAvailableSeats() <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No seats available."
                );

                return;
            }

            String passengerName =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter passenger name:"
                    );

            if (passengerName == null ||
                    passengerName.trim().isEmpty()) {
                return;
            }

            String ageInput =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter passenger age:"
                    );

            if (ageInput == null ||
                    ageInput.trim().isEmpty()) {
                return;
            }

            int age;

            try {

                age = Integer.parseInt(
                        ageInput.trim()
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid age."
                );

                return;
            }

            String gender =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter gender:"
                    );

            if (gender == null ||
                    gender.trim().isEmpty()) {
                return;
            }

            BookingDAO bookingDAO =
                    new BookingDAO();

            boolean success =
                    bookingDAO.bookTicket(
                            user.getUserId(),
                            selectedTrain.getTrainId(),
                            passengerName.trim(),
                            age,
                            gender.trim()
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket booked successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket booking failed."
                );
            }
        });

        // My Bookings
        myBookingsButton.addActionListener(e -> {

            BookingDAO bookingDAO =
                    new BookingDAO();

            ArrayList<Booking> bookings =
                    bookingDAO.getBookings(
                            user.getUserId()
                    );

            if (bookings.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No bookings found."
                );

                return;
            }

            StringBuilder message =
                    new StringBuilder();

            for (Booking booking : bookings) {

                message.append("Booking ID: ")
                        .append(booking.getBookingId())
                        .append("\n");

                message.append("Train ID: ")
                        .append(booking.getTrainId())
                        .append("\n");

                message.append("Passenger Name: ")
                        .append(booking.getPassengerName())
                        .append("\n");

                message.append("Age: ")
                        .append(booking.getAge())
                        .append("\n");

                message.append("Gender: ")
                        .append(booking.getGender())
                        .append("\n");

                message.append("Seat Number: ")
                        .append(booking.getSeatNumber())
                        .append("\n");

                message.append("Booking Date: ")
                        .append(booking.getBookingDate())
                        .append("\n");

                message.append("Status: ")
                        .append(booking.getBookingStatus())
                        .append("\n");

                message.append("-----------------------------\n");
            }

            JTextArea textArea =
                    new JTextArea(message.toString());

            textArea.setEditable(false);

            JScrollPane scrollPane =
                    new JScrollPane(textArea);

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "My Bookings",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Cancel Ticket
        cancelTicketButton.addActionListener(e -> {

            String bookingIdInput =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter booking ID to cancel:"
                    );

            if (bookingIdInput == null ||
                    bookingIdInput.trim().isEmpty()) {
                return;
            }

            int bookingId;

            try {

                bookingId = Integer.parseInt(
                        bookingIdInput.trim()
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid booking ID."
                );

                return;
            }

            int confirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to cancel this ticket?",
                            "Confirm Cancellation",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }

            BookingDAO bookingDAO =
                    new BookingDAO();

            boolean success =
                    bookingDAO.cancelBooking(
                            bookingId,
                            user.getUserId()
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket cancelled successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Cancellation failed. Check booking ID."
                );
            }
        });

        // Logout
        logoutButton.addActionListener(e -> {

            new LoginGUI();
            dispose();

        });

        setVisible(true);
    }
}