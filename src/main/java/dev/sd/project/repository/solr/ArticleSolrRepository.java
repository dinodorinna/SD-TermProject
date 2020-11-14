package dev.sd.project.repository.solr;

import dev.sd.project.model.solr.ArticleSolr;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.solr.repository.Query;

import java.util.List;

public interface ArticleSolrRepository extends PagingAndSortingRepository<ArticleSolr, String> {
    @Query("writer:?0 OR title:?0 OR content:?0 OR tag:?0")
    List<ArticleSolr> search(String word);

}
