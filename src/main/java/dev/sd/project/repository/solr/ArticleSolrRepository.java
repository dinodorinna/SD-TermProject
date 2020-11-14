package dev.sd.project.repository.solr;

import dev.sd.project.model.solr.ArticleSolr;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleSolrRepository extends PagingAndSortingRepository<ArticleSolr, String> {

}
