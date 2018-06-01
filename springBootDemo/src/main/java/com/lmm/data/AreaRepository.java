package com.lmm.data;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends ElasticsearchRepository<Area, String> {
}
