package apig.aramian.demo.controller;

import apig.aramian.demo.entity.User;
import apig.aramian.demo.exception.ResourceNotFoundException;
import apig.aramian.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repository;


    @GetMapping
    public List<User> getAllUsers() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with id " + id));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return this.repository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        User existingUser = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with id " + id));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.repository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id){
        User existingUser = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with id " + id));
         this.repository.delete(existingUser);
         return ResponseEntity.ok().build();
    }

}
