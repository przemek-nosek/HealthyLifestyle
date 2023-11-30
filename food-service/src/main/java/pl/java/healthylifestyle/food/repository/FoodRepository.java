package pl.java.healthylifestyle.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.java.healthylifestyle.food.entity.Food;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByUuid(String uuid);

    @Query(nativeQuery = true, value = """
            select * from food where unaccent(name) like %:name%
            """)
    List<Food> findAllByNameContainingIgnoringCaseAndAccent(String name);
}
