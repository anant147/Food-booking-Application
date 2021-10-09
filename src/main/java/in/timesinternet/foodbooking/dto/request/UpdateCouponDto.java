package in.timesinternet.foodbooking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCouponDto {

    @NotNull(message = "name can't be null")
    private String name;
    @NotNull(message = "value can't be null")
    private Integer value;

    @JsonFormat( pattern = "dd-MM-yyyy")
    @NotNull(message = "Starting date can't be null")
    private Date startingDate;

    @JsonFormat( pattern = "dd-MM-yyyy")
    @NotNull(message = "Ending date can't be null")
    private Date endingDate;

    @NotNull(message = "Cart can't be null")
    private Integer minimumCartValue;
    @NotNull(message = "Max Discount can't be null")
    private Integer maxDiscount;
    @NotNull(message = "Maxperuser can't be null")
    private Integer maxPerUser;
    @NotNull(message = "total use can't be null")
    private Integer totalUse;
    @NotNull(message = "terms and conditions can't be null")
    private String termsAndCondition;
    @NotNull(message = "ImageId can't be null")
    private Integer imageId;


}
