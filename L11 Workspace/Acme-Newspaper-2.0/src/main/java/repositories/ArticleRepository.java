package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("select a from Article a where(a.title LIKE %?1% or a.summary LIKE %?1% or a.body LIKE %?1%) and a.publicationMoment <= current_date and a.newspaper.id = ?2")
	Collection<Article> findPerKeyword(String aux, int newspaperId);
	
	@Query("select a from Article a where a.writer.id = ?1")
	Collection<Article> findByWriterId(int writerId);

}
