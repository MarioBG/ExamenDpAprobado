
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='out box'")
	Folder getOutBoxFolderFromActorId(int id);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='in box'")
	Folder getInBoxFolderFromActorId(int id);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='spam box'")
	Folder getSpamBoxFolderFromActorId(int id);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='trash box'")
	Folder getTrashBoxFolderFromActorId(int id);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='notification box'")
	Folder getNotificationBoxFolderFromActorId(int id);

	@Query("select f from Actor a join a.folders f where a.id=?1 and f.parent is null")
	Collection<Folder> getFirstLevelFoldersFromActorId(int actorId);

	@Query("select f from Folder f join f.messages m where m.id=?1")
	Folder getFolderFromMessageId(int messageId);

	@Query("select f from Folder f where f.parent.id=?1")
	Collection<Folder> getChildFolders(int folderId);
	
	@Query("select distinct f from Folder f, Actor a join a.folders f where a.userAccount.id = ?1 and f.name = ?2")
	Folder findByFolderName(int userAccountId, String folderName);

	@Query("select distinct f from Folder f, Actor a join a.folders f where a.userAccount.id = ?1 and f.parent is null")
	Collection<Folder> findFoldersWithoutParent(int userAccountId);

}
