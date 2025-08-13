package entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Simple SHA-256 password hasher. Produces a lowercase hex string.
 * This is deterministic and has no salt by design to minimize changes.
 * For production, prefer a salted, slow hash (e.g., bcrypt/Argon2/PBKDF2).
 */
public final class PasswordHasher {
    private PasswordHasher() {
    }

    /**
     * Hash a plaintext password using SHA-256 and return a lowercase hex string.
     */
    public static String hash(String plaintext) {
        if (plaintext == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(plaintext.getBytes(StandardCharsets.UTF_8));
            return toHex(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    /**
     * Check if the candidate plaintext matches the stored SHA-256 hex hash.
     */
    public static boolean matches(String plaintext, String storedHash) {
        if (storedHash == null)
            return false;
        if (plaintext == null)
            return false;
        String hashed = hash(plaintext);
        return constantTimeEquals(hashed, storedHash);
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null)
            return false;
        if (a.length() != b.length())
            return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
