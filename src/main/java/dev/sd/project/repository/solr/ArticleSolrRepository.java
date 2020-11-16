package dev.sd.project.repository.solr;

import dev.sd.project.model.solr.ArticleSolr;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.solr.repository.Query;

import java.util.List;

public interface ArticleSolrRepository extends PagingAndSortingRepository<ArticleSolr, String> {
    List<ArticleSolr> findAllByTitle(String query);
    List<ArticleSolr> findAllByContent(String query);
    List<ArticleSolr> findAllByArticleId(String id);
}
