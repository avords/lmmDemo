package com.lmm.data;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {
}
