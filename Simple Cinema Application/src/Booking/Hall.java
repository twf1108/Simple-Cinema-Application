package Booking;
public class Hall {
    private String hallNo;
    
    public Hall(){}
    public Hall(String hallNo){
        this.hallNo = hallNo;
    }
    
    public String getHallNo(){
        return this.hallNo;
    }
    public void setHallNo(String hallNo){
        this.hallNo = hallNo;
    }    
}
