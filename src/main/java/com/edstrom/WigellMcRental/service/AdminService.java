package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.BookingCreateDto;
import com.edstrom.WigellMcRental.mapper.BookingMapper;
import com.edstrom.WigellMcRental.model.Address;
import com.edstrom.WigellMcRental.model.Booking;
import com.edstrom.WigellMcRental.model.Customer;
import com.edstrom.WigellMcRental.model.Mc;
import com.edstrom.WigellMcRental.repository.AddressRepository;
import com.edstrom.WigellMcRental.repository.BookingRepository;
import com.edstrom.WigellMcRental.repository.CustomerRepository;
import com.edstrom.WigellMcRental.repository.McRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final McRepository mcRepository;
    private final BookingRepository bookingRepository;
    private final PricingService pricingService;


    public AdminService(CustomerRepository customerRepository,
                        AddressRepository addressRepository,
                        McRepository mcRepository, BookingRepository bookingRepository,
                        PricingService pricingService){
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.mcRepository = mcRepository;
        this.bookingRepository = bookingRepository;
        this.pricingService = pricingService;

    }

    @Transactional
    public void createSampleData() {

        BigDecimal rate = pricingService.getRate("SEK", "GBP");

        Address a1 = new Address( "Goa gatan 101", "907 77", "Sövlesborg");
        Address a2 = new Address( "Biltursesplanaden 44", "202 33", "Kiruna");
        Address a3 = new Address( "Stenmursvägen 1", "313 99", "Visby");
        Address a4 = new Address( "Gnuttstråket 11", "111 11", "Gläntan");
        Address a5 = new Address( "Tuffa vägen 74", "561 52", "Skurkeberg");

        addressRepository.save(a1);
        addressRepository.save(a2);
        addressRepository.save(a3);
        addressRepository.save(a4);
        addressRepository.save(a5);

        Customer c1 = new Customer( "Sverker", "Eliasson", a1,
                "Svasseman@craycraymail.com", "0701233210", LocalDate.parse("1990-05-12"));

        Customer c2 = new Customer( "Hulrika", "Larsson", a1,
                "Hullohar@gmail.com", "0735560900", LocalDate.parse("1988-11-23"));

        Customer c3 = new Customer( "TinaLina", "Larsson", a2,
                "TheLine@darkmail.com", "0706661666", LocalDate.parse("1991-02-17"));

        Customer c4 = new Customer( "Mysan", "Toffelbrink", a4,
                "MysTofflan@granmail.com", "0731442071", LocalDate.parse("2008-04-13"));

        Customer c5 = new Customer( "TassePär", "Andersson", a5,
                "Voffvoff@secretmail.com", "0700000000", LocalDate.parse("1983-09-14"));

        customerRepository.save(c1);
        customerRepository.save(c2);
        customerRepository.save(c3);
        customerRepository.save(c4);
        customerRepository.save(c5);

        Mc mc1 = new Mc("Easter", "BunnyHopper", "2009", BigDecimal.valueOf(900));
        Mc mc2 = new Mc("Cruiser", "Harley Davidson", "1998", BigDecimal.valueOf(400));
        Mc mc3 = new Mc("Cross", "Yama hama haha", "2023", BigDecimal.valueOf(1200));
        Mc mc4 = new Mc("Vespa", "RaisinEater", "2018", BigDecimal.valueOf(400));
        Mc mc5 = new Mc("Highway", "FreeFlameBird", "2025", BigDecimal.valueOf(1400));

        mcRepository.saveAll(List.of(mc1, mc2, mc3, mc4, mc5));

        Booking b1 = BookingMapper.createBooking(
                c1,
                List.of(mc1, mc5),
                new BookingCreateDto(
                        c1.getId(),
                        List.of(mc1.getId(), mc5.getId()),
                        LocalDate.of(2026, 6, 5),
                        LocalDate.of(2026, 6, 10),
                        BigDecimal.valueOf(13800)
                )
        );
        // GBP
        b1.setTotalPriceGbp(
                b1.getTotalPrice().multiply(rate)
        );
        Booking b2 = BookingMapper.createBooking(
                c4,
                List.of(mc2, mc3),
                new BookingCreateDto(
                        c4.getId(),
                        List.of(mc2.getId(), mc3.getId()),
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 3),
                        BigDecimal.valueOf(4800)
                )
        );
        // GBP
        b2.setTotalPriceGbp(
                b2.getTotalPrice().multiply(rate)
        );
        bookingRepository.saveAll(List.of(b1, b2));
    }
}



