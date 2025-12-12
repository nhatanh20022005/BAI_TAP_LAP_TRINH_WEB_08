package ltwebct4.input;

import lombok.Data;
import java.util.List;

@Data
public class UserInput {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    // Danh sách ID của Category liên quan (Many-to-Many)
    private List<Long> categoryIds; 
}