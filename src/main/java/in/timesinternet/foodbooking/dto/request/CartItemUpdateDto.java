package in.timesinternet.foodbooking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemUpdateDto {

    Integer quantity;
    Integer cartItemId;

}
