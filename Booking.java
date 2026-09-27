public class Booking {

    private int bookingId;
    private int userId;
    private int trainId;
    private String passengerName;
    private int age;
    private String gender;
    private int seatNumber;
    private String bookingDate;
    private String bookingStatus;

    public Booking(int bookingId, int userId, int trainId,
                   String passengerName, int age, String gender,
                   int seatNumber, String bookingDate,
                   String bookingStatus) {

        this.bookingId = bookingId;
        this.userId = userId;
        this.trainId = trainId;
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getTrainId() {
        return trainId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }
}