package gr.myprojects.easytask.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    @Getter(AccessLevel.PRIVATE)
    private Set<Task> tasks = new HashSet<>();

    public Set<Task> getAllTasks() {
        if (tasks == null) tasks = new HashSet<>();

        return Collections.unmodifiableSet(tasks);
    }

    public void addTask(Task task) {
        if (tasks == null) tasks = new HashSet<>();

        tasks.add(task);
        task.setUser(this);
    }

    public void removeTask(Task task) {
        if (tasks == null) return;

        tasks.remove(task);
        task.setUser(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
