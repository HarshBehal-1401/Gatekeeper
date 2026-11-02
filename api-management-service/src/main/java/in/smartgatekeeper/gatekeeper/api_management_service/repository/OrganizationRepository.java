package in.smartgatekeeper.gatekeeper.api_management_service.repository;

import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.OrganizationResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.entity.Organization;
import in.smartgatekeeper.gatekeeper.api_management_service.entity.OrganizationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsBySlugIgnoreCase(String slug);

    List<Organization> findAllByStatusNot(OrganizationStatus status);

    Optional<Organization> findByIdAndStatusNot(Long id, OrganizationStatus status);

}
