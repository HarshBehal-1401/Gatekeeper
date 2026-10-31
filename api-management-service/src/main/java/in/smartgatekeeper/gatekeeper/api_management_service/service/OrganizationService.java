package in.smartgatekeeper.gatekeeper.api_management_service.service;

import in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto.OrganizationRequest;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto.OrganizationStatusRequest;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto.TenantAdminRequest;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.OrganizationResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.TenantAdminResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.entity.ManagementUser;
import in.smartgatekeeper.gatekeeper.api_management_service.entity.Organization;
import in.smartgatekeeper.gatekeeper.api_management_service.entity.OrganizationStatus;
import in.smartgatekeeper.gatekeeper.api_management_service.entity.Role;
import in.smartgatekeeper.gatekeeper.api_management_service.exception.ApiException;
import in.smartgatekeeper.gatekeeper.api_management_service.repository.ManagementUserRepository;
import in.smartgatekeeper.gatekeeper.api_management_service.repository.OrganizationRepository;
import in.smartgatekeeper.gatekeeper.api_management_service.repository.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ManagementUserRepository managementUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public OrganizationService(OrganizationRepository organizationRepository,
                               ManagementUserRepository managementUserRepository,
                               RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.managementUserRepository = managementUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public OrganizationResponse create(OrganizationRequest request) {
        long startedAt = System.nanoTime();

        checkUnique(request.getName(), request.getSlug(), null);

        Organization organization = new Organization();
        organization.setName(request.getName());
        organization.setSlug(request.getSlug());
        organization.setStatus(OrganizationStatus.PENDING);
        organization.setCreatedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());

        organizationRepository.save(organization);

        return OrganizationResponse.from(organization);
    }

    @Transactional(readOnly = true)
    public List<OrganizationResponse> findAll() {
        List<OrganizationResponse> response = new ArrayList<OrganizationResponse>();

        for(Organization organization :
                organizationRepository.findAllByStatusNot(OrganizationStatus.DELETED)) {
            response.add(OrganizationResponse.from(organization));
        }
        return response;
    }

    @Transactional(readOnly = true)
    public OrganizationResponse findById(Long id) {

        Organization organization = getOrganization(id);

        return OrganizationResponse.from(organization);
    }

    @Transactional
    public OrganizationResponse update(Long id, OrganizationRequest request) {

        Organization organization = getOrganization(id);

        checkUnique(request.getName(), request.getSlug(), id);

        organization.setName(request.getName());
        organization.setSlug(request.getSlug());
        organization.setUpdatedAt(LocalDateTime.now());

        return OrganizationResponse.from(organization);
    }

    // 5. Change Organization Status
    @Transactional
    public OrganizationResponse changeStatus(
            Long id,
            OrganizationStatusRequest request) {

        Organization organization = getOrganization(id);

        if (request.getStatus() == null) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "Organization status cannot be null"
            );
        }

        if (request.getStatus().equals(OrganizationStatus.DELETED)) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "Use DELETE to soft-delete an organization"
            );
        }

        organization.setStatus(request.getStatus());
        organization.setUpdatedAt(LocalDateTime.now());

        return OrganizationResponse.from(organization);
    }

    // 6. Soft Delete Organization
    @Transactional
    public void delete(Long id) {

        Organization organization = getOrganization(id);

        LocalDateTime now = LocalDateTime.now();

        organization.setStatus(OrganizationStatus.DELETED);
        organization.setDeletedAt(now);
        organization.setUpdatedAt(now);
    }

    // Find Organization or Throw Exception
    private Organization getOrganization(Long id) {

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND,
                        "Organization not found with ID: " + id
                ));

        if (organization.getStatus() == OrganizationStatus.DELETED) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND,
                    "Organization not found with ID: " + id
            );
        }

        return organization;
    }

    @Transactional
    public TenantAdminResponse createTenantAdmin(Long organizationId, TenantAdminRequest request) {
        long startedAt = System.nanoTime();
        Organization organization = getOrganization(organizationId);

        if(managementUserRepository.findByOrganizationId(organizationId).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "This organization already has a tenant administrator");
        }

        if(managementUserRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already exists");
        }

        Role role = roleRepository.findByName("TENANT_ADMIN")
                .orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "TENANT_ADMIN role is missing"));

        ManagementUser user = new ManagementUser();
        user.setOrganization(organization);
        user.setRole(role);
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(request.isEnabled());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        managementUserRepository.save(user);

        return TenantAdminResponse.from(user);
    }

    @Transactional(readOnly = true)
    public TenantAdminResponse getTenantAdmin(Long organizationId) {
        return TenantAdminResponse.from(getTenantAdminEntity(organizationId));
    }

    @Transactional
    public TenantAdminResponse updateTenantAdmin(Long organizationId, TenantAdminRequest request) {
        long startedAt = System.nanoTime();
        ManagementUser user = getTenantAdminEntity(organizationId);

        if(!user.getUsername().equals(request.getUsername())
                && managementUserRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already exists");
        }

        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(request.isEnabled());
        user.setUpdatedAt(LocalDateTime.now());

        return TenantAdminResponse.from(user);
    }

    @Transactional
    public void deleteTenantAdmin(Long organizationId) {
        long startedAt = System.nanoTime();

        ManagementUser user = getTenantAdminEntity(organizationId);

        managementUserRepository.delete(user);
    }

    private ManagementUser getTenantAdminEntity(Long organizationId) {
        getOrganization(organizationId);
        return managementUserRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Tenant administrator not found"));
    }


    private void checkUnique(String name, String slug, Long currentId) {
        if(organizationRepository.existsByNameIgnoreCase(name)) {
            Organization current = currentId == null ?
                    null : organizationRepository.findById(currentId).orElse(null);

            if(current == null || !current.getName().equalsIgnoreCase(name)) {
                throw new ApiException(HttpStatus.CONFLICT, "Organization name already exists");
            }
        }

        if(organizationRepository.existsBySlugIgnoreCase(slug)) {
            Organization current = currentId == null ?
                    null : organizationRepository.findById(currentId).orElse(null);

            if(current == null || !current.getSlug().equalsIgnoreCase(slug)) {
                throw new ApiException(HttpStatus.CONFLICT, "Organization slug already exists");
            }
        }
    }
}
