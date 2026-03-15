package org.example.telecomcomplainscontractservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscontractservice.entity.ContractService;
import org.example.telecomcomplainscontractservice.entity.Service;
import org.example.telecomcomplainscontractservice.repository.ContractServiceRepository;
import org.example.telecomcomplainscontractservice.repository.ServiceRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceImpl {

    private final ServiceRepository serviceRepository;
    private final ContractServiceRepository
            contractServiceRepository;

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Service getServiceById(Integer id) {
        return serviceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Service not found: " + id));
    }

    public List<Service> getServicesByType(String type) {
        return serviceRepository.findByServiceType(type);
    }

    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    public List<Service> getServicesByContract(
            Integer contractId) {
        List<ContractService> contractServices =
                contractServiceRepository
                        .findByContractId(contractId);
        List<Service> services = new ArrayList<>();
        for (ContractService cs : contractServices) {
            serviceRepository.findById(cs.getServiceId())
                    .ifPresent(services::add);
        }
        return services;
    }

    public ContractService activateService(
            Integer contractId, Integer serviceId) {
        ContractService cs = contractServiceRepository
                .findByContractId(contractId)
                .stream()
                .filter(x -> x.getServiceId()
                        .equals(serviceId))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Service not found in contract"));
        cs.setStatus("ACTIVE");
        cs.setActivationDate(LocalDate.now());
        return contractServiceRepository.save(cs);
    }

    public void deleteService(Integer id) {
        serviceRepository.deleteById(id);
    }
}