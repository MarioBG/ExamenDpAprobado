package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SubscriptionVolume;

@Repository
public interface SubscriptionVolumeRepository extends JpaRepository<SubscriptionVolume, Integer> {
	
}
