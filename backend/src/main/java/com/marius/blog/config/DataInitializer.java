package com.marius.blog.config;

import com.marius.blog.model.User;
import com.marius.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            admin.setBio("System Administrator");
            userRepository.save(admin);
            System.out.println("✅ Default admin user created!");
            System.out.println("   Username: admin");
            System.out.println("   Password: admin123");
        }
        
        // Create default author user if it doesn't exist
        if (!userRepository.existsByUsername("author")) {
            User author = new User();
            author.setUsername("author");
            author.setEmail("author@example.com");
            author.setPassword(passwordEncoder.encode("author123"));
            author.setRole(User.Role.AUTHOR);
            author.setBio("Default Author");
            userRepository.save(author);
            System.out.println("✅ Default author user created!");
            System.out.println("   Username: author");
            System.out.println("   Password: author123");
        }
    }
}
