package sit.int202.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sit.int202.demo.entities.Productline;

@Repository
public interface ProductLineRepository extends JpaRepository<Productline, Long> {
}
