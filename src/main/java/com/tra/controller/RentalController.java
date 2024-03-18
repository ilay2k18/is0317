import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class RentalController {

    @PostMapping("/checkout")
    public RentalAgreement checkoutTool(@RequestBody CheckoutRequest request) {
        if (request.getRentalDays() < 1 || request.getDiscountPercent() < 0 || request.getDiscountPercent() > 100) {
            throw new IllegalArgumentException("Invalid rental parameters");
        }

        Tool tool = getToolByCode(request.getToolCode());

        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setToolCode(tool.getToolCode());
        rentalAgreement.setToolType(tool.getToolType());
        rentalAgreement.setToolBrand(tool.getBrand());
        rentalAgreement.setRentalDays(request.getRentalDays());
        rentalAgreement.setCheckoutDate(LocalDate.now());
        rentalAgreement.setDueDate(LocalDate.now().plusDays(request.getRentalDays()));

        rentalAgreement.setDailyRentalCharge(tool.getDailyCharge());
        rentalAgreement.setChargeDays(tool.calculateCharge(rentalAgreement.getCheckoutDate(), rentalAgreement.getRentalDays()));

        rentalAgreement.setPreDiscountCharge(rentalAgreement.getChargeDays());
        rentalAgreement.setDiscountPercent(request.getDiscountPercent());
        rentalAgreement.setDiscountAmount(rentalAgreement.getPreDiscountCharge() * request.getDiscountPercent() / 100);
        rentalAgreement.setFinalCharge(rentalAgreement.getPreDiscountCharge() - rentalAgreement.getDiscountAmount());

        return rentalAgreement;
    }

    private Tool getToolByCode(String toolCode) {
        // Implement tool retrieval logic
        return null;
    }
}
