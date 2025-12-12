package ltwebct4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String productsView() {
        return "products"; // Trả về file products.html
    }
}