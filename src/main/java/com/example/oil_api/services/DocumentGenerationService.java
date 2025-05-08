package com.example.oil_api.services;

import com.example.oil_api.mappers.InvoiceMapper;
import com.example.oil_api.mappers.WasteTransferCardMapper;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Invoice;
import com.example.oil_api.models.entities.Pickup;
import com.example.oil_api.models.entities.WasteTransferCard;
import com.example.oil_api.repositories.InvoiceRepository;
import com.example.oil_api.repositories.UserRepository;
import com.example.oil_api.repositories.WasteTransferCardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentGenerationService {

    private final InvoiceRepository invoiceRepository;
    private final WasteTransferCardRepository wasteTransferCardRepository;
    private final UserRepository userRepository;
    private final InvoiceMapper invoiceMapper;
    private final WasteTransferCardMapper wasteTransferCardMapper;

    public Invoice createInvoice(Driver driver, Pickup pickup) {
        Invoice invoice = invoiceMapper.mapFromPickup(pickup);
        invoice.setNumber(generateInvoiceNumber(driver.getId()));
        return invoiceRepository.save(invoice);
    }

    public WasteTransferCard createWasteCard(Driver driver, Pickup pickup) {
        WasteTransferCard wasteTransferCard = wasteTransferCardMapper.mapFromPickup(pickup, driver);

        wasteTransferCard.setWeightMg(getWeight(pickup));
        wasteTransferCard.setNumber(generateWasteTransferCardNumber(driver.getId()));

        return wasteTransferCardRepository.save(wasteTransferCard);
    }

    private String generateInvoiceNumber(int driverId) {
        return generateDocumentNumber(driverId, "");
    }

    private String generateWasteTransferCardNumber(int driverId) {
        return generateDocumentNumber(driverId, "S/");
    }

    private String generateDocumentNumber(int driverId, String extraSegment) {
        Driver driver = (Driver) userRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        String firstLetter = getFirstLetter(driver.getFirstName());
        String lastLetter = getFirstLetter(driver.getLastName());
        String numberOfInvoice = String.valueOf(driver.getPickups().size() + 1);
        String year = String.valueOf(LocalDateTime.now().getYear());

        if (extraSegment == null || extraSegment.isEmpty()) {
            return String.format("%s%s/%s/%s", firstLetter, lastLetter, numberOfInvoice, year);
        }
        return String.format("%s%s/%s/%s%s", firstLetter, lastLetter, numberOfInvoice, extraSegment, year);
    }

    private BigDecimal getWeight(Pickup pickup) {
        return pickup.getKg().multiply(new BigDecimal("0.001")).setScale(3, RoundingMode.HALF_UP);
    }

    private String getFirstLetter(String string) {
        return String.valueOf(string.toUpperCase().charAt(0));
    }
}
