package in.timesinternet.foodbooking.contoller.customer;

import in.timesinternet.foodbooking.dto.request.CategoryUpdateDto;
import in.timesinternet.foodbooking.entity.Category;
import in.timesinternet.foodbooking.entity.Coupon;
import in.timesinternet.foodbooking.entity.Item;
import in.timesinternet.foodbooking.entity.Serviceability;
import in.timesinternet.foodbooking.service.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping(value = "/api/customer/restaurant")
public class CustomerRestaurantController {


    @Autowired
    ItemService itemService;
    @Autowired
    CouponService couponService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    PincodeService pincodeService;

    @GetMapping("/{restaurantId}/coupon")
    ResponseEntity<List<Coupon>> getAllCoupon(@PathVariable Integer restaurantId)
    {

        return ResponseEntity.ok(couponService.getAllCoupon(restaurantId));
    }


        @GetMapping("/{restaurantId}/category")
        ResponseEntity<List<Category>> getAllCategory(@PathVariable Integer restaurantId)
        {

        return ResponseEntity.ok(categoryService.getAllCategory(restaurantId));
    }
    @GetMapping("/{restaurantId}/item")
    ResponseEntity<List<Item>> getMenu(@PathVariable Integer restaurantId)
    {

        return ResponseEntity.ok(itemService.getAllItem(restaurantId));
    }

    @GetMapping("/category/{categoryId}/item")
    ResponseEntity<List<Item>> getItem(@PathVariable Integer categoryId)
    {

        return ResponseEntity.ok(itemService.getItemByCategory(categoryId));
    }

    @GetMapping("/{restaurantId}/pincode")
    ResponseEntity<List<Serviceability>> getServiceablePincode(@PathVariable Integer restaurantId)
    {
        return ResponseEntity.ok(pincodeService.getPincodeList(restaurantId));
    }
}
