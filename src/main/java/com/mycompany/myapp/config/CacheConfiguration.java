package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

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
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Party.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyRole.class.getName());
            createCache(cm, com.mycompany.myapp.domain.RoleType.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyAddress.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyContact.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyIdentification.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyRelationship.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Agreement.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyClassification.class.getName());
            createCache(cm, com.mycompany.myapp.domain.StatusType.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CommEvent.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CommEventPurpose.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CommEvtPposType.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactMech.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactMechType.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyContactMech.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyContactMechPpos.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactMechPposType.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactMechLink.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FacilityRole.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FacilityRoleType.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Facility.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PartyFacility.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FacilityContactMech.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Casus.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CasusRole.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CasusRoleType.class.getName());
            createCache(cm, com.mycompany.myapp.domain.WorkEffort.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CommWorkEffort.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
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
