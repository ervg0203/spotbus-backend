package com.spotBus.backend.config;

import com.spotBus.backend.entity.AdminEntity;
import com.spotBus.backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ConditionalOnProperty(name = "admin.bootstrap.enabled", havingValue = "true", matchIfMissing = true)
public class AdminDataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;
    private final String adminName;
    private final String adminPhone;

    public AdminDataInitializer(AdminRepository adminRepository,
                                PasswordEncoder passwordEncoder,
                                @Value("${admin.bootstrap.email:admin@spotbus.com}") String adminEmail,
                                @Value("${admin.bootstrap.password:admin12345}") String adminPassword,
                                @Value("${admin.bootstrap.name:System Admin}") String adminName,
                                @Value("${admin.bootstrap.phone:9999999999}") String adminPhone) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
    }

    @Override
    public void run(String... args) {
        if (adminRepository.existsByEmail(adminEmail)) {
            return;
        }

        AdminEntity admin = new AdminEntity();
        admin.setName(adminName);
        admin.setEmail(adminEmail);
        admin.setPhone(adminPhone);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());

        adminRepository.save(admin);
    }
}
