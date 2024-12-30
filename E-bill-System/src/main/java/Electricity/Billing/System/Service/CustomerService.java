package Electricity.Billing.System.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Electricity.Billing.System.Model.Customer;
import Electricity.Billing.System.Repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

  
    public void deleteCustomerById(long id) {
    	Long longId = Long.valueOf(id);
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Customer with ID " + id + " not found.");
        }
    }
    
    public void updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(id);

        if (existingCustomerOptional.isPresent()) {
            // Retrieve the existing customer
            Customer existingCustomer = existingCustomerOptional.get();

            // Update only the fields you wish to change
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhone(customer.getPhone());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setConnectionType(customer.getConnectionType());

            // Save the updated customer
            customerRepository.save(existingCustomer);
        } else {
            throw new EntityNotFoundException("Customer with id " + id + " not found.");
        }
    }

	public Customer findById(Long id) {

		return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " not found."));
	}

	


}
