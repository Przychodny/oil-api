package com.example.oil_api.services;

import com.example.oil_api.mappers.PickupMapper;
import com.example.oil_api.models.command.CreatePickupCommand;
import com.example.oil_api.models.dto.PickupDto;
import com.example.oil_api.models.entities.Client;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Invoice;
import com.example.oil_api.models.entities.Pickup;
import com.example.oil_api.models.entities.WasteTransferCard;
import com.example.oil_api.repositories.ClientRepository;
import com.example.oil_api.repositories.PickupRepository;
import com.example.oil_api.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PickupMapper pickupMapper;
    private final DocumentGenerationService documentGenerationService;


    @Transactional
    public PickupDto create(int driverId, CreatePickupCommand command) {
        Driver driver = (Driver) userRepository.findWithLockingById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        Client client = clientRepository.findById(command.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Pickup pickup = pickupMapper.mapFromCommand(command);

        BigDecimal netTotal = command.getNetPricePerKg().multiply(command.getKg());
        BigDecimal vat = netTotal.multiply(new BigDecimal("0.23")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal grossTotal = netTotal.add(vat);

        driver.setBalance(driver.getBalance().subtract(grossTotal));

        pickup.setDriver(driver);
        pickup.setClient(client);
        pickup.setPickupTime(LocalDateTime.now());
        pickup.setNetPricePerKg(command.getNetPricePerKg());
        pickup.setKg(command.getKg());
        pickup.setNetTotal(netTotal);
        pickup.setVat(vat);
        pickup.setGrossTotal(grossTotal);

        Invoice invoice = documentGenerationService.createInvoice(driver, client, pickup);
        WasteTransferCard wasteCard = documentGenerationService.createWasteCard(driver, client, pickup);

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
}
