package pl.sda.borat.projekt_koncowy.exeception;

public class MeetingDoesntExistException extends RuntimeException {
    private Long meetingId;

    public MeetingDoesntExistException(Long meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public String getMessage() {
        return String.format("Meeting id: %d doesn't exist", meetingId);
    }
}
