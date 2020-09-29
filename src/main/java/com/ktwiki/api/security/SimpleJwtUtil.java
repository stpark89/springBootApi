package com.ktwiki.api.security;

import com.ktwiki.api.dto.JwtAuthenticationResponse;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class SimpleJwtUtil implements JwtUtil, Serializable {

    static final String CLAIM_KEY_ID = "jti";
    static final String CLAIM_KEY_USER_ID = "uid";
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_ROLES = "roles";
    private static final long serialVersionUID = -3301605591108950415L;
    // default: sha256 hash of `nautilus@teaminina@xplatform`
    @Value("${jwt.secret:ad6527f8a03fbd724ddc68d4244c43f82b8056560ad62fa58d2e19dfb779e52a}")
    private String secret;

    // default: 1D
    @Value("${jwt.expiration:86400}")
    private Long expiration;

    private UserDetailsService userDetailsService;

    @Inject
    public void setUserDetailsService(UserDetailsService jwtUserDetailsService) {
        this.userDetailsService = jwtUserDetailsService;
    }

    @Override
    public String getIdFromToken(String token) {
        String id;
        try {
            final Claims claims = getClaimsFromToken(token);
            id = claims.getId();
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Long userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Long.parseLong(claims.get(CLAIM_KEY_USER_ID).toString());
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    @Override
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getRolesFromToken(String token) {
        List<String> roles;
        try {
            final Claims claims = getClaimsFromToken(token);
            roles = (List) claims.get(CLAIM_KEY_ROLES);
        } catch (Exception e) {
            roles = null;
        }
        return roles != null ? roles.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList()) : null;
    }

    @Override
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    @Override
    public Claims getClaimsFromToken(String token)
            throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        Claims claims;

        claims = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }


    private Date generateExpirationDate(String type) {
        if (type.equals("token")) {
            return new Date(System.currentTimeMillis() + expiration * 1000);
        } else {
            return new Date(System.currentTimeMillis() + expiration * 5 * 1000);
        }
    }

    @Override
    public Boolean isTokenExpired(String token) {
        try {
            getClaimsFromToken(token);
            return false;
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

    @Override
    public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    @Override
    public JwtAuthenticationResponse generateToken(String username) {
        Map claims = generateClaims(username);
        String token = doGenerateToken(claims);
        String refreshToken = doGenerateRefreshToken(claims);
        return new JwtAuthenticationResponse(token, refreshToken);
    }

    @Override
    public JwtAuthenticationResponse refreshToken(String username) {

        String newToken;
        String newRefreshToken;
        try {
            final Map claims = generateClaims(username);
            newToken = doGenerateToken(claims);
            newRefreshToken = doGenerateRefreshToken(claims);
        } catch (Exception e) {
            newToken = null;
            newRefreshToken = null;
        }
        return new JwtAuthenticationResponse(newToken, newRefreshToken);
    }

    private Map generateClaims(String username) {
        final JwtUser userDetails = (JwtUser) this.userDetailsService.loadUserByUsername(username);
        Map claims = new HashMap<>();

        claims.put(CLAIM_KEY_ID, UUID.randomUUID().toString());
        claims.put(CLAIM_KEY_USER_ID, userDetails.getId());
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ROLES, AuthorityUtils.authorityListToSet(userDetails.getAuthorities()));

        return claims;
    }

    private String doGenerateToken(Map claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate("token"))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private String doGenerateRefreshToken(Map claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate("refresh"))
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            this.getClaimsFromToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
