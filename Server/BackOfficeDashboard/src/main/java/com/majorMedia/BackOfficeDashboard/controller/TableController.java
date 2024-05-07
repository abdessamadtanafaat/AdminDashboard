package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.aspect.LogActivity;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.model.responses.BusinessResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.ObjectsList;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.service.TableService.ITableService;
import com.majorMedia.BackOfficeDashboard.service.adminService.IAdminService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/tables")

@AllArgsConstructor

//@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")

public class TableController {

    private final ITableService tableService;

    @LogActivity
    @GetMapping(value = "/owners")
    public ResponseEntity<ObjectsList<User>> getAllUsers(
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required=false ,name="page" , defaultValue="1") int page

    )
    {
        return ResponseEntity.ok(tableService.getAllOwners(searchKey,sortOrder,page));
    }

    @LogActivity
    @GetMapping(value = "/business")
    public ResponseEntity<ObjectsList<Business>> getAllBusiness(
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required=false ,name="page" , defaultValue="1") int page
    )
    {
        return ResponseEntity.ok(tableService.getAllBusiness(searchKey,sortOrder,page));
    }

@LogActivity
@GetMapping(value = "/campagnes")
public ResponseEntity<ObjectsList<Campaign>> getAllCampagnes(
        @RequestParam(required = false) String sortOrder,
        @RequestParam(required = false) String searchKey,
        @RequestParam(required=false ,name="page" , defaultValue="1") int page
)
{
    return ResponseEntity.ok(tableService.getAllCampagnes(searchKey,sortOrder,page));
}
    @LogActivity
    @GetMapping(value = "/owner")
    public ResponseEntity<User> getOwner(@RequestParam(value = "ownerId")
                                                        Long ownerId)
    {
        return ResponseEntity.ok(tableService.getOwnerInfo(ownerId));
    }


    @LogActivity
    @PatchMapping("/deactivateAccount/{ownerId}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Long ownerId) throws BadRequestException {
        String string = tableService.deactivateAccount(ownerId);
        return ResponseEntity.ok(string);
    }
    @LogActivity
    @PatchMapping("/deactivateAccounts/{ownerIds}")
    public ResponseEntity<String> deactivateAccounts(@PathVariable List<Long> ownerIds) {
        String result = tableService.deactivateAccounts(ownerIds);
        return ResponseEntity.ok(result);
    }
    @LogActivity
    @PatchMapping("/activateAccount/{ownerId}")
    public ResponseEntity<String> activateAccount(@PathVariable Long ownerId)
    {
        String string = tableService.activateAccount(ownerId);
        return ResponseEntity.ok(string);
    }
    @LogActivity
    @PatchMapping("/editOwner/{ownerId}")
    public ResponseEntity<String> editOwner(
            @PathVariable Long ownerId,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value ="lastName", required = false) String lastName,
            @RequestParam(value =  "email", required = false) String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "username", required = false) String username
    ) throws MessagingException, UnsupportedEncodingException {
        String result = tableService.editOwner(ownerId, firstName, lastName, email, password, username);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/business/{businessId}/users")
    public List<User> getUsersByBusinessId(@PathVariable int businessId) {
        return tableService.getUsersByBusinessId(businessId);
    }

    @GetMapping("/owner/{ownerId}/businesses")
    public List<Business> getBusinessesByOwnerId(@PathVariable Long ownerId) {
        return tableService.getBusinessesByOwnerId(ownerId);
    }

}
