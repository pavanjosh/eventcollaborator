package au.cogito.collab.service;

import java.util.UUID;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;


/**
 * Created by pavankumarjoshi on 31/05/2017.
 */
public class TokenService {

    private static final Cache restApiAuthTokenCache = CacheManager.getInstance().getCache("restApiAuthTokenCache");
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }
    public Authentication retrieve(String token) {
        return (Authentication) restApiAuthTokenCache.get(token).getObjectValue();
    }
    public void store(String token, Authentication authentication) {
        restApiAuthTokenCache.put(new Element(token, authentication));
    }

    public boolean contains(String token) {
        return restApiAuthTokenCache.get(token) != null;
    }
    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        restApiAuthTokenCache.evictExpiredElements();
    }
}
