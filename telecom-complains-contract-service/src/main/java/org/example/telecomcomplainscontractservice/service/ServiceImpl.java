package org.example.telecomcomplainscontractservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscontractservice.entity.ContractService;
import org.example.telecomcomplainscontractservice.entity.Service;
import org.example.telecomcomplainscontractservice.repository.ContractServiceRepository;
import org.example.telecomcomplainscontractservice.repository.ServiceRepository;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceImpl {

    private final ServiceRepository serviceRepository;
    private final ContractServiceRepository contractServiceRepository;

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Service getServiceById(Integer id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service non trouvé : " + id));
    }

    public List<Service> getServicesByType(String type) {
        return serviceRepository.findByServiceType(type);
    }

    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    public List<ContractService> getServicesByContract(Integer contractId) {
        return contractServiceRepository.findByContractId(contractId);
    }

    public ContractService activateService(Integer contractId, Integer serviceId) {
        ContractService cs = new ContractService();
        cs.setContractId(contractId);
        cs.setServiceId(serviceId);
        cs.setStatus("ACTIVE");
        cs.setActivationDate(java.time.LocalDate.now());
        return contractServiceRepository.save(cs);
    }

    public void deleteService(Integer id) {
        serviceRepository.deleteById(id);
    }
}