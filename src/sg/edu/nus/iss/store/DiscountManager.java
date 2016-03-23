package sg.edu.nus.iss.store;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
 * DiscountManager class: Manager class to manage Discount Objects.
 * Author: Sanskar Deepak
 */
public class DiscountManager {
	private ArrayList<Discount> discounts;
	private Date dNow;
	private SimpleDateFormat ft;

	public DiscountManager() {
		discounts = new ArrayList<Discount> ();		
		dNow = new Date();
		ft = new SimpleDateFormat("yyyy-mm-dd");
		ft.format(dNow);
	}

	public boolean addDiscount(String discountCode, String description, float percentage, String startDate, String discountPeriod) {
			for(Discount d : discounts){
				if(d.getDiscountCode().equalsIgnoreCase(discountCode)){
					return false;
				}
			} 
		
		if (startDate == "")
			discounts.add(new MemberDiscount(discountCode, description, percentage));
		else
			discounts.add(new OccasionalDiscount(discountCode, description, startDate, discountPeriod, percentage));
		return true;
	}

	public Discount getDiscount(String discountCode){
		for (Discount d : discounts){
			if(d.getDiscountCode()==discountCode){
				return d;
			}
		}
		return null;
	}

	public float getMaxDiscount(float totalPrice) {
		return 0 ;
	}

	public float getMaxDiscount(float totalPrice, Member member) {
		float mDiscount = calculateMemberDiscount(totalPrice, member);
		float oDiscount = calculateOccasionalDiscount(totalPrice);

		if (mDiscount>oDiscount)
			return mDiscount;

		return oDiscount;
	}

	public float calculateMemberDiscount(float totalPrice, Member member) {
		Discount d;
		float discount;
		if(member.getLoyaltyPoints()==-1){
			d= getDiscount("MEMBER_FIRST");
			discount = totalPrice*(d.getPercentage());
			return discount;
		}
		d=getDiscount("MEMBER_SUBSEQ");
		discount = totalPrice*(d.getPercentage()/100);
		return discount;
	}

	public float calculateOccasionalDiscount(float totalPrice) {
		float amt = 0;
		for (Discount d : discounts){
			String s= d.getStartDate();
			if (d.getApplicableToMember().equalsIgnoreCase("A")){
				try {
					Date startDate = ft.parse(s);
					Calendar c = Calendar.getInstance();
					c.setTime(startDate);
					c.add(Calendar.DATE, Integer.parseInt(d.getDiscountPeriod()));
					String output = ft.format(c.getTime());
					Date endDate = ft.parse(output);
					if ((dNow.after(startDate)&&dNow.before(endDate))||dNow.equals(endDate)||dNow.equals(startDate)){
						if(totalPrice*(d.getPercentage())>amt)
						amt = totalPrice*(d.getPercentage());
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return amt;

	}

	public void modifyDiscount(String discountCode, float percentage) {
		getDiscount(discountCode).setPercentage(percentage);
		writeToFile();
	}

	public  ArrayList<Discount> getDiscounts() {
		// TODO Auto-generated method stub
		return discounts;
	}
	
	public void writeToFile() {
		try {
			BufferedWriter writer = new BufferedWriter (new FileWriter ("StoreAppData/Discounts.dat"));
			for(Discount c : discounts){
				writer.write(c.getDiscountCode()+",");
				writer.write(c.getDescription()+",");
				writer.write(c.getStartDate()+",");
				writer.write(c.getDiscountPeriod()+",");
				writer.write(c.getPercentage()+",");
				writer.write(c.getApplicableToMember()+"\n");
			}
			writer.close();
		}catch (IOException ex) {
			System.out.println("Cannot Write to file !");
			ex.printStackTrace();
		}
	}

	public void removeDiscount(String discountCode) {
		if(discounts!=null){
			for(Discount d : discounts){
				if(discountCode.compareTo(d.getDiscountCode())==0){
					discounts.remove(d);
					break;
				}
			}
		writeToFile();
		}
	}
	
}
