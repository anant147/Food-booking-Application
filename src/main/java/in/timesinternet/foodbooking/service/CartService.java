package in.timesinternet.foodbooking.service;

import in.timesinternet.foodbooking.dto.request.ApplyCouponResponseDto;
import in.timesinternet.foodbooking.dto.request.CartDto;
import in.timesinternet.foodbooking.dto.request.CartItemUpdateDto;
import in.timesinternet.foodbooking.entity.Cart;
import in.timesinternet.foodbooking.entity.CartItem;
import in.timesinternet.foodbooking.entity.enumeration.CartStatus;

public interface CartService {

    Cart updateCart(CartDto cartDto, String email);

    Cart getCurrentCart(String email);

    Cart updateCartStatus(CartStatus cartStatus, String email);

    Object applyCouponOnCart(Integer cartId, String email);

    Cart updateCartItemQuantity(CartItemUpdateDto cartItemUpdateDto, String userEmail);

    Cart addItemToCart(Integer itemId, String userEmail);

    Cart deleteCartItem(Integer cartItemId, String userEmail);

    Cart addNewCart(String userEmail);

    ApplyCouponResponseDto addCouponOnCurrentCart(String email, String couponName);
}
