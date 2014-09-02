package com.johannesbrodwall.oauth2fun.ident;

import java.security.SecureRandom;

import lombok.Getter;
import lombok.Setter;

public class OauthClientSession {

    @Getter
    private String code;

    @Getter @Setter
    private String redirectUri;

    @Getter
    private String accessToken;
    private static SecureRandom secureRandom = new SecureRandom();

    public OauthClientSession() {
        this.code = Long.toString(secureRandom.nextLong(), 0x10);
    }

    public void redeemCode(String suppliedCode) {
        if (!suppliedCode.equals(code)) {
            throw new RuntimeException("Invalid code");
        }
        code = null;
        this.accessToken = Long.toString(secureRandom.nextLong(), 0x10);

    }

}
