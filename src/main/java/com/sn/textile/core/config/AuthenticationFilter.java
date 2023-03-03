package com.sn.textile.core.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sn.textile.core.base.constants.Constants;
import com.sn.textile.core.base.constants.ENV;
import com.sn.textile.core.base.dtos.LoggedInUserDto;
import com.sn.textile.core.base.util.EnvConfig;
import com.sn.textile.core.base.util.UtilValidate;
import com.sn.textile.core.utils.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    LoggedInUserDto loggedInUser;


    @Autowired
    UtilValidate utilValidate;

//    @Autowired
//    RedisRepo redisRepo;

    @Autowired
    HttpServletRequest servletRequest;

    Map<String, Claim> claims = new HashMap<>();

    List<String> permittedUrlPatterns = Arrays.asList(
            "/swagger-ui/**",
            "/v3/**",
            "/**/public/**",
            "/login",
            "/api/v1/auth/authenticate",
            "/webxmonitoringpoint/**"
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        log.info("### REQ_URI->" + path);
        boolean isPermittedURI = permittedUrlPatterns.stream().anyMatch(p -> new AntPathMatcher().match(p, path));
        StringBuilder token = new StringBuilder(AppUtil.getTokenValue(request));

        String apiKey = AppUtil.getHeader(request, "api-key");
        String apiSecret = AppUtil.getHeader(request, "api-sec");

        if (!isPermittedURI) {
            // decode token
            DecodedJWT jwt = getDecodedToken(token.toString());
            if (null != jwt) {

                // check token expiry date
                if (isTokenExpired(jwt)) {
                    log.error("!!! Token expired.");
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write("{\"status\": 401, \"body\": null, \"message\": \"Token expired.\"}");
                    return;
                }

                // retrieve claims
                claims = jwt.getClaims();

//                if( !utilValidate.noData(apiKey) && !utilValidate.noData(apiSecret) ){ // for feign call
//
//                    if( !apiKey.equals(EnvConfig.getString(ENV.DEF_API_KEY, Constants.DEFAULT_API_KEY)) && ! apiSecret.equals(EnvConfig.getString(ENV.DEF_API_SEC, Constants.DEFAULT_API_SEC)) ){
//                        log.error("Invalid API Secret Key");
//                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                        response.getWriter().write("{\"status\": 401, \"body\": null, \"message\": \"Invalid API Secret Key.\"}");
//                        return;
//                    }
//
//                } 
//                else if( !isValidRequester() && EnvConfig.getString(ENV.SPRING_ACTIVE_PROFILE, "local").equalsIgnoreCase("prod") ){
//                    log.error("Invalid token.");
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    response.getWriter().write("{\"status\": 401, \"body\": null, \"message\": \"Unauthorized access.\"}");
//                    return;
//                }
                
//                log.info("has token in redis: " + isTokenExistInRedis(token, claims.get("userId").asString()));

//                if (!hasValidClaims() || !isTokenExistInRedis(token, claims.get("userId").asString())) {
//                    log.error("Invalid token or token not exist in redis.");
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    response.getWriter().write("{\"status\": 401, \"body\": null, \"message\": \"Invalid token.\"}");
//                    return;
//                }

                // retrieve and keep user details from token claims
                retrieveUser(jwt);

            } else {
                log.error("!!! Unauthorized access.");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"status\": 401, \"body\": null, \"message\": \"Unauthorized access.\"}");
                return;
            }
        }

//        if (!secureService.hasPermission(token)) {
//            log.error("!!! Access forbidden.");
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().write("{\"status\": 403, \"body\": null, \"message\": \"Access Forbidden.\"}");
//            return;
//        }

        allowCrossOrigin(request, response, filterChain);
        filterChain.doFilter(request, response);
    }

    private void allowCrossOrigin(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        if ("OPTIONS".equals(request.getMethod())) response.setStatus(HttpServletResponse.SC_OK);
    }

    private DecodedJWT getDecodedToken(String token) {
        DecodedJWT decodedJWT = null;
        try {
            if (StringUtils.isEmpty(token)) return null;
            decodedJWT = JWT.decode(token);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return decodedJWT;
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        return null != decodedJWT && decodedJWT.getExpiresAt().before(new Date());
    }

    private boolean hasKey(String key) {
        return !utilValidate.noData(claims) && claims.containsKey(key);
    }

    private boolean hasValidClaims() {
    	log.info("Has Key: " + hasKey("userId"));
        return hasKey("userId");
    }

    private boolean isValidRequester() {
        String actualTokenInitiator = AppUtil.SHA256(AppUtil.getIpProxyIp(servletRequest) + AppUtil.getClientInfo(servletRequest));
        String requester = claims.get("tokenInitial").as(String.class);
        log.info("Valid Requestor: " + actualTokenInitiator.equalsIgnoreCase(requester));
        log.info("Token Initiator: " + requester);
        log.info("Requestor: " + actualTokenInitiator);
        return actualTokenInitiator.equalsIgnoreCase(requester);
    }

    private boolean isTokenExistInRedis(StringBuilder token, String uid) {
        if (token == null || uid == null || uid.isEmpty()) return false;
        // check token existence on redis
//        UUID userId = AppUtil.toUUID(redisRepo.getValue(token.toString()));
//        if (userId != null && userId.toString().equalsIgnoreCase(uid))
        if(true)
            return true;
        return false;
    }

    private LoggedInUserDto retrieveUser(DecodedJWT jwt) {
        if (hasKey("userId")) loggedInUser.setUserId(claims.get("userId").as(UUID.class));
        if (hasKey("firstName")) loggedInUser.setFirstName(claims.get("firstName").asString());
        if (hasKey("lastName")) loggedInUser.setLastName(claims.get("lastName").asString());
        if (hasKey("designation")) loggedInUser.setDesignation(claims.get("designation").asString());
        if (hasKey("roleCode")) loggedInUser.setRoleCode(claims.get("roleCode").asString());
        if (hasKey("email")) loggedInUser.setEmail(claims.get("email").asString());
        if (hasKey("phone")) loggedInUser.setPhone(claims.get("phone").asString());
        if (hasKey("profileImg")) loggedInUser.setProfileImg(claims.get("profileImg").asString());

        if (hasKey("roleId")) loggedInUser.setRoleId(claims.get("roleId").as(UUID.class));
        if (hasKey("roleName")) loggedInUser.setRoleName(claims.get("roleName").asString());

        loggedInUser.setToken("Bearer " + jwt.getToken());
        return loggedInUser;
    }

}
