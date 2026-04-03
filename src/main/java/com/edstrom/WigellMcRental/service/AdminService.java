package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.AddressCreateDto;
import com.edstrom.WigellMcRental.dto.AddressDto;
import com.edstrom.WigellMcRental.dto.CustomerCreateDto;
import com.edstrom.WigellMcRental.dto.CustomerDto;
import com.edstrom.WigellMcRental.exception.AddressNotFoundException;
import com.edstrom.WigellMcRental.exception.CustomerAndAddressNoMatchException;
import com.edstrom.WigellMcRental.exception.CustomerNotFoundException;
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
public class AdminService {


    // Borde logging också injiceras plus hur ska den se ut med meddelanden på create, update, delete
    //private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public AdminService(CustomerRepository customerRepository, AddressRepository addressRepository){
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
        Customer entityCustomer = CustomerMapper.toCustomerEntity(dto, addressRepository);
        Customer savedCustomer = customerRepository.save(entityCustomer);
        return CustomerMapper.toCustomerDto(savedCustomer);
    }


    @Transactional
    public AddressDto createAddress(AddressCreateDto dto) {
        Address entityAddress = AddressMapper.toAddressEntity(dto);
        Address savedAddress = addressRepository.save(entityAddress);
        return AddressMapper.toAddressDto(savedAddress);
    }
    @Transactional
    public CustomerDto customerUpdate(Long id, CustomerUpdateDto dto)




    @Transactional
    public void customerDelete(Long id) {
        if(!customerRepository.existsById(id)){
            throw new CustomerNotFoundException(id);
        }
       customerRepository.deleteById(id);
    }
    @Transactional
    public void addressDelete(Long customerId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow (() -> new AddressNotFoundException(addressId));

                boolean matchCustomer = address.getCustomers().stream()
                        .anyMatch(c -> c.getId().equals(customerId));

                if (!matchCustomer) {
                    throw new CustomerAndAddressNoMatchException(customerId);
                }
                for (Customer c : address.getCustomers()) {
                    c.setAddress(null);
        }
                addressRepository.delete(address);
    }

    @Transactional
    public void createSampleData(){


    }

}
