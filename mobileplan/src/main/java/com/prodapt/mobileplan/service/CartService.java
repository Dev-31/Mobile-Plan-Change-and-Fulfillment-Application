package com.prodapt.mobileplan.service;

import com.prodapt.mobileplan.dto.request.AddToCartRequest;
import com.prodapt.mobileplan.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(AddToCartRequest request);

    CartResponse getCart(Long userId);

    void clearCart(Long userId);
}
