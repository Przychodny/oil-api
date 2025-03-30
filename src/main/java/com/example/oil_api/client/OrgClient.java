package com.example.oil_api.client;

import com.example.oil_api.models.dto.OrgClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "oil-api", url = "https://rejestr.io/api/v2/org", configuration = FeignClientConfig.class)
public interface OrgClient {

    @GetMapping("/nip{nip}")
    OrgClientDto getOrganizationData(@PathVariable String nip);
}
