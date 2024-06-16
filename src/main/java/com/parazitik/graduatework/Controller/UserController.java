package com.parazitik.graduatework.Controller;

import com.parazitik.graduatework.Entity.Users;
import com.parazitik.graduatework.Model.UserDTO;
import com.parazitik.graduatework.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)");

    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @CrossOrigin
    @PostMapping("create")
    public ResponseEntity<Object> createUsers(@RequestBody Users user) {
        log.info("Creating user: {}", user);
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return ResponseEntity.noContent().build();
        }
        if (!usersService.createUsers(user)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("login")
    public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String password) {
        Users result = usersService.login(email, password);
        if (result == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result.getId());
    }

    @GetMapping("getUser")
    public ResponseEntity<Users> grabUsersById(@RequestParam Long userId) {
        return usersService.getUsersById(userId);
    }

    @GetMapping("exists")
    public ResponseEntity<Long> isUserExists(@RequestParam String email) {
        System.out.println(email);
        return ResponseEntity.ok(usersService.isExistsByEmail(email));
    }

    @PutMapping("update")
    public ResponseEntity<Object> updateUserById(@RequestBody UserDTO user) {
        return ResponseEntity.ok(usersService.updateUser(user));
    }
}
