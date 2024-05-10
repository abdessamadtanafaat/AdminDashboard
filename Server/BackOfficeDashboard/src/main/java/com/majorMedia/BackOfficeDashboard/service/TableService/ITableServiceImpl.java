package com.majorMedia.BackOfficeDashboard.service.TableService;

import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.exception.EmailServiceException;
import com.majorMedia.BackOfficeDashboard.model.responses.ObjectsList;
import com.majorMedia.BackOfficeDashboard.model.responses.PaginationData;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.BusinessRepository;
import com.majorMedia.BackOfficeDashboard.repository.CampagneRepository;
import com.majorMedia.BackOfficeDashboard.repository.UserRepository;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@AllArgsConstructor
public class ITableServiceImpl implements ITableService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BusinessRepository businessRepository;
    private final CampagneRepository campagneRepository;
    private ServiceUtils adminService;
    private EmailUtils emailUtils;

    @Override
    @Transactional()
    public User getOwnerInfo(Long ownerId) {
        try {
            return  userRepository.findById(ownerId)
                    .orElseThrow(() -> new EntityNotFoundException("Owner with ID " + ownerId + " not found"));

        } catch (EntityNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while fetching owner information", e);
        }

    }

    @Override
    public ObjectsList<User> getAllOwners(String searchKey, String sortOrder , int page ) {
        Pageable paging;
        if ("asc".equalsIgnoreCase(sortOrder)) {
            paging = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.ASC, "createdAt"));
        } else {
            paging = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        }        if(searchKey ==null){
            Page<User> users = userRepository.findAllWithBusinesses(paging);
            return unwrapOwnerList(users, page);
        }
        Page<User> users = userRepository.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseWithBusinesses(searchKey, paging);
        return unwrapOwnerList(users, page);
    }
    public ObjectsList<User> unwrapOwnerList(Page<User> users , int page){
        return ObjectsList.<User>builder().data(users.getContent()).
                meta(
                        new PaginationData(
                                page , 5 , users.getTotalPages() ,
                                users.getTotalElements()
                        )).build();

    }
    @Override
    @Transactional
    public String deactivateAccount(Long ownerId){
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner are required");
        }

        User owner  = userRepository.findById(ownerId)
                .orElseThrow(()->new com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException(ownerId, User.class));

        if (owner.is_deactivated()){
            throw new IllegalStateException("Account: " + owner.getLastName() + " is already deactivated");
        }
        owner.set_deactivated(true);
        owner.setActive(false);
        userRepository.save(owner);
        return "Account : "+owner.getLastName() +" deactivated Successfully";
    }

    @Override
    @Transactional
    public String deactivateAccounts(List<Long> ownerIds) {
        try {
            if (ownerIds == null || ownerIds.isEmpty()) {
                throw new IllegalArgumentException("At least one owner ID is required");
            }

            List<User> owners = userRepository.findAllById(ownerIds);
            StringBuilder deactivatedAccounts = new StringBuilder();
            for (User owner : owners) {
                owner.set_deactivated(true);
                owner.setActive(false);
                userRepository.save(owner);
                deactivatedAccounts.append("Account: ").append(owner.getLastName()).append(" deactivated Successfully\n");
            }

            return deactivatedAccounts.toString();
        } catch (IllegalArgumentException | com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException ex) {
            return "Error: " + ex.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String activateAccount(Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner are required");
        }
        User owner  = userRepository.findById(ownerId)
                .orElseThrow(()->new com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException(ownerId, User.class));

        owner.set_deactivated(false);
        owner.setActive(false);
        userRepository.save(owner);
        return "Account : "+owner.getLastName() +" activated Successfully";
    }


    @Override
    @Transactional
    public String editOwner(Long ownerId, String firstName, String lastName, String email, String password, String username) throws MessagingException, UnsupportedEncodingException {
        try {
            User owner = userRepository.findById(ownerId)
                    .orElseThrow(() -> new com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException(ownerId, User.class));

//            if (firstName != null) {
//                owner.setFirstName(firstName);
//            }
//            if (lastName != null) {
//                owner.setLastName(lastName);
//            }
//            if (email != null) {
//                owner.setEmail(email);
//            }
            if (password != null) {
                String encodedPassword = passwordEncoder.encode(password);
                owner.setPassword(encodedPassword);
            }
//            if (username != null) {
//                owner.setUsername(username);
//            }
            owner.setActive(false);
            userRepository.save(owner);
            try {
                emailUtils.sendEmailActivation(owner.getEmail(), password);
            } catch (UnsupportedEncodingException | MessagingException e) {
                e.printStackTrace();
                throw new EmailServiceException();
            }

            return "Account updated successfully for " + owner.getFirstName();

        } catch (Exception e) {
            e.printStackTrace();
        }return  null;
    }

    @Override
    public ObjectsList<Campaign> getAllCampagnes(String searchKey, String sortOrder, int page) {
        Pageable paging;
        if ("asc".equalsIgnoreCase(sortOrder)) {
            paging = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.ASC, "createdDate"));
        } else {
            paging = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdDate"));
        }
        if (searchKey == null) {
            return unwrapCampaignList(campagneRepository.findAll(paging), page);
        }
        Page<Campaign> campaigns = campagneRepository.findAllByCampaignNameContainsIgnoreCase(searchKey, paging);
        return unwrapCampaignList(campaigns, page);
    }

    public ObjectsList<Campaign> unwrapCampaignList(Page<Campaign> campaigns , int page){
        return ObjectsList.<Campaign>builder().data(campaigns.getContent()).
                meta(
                        new PaginationData(
                                page , 5 , campaigns.getTotalPages() ,
                                campaigns.getTotalElements()
                        )).build();

    }

    @Override
    public ObjectsList<Business> getAllBusiness(String searchKey, String sortOrder, int page) {
        Pageable paging;
        if ("asc".equalsIgnoreCase(sortOrder)) {
            paging = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.ASC, "createdDate"));
        } else {
            paging = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdDate"));
        }
        if (searchKey == null) {
            return unwrapBusinessList(businessRepository.findAll(paging), page);
        }
        Page<Business> business = businessRepository.findAllByBusinessNameContainsIgnoreCase(searchKey, paging);
        return unwrapBusinessList(business, page);
    }

    public ObjectsList<Business> unwrapBusinessList(Page<Business> business , int page){
        return ObjectsList.<Business>builder().data(business.getContent()).
                meta(
                        new PaginationData(
                                page , 5 , business.getTotalPages() ,
                                business.getTotalElements()
                        )).build();

    }

    public List<User> getUsersByBusinessId(int businessId) {
        Business business = businessRepository.findById(businessId).orElse(null);
        if (business != null) {
            return business.getUser().getBusinesses().stream()
                    .map(Business::getUser)
                    .toList();
        }
        return null;
    }

    public List<Business> getBusinessesByOwnerId(Long ownerId) {
        User owner = userRepository.findById(ownerId).orElse(null);
        if (owner != null) {
            return owner.getBusinesses();
        }
        return null;
    }

}
