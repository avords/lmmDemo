package com.lmm.data;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends ElasticsearchRepository<City, String> {
}
