package Electricity.Billing.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Electricity.Billing.System.Model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {}
