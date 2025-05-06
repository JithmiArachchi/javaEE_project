package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity  // mark as table
@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {
    @Id  // for primary key
    @GeneratedValue(strategy = GenerationType.AUTO) // auto increment ID value
    private Long id;
    private String name ;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
