package memos.tutorials.customvalidation.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import memos.tutorials.customvalidation.controller.validation.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AtLeastOneNotBlank(fields = {"id", "passport", "drivingLicence"})
@ConditionalMandatory(field = "type", values = "BUSINESS", requires = "companyName")
@ConditionalMandatory(field = "type", values = "PERSONAL", requires = {"firstName", "lastName"})
public class UserRequestDTO {
    public enum AccountType {
        PERSONAL, BUSINESS
    }

    @NotNull
    private AccountType type;

    private String firstName;

    private String lastName;

    private String companyName;

    private String id;

    private String passport;

    private String drivingLicence;

    @Age(min = 18, max = 65)
    private LocalDate birthDate;

    @ISO3166CountryCode
    private String country;

    @ExcludedNumbers(excludedNumbers = {4, 9, 13, 17, 39, 43, 666})
    private Integer luckyNumber;

    @NotNull
    @IntegerValues(values = {2, 4, 8, 16, 32})
    private Integer maxDataSize;

    @Fibonacci
    private Long fibonacci;

    @Min(value = 1)
    @Max(value = 12)
    @ExcludedNumbers(excludedNumbers = {6, 9})
    private Integer combinedValidation;

    @Min(3)
    @DivisibleBy(divider = 3)
    private Integer subscriptionDuration;
}
