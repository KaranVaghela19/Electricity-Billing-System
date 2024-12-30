package Electricity.Billing.System.Controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Electricity.Billing.System.Model.Bill;
import Electricity.Billing.System.Model.Customer;
import Electricity.Billing.System.Repository.BillRepository;
import Electricity.Billing.System.Service.BillService;
import Electricity.Billing.System.Service.CustomerService;
import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BillService billService;
    
    

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }
    
    @GetMapping("/add-customer")
    public String showAddCustomerForm() {
        return "add-customer"; // Renders add-customer.html
    } 

    
    
    @GetMapping("/customer-list")
    public String viewCustomerList(Model model) {
        List<Customer> customers = customerService.getAllCustomers(); // Fetch customers from the database
        model.addAttribute("customers", customers); // Add customers to the model
        return "add-customer"; // Return the customer list view
    }
    
    
    @GetMapping("/delete-customer/{id}")
    public String deleteCustomerById(@PathVariable("id") int id) {
        // Perform deletion in the service
        customerService.deleteCustomerById((long)id);
        // Redirect back to the customer list page
        return "redirect:/admin/customers";
    }

	  
    @GetMapping("/editid/{id}")
    public String showEditPage(@PathVariable("id") Long id, Model model) {
        // Retrieve the customer from the service using the id
    	Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);  // Pass the customer to the new page
        return "Edit";  // Return the new HTML page for editing
    }


    @PutMapping("/editid/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute  Customer customer) {
        customer.setId(id);  // Set the customer ID (in case it's missing or not bound in the form)
        customerService.updateCustomer(id, customer);  // Call the update method in the service
        return "redirect:/admin/customers";  // Redirect back to the customer list page after the update
    }	
    
    @PostMapping("/add-customer")
    public String addOrUpdateCustomer( @Validated @ModelAttribute Customer customer, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            // Log the validation errors
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Error: " + error.getDefaultMessage());
            });
            
            // Return to the form if there are errors
           // return "add-customer";  // The name of your form page (view)
        }
    	
    	try {
            if (customer.getId() == null || customer.getId() <= 0) {
                // Call a service method to add the customer
                customerService.addCustomer(customer);
            } else {
                // Call a service method to update the customer
                customerService.updateCustomer(customer.getId(), customer);
            }
        } catch (EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            // Redirect to an error page or handle it gracefully
            throw ex; // Optionally, handle using a global exception handler
        }

        return "redirect:/admin/customers";
    }
    
    
    ////// For Bills
    
    @GetMapping("/bill-details")
    public String showAddBillForm() {
        return "Billform"; // Renders bill.html
    }
    
  
    @GetMapping("/bill-list")
    public ModelAndView getallbill() {
    	List<Bill>list=billService.getallbill();
		ModelAndView m = new ModelAndView()  ;
		m.setViewName("bill-list");
		m.addObject("bill",list);
		return m;
		
    	
    }
    
    
    // @PostMapping("/add-customer")
    // public String addCustomer(Customer customer) {
    //      customerService.addCustomer(customer);
    //      return "redirect:/admin/customers";
    //   }
    
   // @PostMapping("/bills")
   ///public String billlist(@ModelAttribute Bill s) {
  // 	billService.saveBill(s);
	//	return "redirect:/admin/bill-list";
   	
   // }
    
    @PostMapping("/bills")
    public String createBill(@ModelAttribute Bill bill, 
                             @RequestParam("customerId") Long customerId, 
                             BindingResult bindingResult, 
                             Model model) {
        // Validate the customer ID
        Customer customer = customerService.findById(customerId);
        if (customer == null) {
            bindingResult.rejectValue("customer", "error.customer", "Customer not found.");
            model.addAttribute("error", "Invalid customer ID.");
            return "BillForm"; // Return the form view with error message
        }

        // Assign the customer to the bill
        bill.setCustomer(customer);

        // Validate the bill object
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Error: " + error.getDefaultMessage());
            });
            return "BillForm"; // Redirect back to the form on validation errors
        }

        // Save the bill
        billService.saveBill(bill);
        return "redirect:/admin/bill-list"; // Redirect to the bill list page
    }


  
    
   
}
