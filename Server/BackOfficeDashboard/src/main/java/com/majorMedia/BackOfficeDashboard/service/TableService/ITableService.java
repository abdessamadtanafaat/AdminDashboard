package com.majorMedia.BackOfficeDashboard.service.TableService;

import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.model.responses.ObjectsList;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface ITableService {
    User getOwnerInfo(Long ownerId);

    public ObjectsList<User> getAllOwners(String searchKey, String sortOrder, int page);

    public String deactivateAccount(Long ownerId) throws BadRequestException;

    public String deactivateAccounts(List<Long> ownerIds);

    public String activateAccount(Long ownerId);

    public ObjectsList<Business> getAllBusiness(String searchKey, String sortOrder, int page);

    ObjectsList<Campaign> getAllCampagnes(String searchKey, String sortOrder, int page);

    public String editOwner(Long ownerId, String firstName, String lastName, String email, String password, String username);

}
