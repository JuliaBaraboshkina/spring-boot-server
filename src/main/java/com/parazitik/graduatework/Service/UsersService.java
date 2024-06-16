package com.parazitik.graduatework.Service;

import com.parazitik.graduatework.Entity.Users;
import com.parazitik.graduatework.Model.UserDTO;
import com.parazitik.graduatework.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public boolean createUsers(Users user) {
         if (userRepository.findByEmail(user.getEmail())) return false;

        user.setCreatedAt(new Date());

        userRepository.save(user);

        return true;
    }

    public Users login(String email, String password){
        return userRepository.getUsersByEmailAndPassword(email, password).orElse(null);
    }

    public ResponseEntity<Users> getUsersById(long id) {
        return ResponseEntity.ok(userRepository.findById(id).orElse(null));
    }

    public ResponseEntity<Users> updateUser(UserDTO user) {
        Users newUser = userRepository.findById(user.getId()).orElse(null);

        if (newUser == null) return ResponseEntity.badRequest().build();

        if (user.getEmail() != null) newUser.setEmail(user.getEmail());
        if (user.getPassword() != null) newUser.setPassword(user.getPassword());
        if (user.getFirstName() != null) newUser.setFirstName(user.getFirstName());
        if (user.getSecondName() != null) newUser.setSecondName(user.getSecondName());
        if (user.getAvatarUrl() != null) newUser.setAvatarUrl(user.getAvatarUrl());

        userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    public Long isExistsByEmail(String email) {
        if(userRepository.findByEmail(email)) return userRepository.getByEmail(email).getId();

        System.out.println("HERE");

        return null;
    }
}
