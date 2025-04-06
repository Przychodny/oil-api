package com.example.oil_api.services;

import com.example.oil_api.models.entities.Client;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentGenerationService {

    private final InvoiceRepository invoiceRepository;
    private final WasteTransferCardRepository wasteTransferCardRepository;
    private final UserRepository userRepository;

    public Invoice createInvoice(Driver driver, Client client, Pickup pickup) {
        Invoice invoice = new Invoice();

        invoice.setClient(client);
        invoice.setNumber(generateInvoiceNumber(driver.getId()));
        invoice.setDate(LocalDate.now());
        invoice.setNetPricePerKg(pickup.getNetPricePerKg());
        invoice.setKg(pickup.getKg());
        invoice.setNetTotal(pickup.getNetTotal());
        invoice.setVat(pickup.getVat());
        invoice.setGrossTotal(pickup.getGrossTotal());

        return invoiceRepository.save(invoice);
    }

    public WasteTransferCard createWasteCard(Driver driver, Client client, Pickup pickup) {
        WasteTransferCard wasteTransferCard = new WasteTransferCard();
        //czemu Ty uzywasz wszedzie bezargumentowego konstruktora i potem przez settery wszystko ustawiasz?
        //tak sie nie robi, przynajmniej ja sie nie spotkalem

        BigDecimal weight = pickup.getKg().multiply(new BigDecimal("0.001")).setScale(3, RoundingMode.HALF_UP);
        wasteTransferCard.setClient(client);
        wasteTransferCard.setNumber(generateWasteTransferCardNumber(driver.getId()));
        wasteTransferCard.setWeightMg(weight);
        wasteTransferCard.setDate(LocalDate.now());
        wasteTransferCard.setDriverCarRegistration(driver.getCar().getRegistration());

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

        String firstLetter = String.valueOf(driver.getFirstName().toUpperCase().charAt(0)); //metoda
        String lastLetter = String.valueOf(driver.getLastName().toUpperCase().charAt(0));
        String numberOfInvoice = String.valueOf(driver.getPickups().size() + 1);
        String year = String.valueOf(LocalDateTime.now().getYear());

        if (extraSegment == null || extraSegment.isEmpty()) {
            return firstLetter + lastLetter + "/" + numberOfInvoice + "/" + year;
        }

        return firstLetter + lastLetter + "/" + numberOfInvoice + "/" + extraSegment + year; //musisz byc konsekwentny w projekcie
        //albo uzywasz string format albo uzywasz z plusami. Inaczej wyglada jakbys mial dwubiegunowke
        //z doswiadczenia powiem, ze uzywaj string format/formatted
    }
}
