package ltwebct4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = "users") 
@ToString(exclude = "users") 
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String images; 

    // Many-to-Many với User. mappedBy chỉ ra rằng User là bên sở hữu mối quan hệ
    @ManyToMany(mappedBy = "categories")
    private Set<User> users = new HashSet<>();
}