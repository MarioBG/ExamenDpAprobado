package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SubscriptionNewspaper;

@Repository
public interface SubscriptionNewspaperRepository extends JpaRepository<SubscriptionNewspaper, Integer> {
	
}
