package in.timesinternet.foodbooking.service.impl;

import in.timesinternet.foodbooking.dto.request.CustomerDto;
import in.timesinternet.foodbooking.dto.request.CustomerUpdateDto;
import in.timesinternet.foodbooking.entity.*;
import in.timesinternet.foodbooking.dto.request.RestaurantResponseDto;
import in.timesinternet.foodbooking.entity.embeddable.RestaurantDetail;
import in.timesinternet.foodbooking.entity.enumeration.Role;
import in.timesinternet.foodbooking.exception.NotFoundException;
import in.timesinternet.foodbooking.exception.UserAlreadyExistException;
import in.timesinternet.foodbooking.repository.CartRepository;
import in.timesinternet.foodbooking.repository.CategoryRepository;
import in.timesinternet.foodbooking.repository.CustomerRepository;
import in.timesinternet.foodbooking.repository.RestaurantRepository;
import in.timesinternet.foodbooking.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import in.timesinternet.foodbooking.entity.embeddable.*;


import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CartRepository cartRepository;

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        ModelMapper modelMapper = new ModelMapper();


        Restaurant restaurant = restaurantRepository.findById(customerDto.getRestaurantId()).get();
        Customer customer = new Customer();
        customer.setAddress(customerDto.getAddress());
        customer.setContact(customerDto.getContact());
        customer.setRestaurant(restaurant);
        customer.setPassword(customerDto.getPassword());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.ROLE_CUSTOMER);
        customer.setActualEmail(customer.getEmail());
        customer.setEmail(customer.getEmail() + "_" + restaurant.getRestaurantDetail().getSubDomain());
//        if (customerDto.getAddress() != null)
//            customer.getAddressList().add(customerDto.getAddress());

        Cart cart = new Cart();
        cart.setRestaurant(restaurant);
        customer = customerRepository.save(customer);
        customer.setCurrentCart(cart);
        customer.addCart(customer.getCurrentCart());
        cartRepository.save(cart);

        try {
            customer = customerRepository.save(customer);
            return customer;
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new UserAlreadyExistException("Customer with given details :- " + customer.getEmail() + " already exits");
        }
    }

    @Override
    public RestaurantResponseDto getRestaurantDetail(String subDomain) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByRestaurantDetailSubDomain(subDomain);

        if (restaurantOptional.isPresent()) {
            RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();

            Restaurant restaurant = restaurantOptional.get();

            restaurantResponseDto.setId(restaurant.getId());
            RestaurantDetail restaurantDetail = restaurant.getRestaurantDetail();

            restaurantResponseDto.setStatus(restaurantDetail.getStatus());
            restaurantResponseDto.setOpeningTime(restaurantDetail.getOpeningTime());
            restaurantResponseDto.setClosingTime(restaurantDetail.getClosingTime());
            restaurantResponseDto.setName(restaurantDetail.getName());
            restaurantResponseDto.setEmail(restaurantDetail.getEmail());
            restaurantResponseDto.setSubDomain(restaurantDetail.getSubDomain());
            restaurantResponseDto.setAddress(restaurantDetail.getAddress());

            restaurantResponseDto.setLogo(restaurant.getLogo());

            return restaurantResponseDto;
        } else {
            throw new NotFoundException("restaurant is not found with given subdomain :- " + subDomain);
        }
    }

    @Override
    public Customer getCustomer(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            throw new RuntimeException("customer not found");
        }
    }


    @Override
    public Customer getCustomer(Integer customerId) {


        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent())
            return customerOptional.get();
        throw new RuntimeException("Customer not found with id " + customerId);
    }

    @Override
    public List<Customer> getAllCustomer(Integer restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            return restaurant.getCustomerList();
        } else
            throw new RuntimeException("restaurant not found");
    }

    @Override
    public Customer updateCustomerProfile(CustomerUpdateDto customerUpdateDto, String userEmail) {
        Customer customer = getCustomer(userEmail);
        customer.setPassword(passwordEncoder.encode(customerUpdateDto.getPassword()));
        customer.setFirstName(customerUpdateDto.getFirstName());
        customer.setLastName(customerUpdateDto.getLastName());
        customer.setAddress(customerUpdateDto.getAddress());
        customer.setContact(customerUpdateDto.getContact());
        return customerRepository.save(customer);
    }

    @Override
    public List<AddressContact> addAddress(AddressContact address, String userEmail) {
        Customer customer = getCustomer(userEmail);
        customer.getAddressList().add(address);
        customerRepository.save(customer);
        System.out.println(customer.getAddressList());
        return customer.getAddressList();
    }


    @Override
    public List<AddressContact> getAddresses(String userEmail) {
        Customer customer = getCustomer(userEmail);
        return customer.getAddressList();
    }

}
