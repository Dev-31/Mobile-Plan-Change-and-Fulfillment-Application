package com.prodapt.mobileplan.service;

import com.prodapt.mobileplan.dto.request.CheckoutRequest;
import com.prodapt.mobileplan.dto.response.CheckoutResponse;

public interface CheckoutService {

    CheckoutResponse checkout(CheckoutRequest request);
}
