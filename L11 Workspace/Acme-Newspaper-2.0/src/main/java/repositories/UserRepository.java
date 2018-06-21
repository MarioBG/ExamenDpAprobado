
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id=?1")
	User findUserByUserAccountId(int uA);

	@Query("select u.followed from User u where u.userAccount.id=?1")
	Collection<User> findFollowedUsersByUserAccountId(int uA);

	@Query("select u.followers from User u where u.userAccount.id=?1")
	Collection<User> findFollowersByUserAccountId(int uA);
}
