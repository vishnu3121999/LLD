import java.time.LocalDateTime;

public class Floor {

    private boolean upButtonActive;
    private boolean downButtonActive;

    private LocalDateTime upButtonClickTime;
    private LocalDateTime downButtonClickTime;

    public Floor(boolean downButtonActive, LocalDateTime downButtonClickTime, boolean upButtonActive, LocalDateTime upButtonClickTime) {
        this.downButtonActive = downButtonActive;
        this.downButtonClickTime = downButtonClickTime;
        this.upButtonActive = upButtonActive;
        this.upButtonClickTime = upButtonClickTime;
    }

    void pressUp(){
        upButtonActive=true;
        upButtonClickTime = LocalDateTime.now();
    }

    void pressDown(){
        downButtonActive=true;
        downButtonClickTime=LocalDateTime.now();
    }

}
