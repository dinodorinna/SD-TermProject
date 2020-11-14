package dev.sd.project.model.solr;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "sdp_core")
@Data
public class ArticleSolr {
    @Id
    private String articleId;

    @Indexed(name = "writer", type = "string")
    private String writerName;
    @Indexed(name = "title", type = "string")
    private String title;
    @Indexed(name = "content", type = "string")
    private String content;
}
