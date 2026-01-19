package com.prodapt.mobileplan.service.impl;

import java.time.LocalDateTime;

import com.prodapt.mobileplan.service.PromotionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.mobileplan.dto.request.AddToCartRequest;
import com.prodapt.mobileplan.dto.response.CartResponse;
import com.prodapt.mobileplan.entity.Cart;
import com.prodapt.mobileplan.entity.CartItem;
import com.prodapt.mobileplan.repository.CartRepository;
import com.prodapt.mobileplan.repository.PlanRepository;
import com.prodapt.mobileplan.repository.UserRepository;
import com.prodapt.mobileplan.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final PromotionService promotionService;

    public CartServiceImpl(
            CartRepository cartRepository,
            UserRepository userRepository,
            PlanRepository planRepository,
            PromotionService promotionService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.promotionService = promotionService;
    }

    @Override
    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        int validity =
                request.getValidityInDays() != null
                        ? request.getValidityInDays()
                        : plan.getValidityInDays();

        double pricePerDay = plan.getPrice() / plan.getValidityInDays();
        double finalPrice = Math.round(pricePerDay * validity);

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepository.save(c);
                });

        cart.getItems().clear();

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setPlan(plan);
        item.setValidityInDays(validity);
        item.setCalculatedPrice(finalPrice);

        cart.getItems().add(item);
        cart.setUpdatedAt(LocalDateTime.now());

        return map(cartRepository.save(cart));
    }

    @Override
    public CartResponse getCart(Long userId) {

        return cartRepository.findByUserId(userId)
                .map(this::map)
                .orElseGet(() -> new CartResponse()); // ✅ EMPTY CART
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        cartRepository.findByUserId(userId)
                .ifPresent(cartRepository::delete);
    }

    private CartResponse map(Cart cart) {

        CartResponse res = new CartResponse();
        res.setCartId(cart.getId());

        if (cart.getItems().isEmpty()) {
            return res;
        }

        CartItem item = cart.getItems().get(0);

        double baseAmount = item.getCalculatedPrice();

        double payableAmount;
        try {
            payableAmount = promotionService.applyPromotion(
                    null,
                    baseAmount
            );
        } catch (Exception e) {
            payableAmount = baseAmount;
        }

        double discount = baseAmount - payableAmount;

        res.setPlanNames(
                cart.getItems()
                        .stream()
                        .map(i -> i.getPlan().getName())
                        .toList()
        );

        res.setValidityInDays(item.getValidityInDays());
        res.setBaseAmount(baseAmount);
        res.setDiscount(discount);
        res.setPayableAmount(payableAmount);

        return res;
    }
}
