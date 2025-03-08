package com.example.property.service.implementation;

import com.example.property.model.Role;
import com.example.property.model.User;
import com.example.property.model.BankAccount;
import com.example.property.repository.BankAccountRepository;
import com.example.property.repository.RoleRepository;
import com.example.property.repository.UserRepository;
import com.example.property.request.BankAccountDetails;
import com.example.property.request.UpdateUserRequest;
import com.example.property.request.UserLoginRequest;
import com.example.property.request.UserRegRequest;
import com.example.property.resource.AuthResource;
import com.example.property.resource.BankDetailResource;
import com.example.property.service.FileUploadService;
import com.example.property.service.RoleService;
import com.example.property.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private StripeService stripeService;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Value("${file.upload-dir}")
    private String path;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public Role getRoleById(UUID id) {
        Optional<Role> isRole = roleRepository.findById(id);
        Role role = isRole.get();
        return role;
    }

    @Override
    public User getUserById(UUID id) {
        Optional<User> isUser = userRepository.findById(id);
        User user = isUser.get();
        return user;
    }

    @Override
    public AuthResource register(UserRegRequest userRegRequest) throws IOException, StripeException {
        System.out.println("reg req"+userRegRequest);
        User user = new User();
        user.setName(userRegRequest.getName());
        user.setEmail(userRegRequest.getEmail());
        user.setPhone(userRegRequest.getPhone());
        user.setPassword(encoder.encode(userRegRequest.getPassword()));
        user.setAddress(userRegRequest.getAddress());
        Set<UUID> roleIds = userRegRequest.getRoleIds();
        List<Role> roles = roleService.getRoleByIds(roleIds);
        user.setRoles(new HashSet<>(roles));
        String customerId = this.getCustomerId(userRegRequest.getName(),userRegRequest.getEmail());
        user.setCustomerId(customerId);
        // register the user
        User isRegistered = userRepository.save(user);
        if(isRegistered.getId() != null){
            String token = jwtService.generateToken(user.getEmail(),user.getPhone(),isRegistered.getId());
            System.out.println("token -----> "+token);
            User isUser = this.getUserById(isRegistered.getId());
            return new AuthResource(isUser,token);
        }
        return null;
    }

    @Override
    public AuthResource verify(UserLoginRequest userLoginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(),userLoginRequest.getPassword())
        );
        if(authentication.isAuthenticated()){
            User user = userRepository.findByEmail(userLoginRequest.getEmail());
            String token = jwtService.generateToken(user.getEmail(),user.getPhone(),user.getId());
            return new AuthResource(user,token);
        }
        return null;
    }

    @Override
    public AuthResource myProfile(UUID id) {
        User user = this.getUserById(id);
        return new AuthResource(user,null);
    }

    @Override
    public User updateProfile(UUID id, UpdateUserRequest updateUserRequest) {
        User isUser = this.getUserById(id);
        if(isUser == null){
            throw new RuntimeException("User not found");
        }
        Optional.ofNullable(updateUserRequest.getName()).ifPresent(isUser::setName);
        Optional.ofNullable(updateUserRequest.getEmail()).ifPresent(isUser::setEmail);
        Optional.ofNullable(updateUserRequest.getPhone()).ifPresent(isUser::setPhone);
        Optional.ofNullable(updateUserRequest.getAddress()).ifPresent(isUser::setAddress);
        if(updateUserRequest.getImage() != null && !updateUserRequest.getImage().isEmpty()){
            try {
                String fileName = fileUploadService.uploadImage(path,updateUserRequest.getImage());
                System.out.println("filename ---------->" + fileName);
                isUser.setImage(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userRepository.save(isUser);
    }

    @Override
    public boolean addBankDetails(UUID userId, BankAccountDetails bankAccountDetails) throws StripeException {
        Optional<User> isUser = userRepository.findById(userId);
        User user = isUser.get();
        Token token = stripeService.createToken(bankAccountDetails);
        System.out.println("token ------->"+token);
        if(token != null) {
            com.stripe.model.BankAccount bankAccount = token.getBankAccount();
            BankAccount account = new BankAccount();
            account.setBankAccountId(bankAccount.getId());
            account.setBankName(bankAccount.getBankName());
            account.setCountry(bankAccount.getCountry());
            account.setCurrency(bankAccount.getCurrency());
            account.setAccountHolderName(bankAccountDetails.getAccountHolderName());
            account.setCustomerId(user.getCustomerId());
            account.setFingerPrint(bankAccount.getFingerprint());
            account.setLast4(bankAccount.getLast4());
            account.setRoutingNo(bankAccount.getRoutingNumber());
            account.setBankTokenId(token.getId());
            account.setUser(user); // Associate user with the bank account
            bankAccountRepository.save(account);
            return true;
        }
        return false;
    }

    public String getCustomerId(String name, String email) throws StripeException {
        Customer customer = stripeService.createCustomer(name, email);
        String customerId = customer.getId();
        return customerId;
    }
}
