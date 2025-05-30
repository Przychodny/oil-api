package com.example.oil_api.services;

import com.example.oil_api.mappers.InvoiceMapper;
import com.example.oil_api.mappers.PickupMapper;
import com.example.oil_api.mappers.WasteTransferCardMapper;
import com.example.oil_api.models.command.create.CreatePickupCommand;
import com.example.oil_api.models.dto.InvoiceDto;
import com.example.oil_api.models.dto.PickupDto;
import com.example.oil_api.models.dto.WasteTransferCardDto;
import com.example.oil_api.models.entities.Client;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Invoice;
import com.example.oil_api.models.entities.Pickup;
import com.example.oil_api.models.entities.WasteTransferCard;
import com.example.oil_api.repositories.ClientRepository;
import com.example.oil_api.repositories.DriverRepository;
import com.example.oil_api.repositories.PickupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PickupService {

    private final PickupRepository pickupRepository;
    private final ClientRepository clientRepository;
    private final PickupMapper pickupMapper;
    private final DocumentGenerationService documentGenerationService;
    private final DriverRepository driverRepository;
    private final InvoiceMapper invoiceMapper;
    private final WasteTransferCardMapper wasteTransferCardMapper;

    @Transactional
    public PickupDto create(int driverId, CreatePickupCommand command) {
        Driver driver = driverRepository.findWithLockingById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        Client client = clientRepository.findById(command.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        BigDecimal netTotal = calculateNetTotal(command);
        BigDecimal vat = calculateVat(netTotal);
        BigDecimal grossTotal = netTotal.add(vat);

        Pickup pickup = pickupMapper.mapToEntity(command, driver, client, netTotal, vat, grossTotal);
        pickup.setPickupTime(LocalDateTime.now());

        if (driver.getBalance().compareTo(grossTotal) < 0) {
            throw new LayerInstantiationException();
        }
        driver.setBalance(driver.getBalance().subtract(grossTotal));

        Invoice invoice = documentGenerationService.createInvoice(driver, pickup);
        WasteTransferCard wasteCard = documentGenerationService.createWasteCard(driver, pickup);

        pickup.setInvoice(invoice);
        pickup.setWasteTransferCard(wasteCard);

        return pickupMapper.mapToDto(pickupRepository.save(pickup));
    }

    public Page<PickupDto> getAllByDriver(int driverId, Pageable pageable) {
        return pickupRepository.findAllByDriverId(driverId, pageable)
                .map(pickupMapper::mapToDto);
    }

    public Page<PickupDto> getAll(Pageable pageable) {
        return pickupRepository.findAll(pageable)
                .map(pickupMapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public InvoiceDto getInvoiceByPickupId(int pickupId) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new EntityNotFoundException("Pickup not found"));

        return invoiceMapper.mapToDto(pickup.getInvoice());
    }

    @Transactional(readOnly = true)
    public WasteTransferCardDto getWasteCardByPickupId(int pickupId) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new EntityNotFoundException("Pickup not found"));

        return wasteTransferCardMapper.mapToDto(pickup.getWasteTransferCard());
    }

    private BigDecimal calculateVat(BigDecimal netTotal) {
        return netTotal.multiply(new BigDecimal("0.23")).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateNetTotal(CreatePickupCommand command) {
        return command.getNetPricePerKg().multiply(command.getKg());
    }
}
