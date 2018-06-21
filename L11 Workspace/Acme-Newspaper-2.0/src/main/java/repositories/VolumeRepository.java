
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Volume;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {
	
	@Query("select v from Volume v where v.user.id = ?1")
	Collection<Volume> findByUserId(int userId);
	
	@Query("select sv.volume from SubscriptionVolume sv where sv.customer.id = ?1")
	Collection<Volume> findSubscribedVolumesByCustomerId(int customerId);

}
