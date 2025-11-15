package ticket;

import seat.Seat;
import showtime.Showtime;

public class Ticket {
    private Seat seat;
    private Showtime showtime;

    Ticket(Seat seat, Showtime showtime) {
        this.seat = seat;
        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public Showtime getShowtime() {
        return showtime;
    }
}
