package com.bhachu.farmica.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.bhachu.farmica.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.bhachu.farmica.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.bhachu.farmica.domain.PackingZoneDetail.class.getName());
            createCache(cm, com.bhachu.farmica.domain.Comment.class.getName());
            createCache(cm, com.bhachu.farmica.domain.ReworkComment.class.getName());
            createCache(cm, com.bhachu.farmica.domain.ReworkDetail.class.getName());
            createCache(cm, com.bhachu.farmica.domain.LotDetail.class.getName());
            createCache(cm, com.bhachu.farmica.domain.VariableData.class.getName());
            createCache(cm, com.bhachu.farmica.domain.BatchDetail.class.getName());
            createCache(cm, com.bhachu.farmica.domain.Region.class.getName());
            createCache(cm, com.bhachu.farmica.domain.Style.class.getName());
            createCache(cm, com.bhachu.farmica.domain.SalesDetail.class.getName());
            createCache(cm, com.bhachu.farmica.domain.WarehouseDetail.class.getName());
            createCache(cm, com.bhachu.farmica.domain.FarmicaReport.class.getName());
            createCache(cm, com.bhachu.farmica.domain.StyleReport.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
