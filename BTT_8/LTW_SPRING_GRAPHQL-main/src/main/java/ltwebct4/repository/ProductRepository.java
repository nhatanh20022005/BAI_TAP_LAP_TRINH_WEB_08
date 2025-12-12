package ltwebct4.repository;

import ltwebct4.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Hiển thị tất cả product có price từ thấp đến cao
    List<Product> findAllByOrderByPriceAsc();

    // 2. Lấy tất cả product của 01 category
    @Query("SELECT p FROM Product p JOIN p.user u JOIN u.categories c WHERE c.id = :categoryId ORDER BY p.title ASC")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
}