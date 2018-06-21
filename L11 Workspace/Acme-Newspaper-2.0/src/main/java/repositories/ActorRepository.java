package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Folder;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id=?1")
	Actor findActorByUserAccountId(int uA);

	@Query("select a.folders from Actor a where a.userAccount.id = ?1")
	Collection<Folder> findFoldersByUserAccountId(int userAccountId);

	@Query("select m.sender from Message m where m.id = ?1")
	Actor findSenderByMessageId(int messageId);

	@Query("select m.recipient from Message m where m.id = ?1")
	Actor findRecipientByMessageId(int messageId);

	@Query("select a from Actor a where a.id =?1")
	Actor findActor(int actorId);

}
