package com.example.property.controller;

import com.example.property.model.Role;
import com.example.property.request.RoleRequest;
import com.example.property.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<?> getAllRoles(){
        try {
            List<Role> roles = roleService.getAll();
            return ResponseEntity.ok(Map.of(
                    "status", true,
                    "message", roles.isEmpty() ? "roles not found" : "roles found",
                    "data", roles
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "An error occurred while fetching roles",
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/get-details")
    public ResponseEntity<?> getRoleById(@RequestBody UUID roleId){
        System.out.println("---------------------00000000--------------------");
        if(roleId == null){
            return ResponseEntity.badRequest().body(Map.of(
                    "status", false,
                    "message", "rol id is nissing"
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "status", false,
                "message", "rol id is nissing"
        ));
//        try{
//            Role role = roleService.findByUUid(roleId);
//            if(role != null){
//                return ResponseEntity.ok(Map.of(
//                        "status",true,
//                        "message","role found",
//                        "data",role
//                ));
//            }
//            return ResponseEntity.ok(Map.of(
//                    "status",false,
//                    "message","role not found"
//            ));
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//            return ResponseEntity.internalServerError().body(Map.of(
//                    "status",false,
//                    "message","something went wrong"
//            ));
//        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody RoleRequest roleRequest, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(Map.of(
               "status",false, "message",result.getAllErrors()
            ));
        }
        try{
            Role role = roleService.add(roleRequest);
            return ResponseEntity.ok(Map.of(
                    "status", true,
                    "message", "Data added",
                    "data", role
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "something went wrong",
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody RoleRequest roleRequest, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(Map.of(
                    "status",false,
                    "message",result.getAllErrors()
            ));
        }
        try{
            Role isUpdate = roleService.update(roleRequest,id);
            return ResponseEntity.ok(Map.of(
                    "status",true,
                    "message","role updated",
                    "data",isUpdate
            ));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","something went wrong",
                    "error",e.getMessage()
            ));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        try{
           roleService.delete(id);
           return ResponseEntity.ok(Map.of(
                   "status", true,
                   "message", "Data deleted"
           ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "something went wrong",
                    "error", e.getMessage()
            ));
        }
    }
}
