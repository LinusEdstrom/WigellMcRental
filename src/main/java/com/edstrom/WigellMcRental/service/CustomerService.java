package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.*;
import com.edstrom.WigellMcRental.exception.AddressNotFoundException;
import com.edstrom.WigellMcRental.exception.CustomerAndAddressNoMatchException;
import com.edstrom.WigellMcRental.exception.CustomerNotFoundException;
import com.edstrom.WigellMcRental.exception.EmailAlreadyExistsException;
import com.edstrom.WigellMcRental.mapper.AddressMapper;
import com.edstrom.WigellMcRental.mapper.CustomerMapper;
import com.edstrom.WigellMcRental.modell.Address;
import com.edstrom.WigellMcRental.modell.Customer;
import com.edstrom.WigellMcRental.repository.AddressRepository;
import com.edstrom.WigellMcRental.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {


        // Borde logging också injiceras plus hur ska den se ut med meddelanden på create, update, delete
        //private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

        private final CustomerRepository customerRepository;
        private final AddressRepository addressRepository;

        public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository){
            this.customerRepository = customerRepository;
            this.addressRepository = addressRepository;
        }

        @Transactional(readOnly = true)
        public List<CustomerDto> findAllCustomers() {
            return customerRepository.findAll().stream()
                    .map(CustomerMapper::toCustomerDto)
                    .toList();
        }
        @Transactional(readOnly = true)
        public CustomerDto findByCustomerId(Long id) {
            return customerRepository.findById(id)
                    .map(CustomerMapper::toCustomerDto)
                    .orElseThrow (() -> new CustomerNotFoundException(id));
        }
        @Transactional
        public CustomerDto createCustomer(CustomerCreateDto dto) {

            if(customerRepository.existsByEmail(dto.email())){
                throw new EmailAlreadyExistsException(dto.email());
            }

            Customer entityCustomer = CustomerMapper.toCustomerEntity(dto, addressRepository);
            Customer savedCustomer = customerRepository.save(entityCustomer);
            return CustomerMapper.toCustomerDto(savedCustomer);
        }

        @Transactional
        public CustomerDto updateCustomer(Long id, CustomerUpdateDto dto){

            Customer customer = customerRepository.findById(id)
                    .orElseThrow(()-> new CustomerNotFoundException(id));

            if (customerRepository.existsByEmail(dto.email()) &&
                    !customer.getEmail().equals(dto.email())) {
                throw new EmailAlreadyExistsException(dto.email());
            }

            CustomerMapper.applyUpdate(customer, dto);
            customerRepository.save(customer);
            return CustomerMapper.toCustomerDto(customer);
        }



        @Transactional
        public void customerDelete(Long id) {
            if(!customerRepository.existsById(id)){
                throw new CustomerNotFoundException(id);
            }
            customerRepository.deleteById(id);
        }

}
