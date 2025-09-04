package com.example.backend.jwt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

@Component
public class JwtService {

    private final PrivateKey privateKey;

    private final PublicKey publicKey;

    private final long expiration = 1000 * 60 * 60;

    public JwtService(@Value("${jwt.private-key-path}") String privateKey,
            @Value("${jwt.public-key-path}") String publicKey) throws NoSuchAlgorithmException, IOException, Throwable {
        this.privateKey = loadPrivateKey(privateKey);
        this.publicKey = loadPublicKey(publicKey);
    }

    private PrivateKey loadPrivateKey(String path) throws IOException, Throwable, NoSuchAlgorithmException {
        String key = Files.readString(Paths.get(path))
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }

    private PublicKey loadPublicKey(String path) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String key = Files.readString(Paths.get(path))
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyBytes));
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(privateKey, SIG.RS384)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}