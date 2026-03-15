package org.example.telecomcomplainscontractservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscontractservice.dto.ContractDetailResponse;
import org.example.telecomcomplainscontractservice.dto.CustomerResponse;
import org.example.telecomcomplainscontractservice.dto.ServiceResponse;
import org.example.telecomcomplainscontractservice.entity.Contract;
import org.example.telecomcomplainscontractservice.entity.ContractService;
import org.example.telecomcomplainscontractservice.repository.ContractRepository;
import org.example.telecomcomplainscontractservice.repository.ContractServiceRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl {

    private final ContractRepository contractRepository;
    private final ContractServiceRepository
            contractServiceRepository;
    private final RestTemplate restTemplate;

    private static final String CUSTOMER_URL =
            "http://localhost:8081";

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public ContractDetailResponse getContractDetail(
            Integer id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Contract not found: " + id));
        return buildContractDetail(contract);
    }

    public List<ContractDetailResponse> getContractsByCustomer(
            Integer customerId) {
        List<Contract> contracts = contractRepository
                .findByCustomerId(customerId);
        List<ContractDetailResponse> result = new ArrayList<>();
        for (Contract c : contracts) {
            result.add(buildContractDetail(c));
        }
        return result;
    }

    public List<Contract> getContractsByStatus(String status) {
        return contractRepository.findByStatus(status);
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public Contract updateContract(
            Integer id, Contract contract) {
        contract.setContractId(id);
        return contractRepository.save(contract);
    }

    public void deleteContract(Integer id) {
        contractRepository.deleteById(id);
    }

    private ContractDetailResponse buildContractDetail(
            Contract contract) {

        ContractDetailResponse response =
                new ContractDetailResponse();
        response.setContractId(contract.getContractId());
        response.setMsisdn(contract.getMsisdn());
        response.setStartDate(contract.getStartDate());
        response.setStatus(contract.getStatus());

        // Appel customer-service
        try {
            String url = CUSTOMER_URL
                    + "/api/customers/"
                    + contract.getCustomerId();
            ResponseEntity<CustomerResponse> resp =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            null,
                            CustomerResponse.class);
            response.setCustomer(resp.getBody());
        } catch (Exception e) {
            System.out.println(
                    "customer-service unavailable: "
                            + e.getMessage());
        }

        // Services du contrat
        try {
            List<ContractService> contractServices =
                    contractServiceRepository
                            .findByContractId(
                                    contract.getContractId());

            List<ServiceResponse> services = new ArrayList<>();
            for (ContractService cs : contractServices) {
                ServiceResponse sr = new ServiceResponse();
                sr.setServiceId(cs.getServiceId());
                sr.setStatus(cs.getStatus());
                if (cs.getActivationDate() != null) {
                    sr.setActivationDate(
                            cs.getActivationDate().toString());
                }
                services.add(sr);
            }
            response.setServices(services);
        } catch (Exception e) {
            System.out.println(
                    "Services error: " + e.getMessage());
        }

        return response;
    }
}