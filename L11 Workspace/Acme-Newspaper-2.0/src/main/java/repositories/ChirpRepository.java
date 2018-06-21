
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	@Query("select c from User u join u.followed v join v.chirps c where u.id=?1")
	Collection<Chirp> findChirpsByFollowedFromUserId(int uacId);

	@Query("select c from Chirp c where c.user.id=?1")
	Collection<Chirp> findChirpsByUserId(int uacId);

}
