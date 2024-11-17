package Jazz.dot_lite.Config;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private String jwtSecret = "af60addca9ea3e3c099551e1b6576c9966dce0a33de879dd7e160f86dbd872ca236d6e9ee66fb6e30039fe7c345324a10f3d0741b0600fa7a45df4c6691eff4f4209767ed39f51e37717d8feecd5dd14fc34ebe619e6a29ae91d9ffe134cb5718bec0b3680d6ae7fc09e67763fe7c05d05d3ba69f47211163852633755b7f861132b0c98f8d7c1af9152d547408e676867a0a32fb525a4354180f5fb6b2dc23b5faa4155b8db63385f96259a90b6ee0e74a5b90a4f0f4fa96fafc296c64588b5c009b3829ae2e1d69a1cf7569b50a65fa553350495d18816f785f961c970c0a9cb9c8da25cc5e9fa4a3e9527a132d616b232d1ee21c3bf6dc8d9e3376e2e82c0";
    private long jwtExpirationDate = 3600000; //1h = 3600s and 3600*1000 = 3600000 milliseconds

    public String generateToken(Authentication authentication) throws JOSEException {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        // JWT claims
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(currentDate)
                .expirationTime(expireDate)
                .build();

        // Signing the JWT
        JWSSigner signer = new MACSigner(jwtSecret); // Replace with your secret key
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public String getUsername(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Extract claims
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Verifier with the same secret key used for signing
            JWSVerifier verifier = new MACVerifier(jwtSecret);

            return signedJWT.verify(verifier) && !isTokenExpired(signedJWT);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws java.text.ParseException {
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return expirationTime.before(new Date());
    }
}
