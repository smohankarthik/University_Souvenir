package sg.edu.nus.iss.store;

public class Member extends Customer{
	private String memberID;
	private int loyaltyPoints;
	
	public Member(String customerName, String memberID) {
		super(customerName);
		this.memberID = memberID;
		loyaltyPoints = -1;
	}
	public Member(String customerName, String memberID, int loyaltyPoints) {
		super(customerName);
		this.memberID = memberID;
		this.loyaltyPoints = loyaltyPoints;
	}
	
	
	public String getMemberID() {
		return memberID;
	}
	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}
	public void updateLoyaltyPoints(int points){
		if (isFirstTime())
		loyaltyPoints = points;
		else loyaltyPoints += points;
	}
	public boolean isFirstTime(){
		if(loyaltyPoints < 0)
		return true;
		return false;
	}
	

}
