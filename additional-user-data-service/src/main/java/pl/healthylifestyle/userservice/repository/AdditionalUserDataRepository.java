package pl.healthylifestyle.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.healthylifestyle.userservice.entity.AdditionalUserData;

import java.util.Optional;

@Repository
public interface AdditionalUserDataRepository extends JpaRepository<AdditionalUserData, Long> {

    Optional<AdditionalUserData> findByUuid(String uuid);
}