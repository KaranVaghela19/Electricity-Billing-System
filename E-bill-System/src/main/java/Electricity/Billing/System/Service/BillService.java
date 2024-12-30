package Electricity.Billing.System.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Electricity.Billing.System.Model.Bill;
import Electricity.Billing.System.Repository.BillRepository;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

  
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill); // Save the bill to the database
    }
    
    public List<Bill> getallbill(){
		return billRepository.findAll();
    	
    }

	
    
}

