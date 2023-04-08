/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.security.SecureRandom;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ASUS
 */
//encodage du mot de passe en utilisant le BCrypt
public class BcryptHasher {
    private static final int COST_FACTOR = 13;

    public String hash(String rawPassword) {
        // Generate a different salt for each user
        String salt = BCrypt.gensalt(COST_FACTOR, new SecureRandom());
        String hashedPassword = BCrypt.hashpw(rawPassword, salt);
        if (salt.startsWith("$2y$")) {
            // replace the prefix with "$2y$" if it starts with "$2a$"
            salt = "$2a$" + salt.substring(4);
            hashedPassword = BCrypt.hashpw(rawPassword, salt);
        }
        return hashedPassword;
    }

    public boolean checkPassword(String passwordHash, String userInput) {
        if (passwordHash == null || passwordHash.isEmpty()) {
            return false;
        }

        return BCrypt.checkpw(userInput, passwordHash);
    }
}


