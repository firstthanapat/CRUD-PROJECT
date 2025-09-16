package sit.int202.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int202.demo.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
