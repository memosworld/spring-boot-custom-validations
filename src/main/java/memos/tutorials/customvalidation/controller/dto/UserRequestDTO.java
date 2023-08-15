package memos.tutorials.customvalidation.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import memos.tutorials.customvalidation.controller.validation.AtLeastOneNotBlank;
import memos.tutorials.customvalidation.controller.validation.ExcludedNumbers;
import memos.tutorials.customvalidation.controller.validation.Fibonacci;
import memos.tutorials.customvalidation.controller.validation.IntegerValues;

@Data
@NoArgsConstructor
@AtLeastOneNotBlank(fields = {"id", "passport", "drivingLicence"})
public class UserRequestDTO {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String id;

    private String passport;

    private String drivingLicence;

    @ExcludedNumbers(excludedNumbers = {4, 9, 13, 17, 39, 43, 666})
    private Integer luckyNumber;

    @NotNull
    @IntegerValues(values = {2, 4, 8, 16, 32})
    private Integer maxDataSize;

    @Fibonacci
    private Long fibonacci;
}
