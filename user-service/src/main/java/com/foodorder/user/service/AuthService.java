package com.foodorder.user.service;

import com.foodorder.user.constants.Role;
import com.foodorder.user.request.AuthRequest;
import com.foodorder.user.response.Response;

public interface AuthService {

    Response login(AuthRequest authRequest, Role role);

}
