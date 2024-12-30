package Electricity.Billing.System.Model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int unitsConsumed;
    
    @Temporal(TemporalType.DATE)
    private Date month; // The month of billing

    @Temporal(TemporalType.DATE)
    private Date dueDate; // Due date for the payment
    
    private double totalAmount; // Total payment amount

    private String status; // Payment status: Paid / Unpaid

   
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Reference to the Customer table

    public Bill() {}
    

	public Bill(Long id, int unitsConsumed, Date month, Date dueDate, double totalAmount, String status,
			Customer customer) {
		super();
		this.id = id;
		this.unitsConsumed = unitsConsumed;
		this.month = month;
		this.dueDate = dueDate;
		this.totalAmount = totalAmount;
		this.status = status;
		this.customer = customer;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getUnitsConsumed() {
		return unitsConsumed;
	}


	public void setUnitsConsumed(int unitsConsumed) {
		this.unitsConsumed = unitsConsumed;
	}


	public Date getMonth() {
		return month;
	}


	public void setMonth(Date month) {
		this.month = month;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
    

    
}

