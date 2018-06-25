package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Zust;

public interface ZustRepository extends JpaRepository<Zust, Integer> {

	@Query("select z from Zust z where z.newspaper.id = ?1")
	Collection<Zust> zustByNewspaperId(int newspaperId);

	@Query("select z from Zust z where z.admin.id = ?1")
	Collection<Zust> findAllByAdminId(int adminId);

	@Query("select z from Zust z where (z.admin.id = ?1 and z.isFinal =true) and z.newspaper.id = null")
	Collection<Zust> findAllByAdminIdWithoutNewspaper(int adminId);

}
