package com.foodorder.user.service;

import com.foodorder.user.dto.request.CustomerRegistrationRequest;
import com.foodorder.user.dto.request.DeliveryAgentRegistrationRequest;
import com.foodorder.user.dto.request.EmployeeRegistrationRequest;
import com.foodorder.user.dto.request.RestaurantAdminRegistrationRequest;
import com.foodorder.user.dto.response.Response;

public interface UserService {

    Response registerCustomer(CustomerRegistrationRequest request);
    Response registerDeliveryAgent(DeliveryAgentRegistrationRequest request);
    Response registerRestaurantAdmin(RestaurantAdminRegistrationRequest request);
    Response registerEmployee(EmployeeRegistrationRequest request);

}
