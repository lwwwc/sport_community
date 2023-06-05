package com.lwc.sportcommunity.hwkit;

import lombok.Data;

/**
 * Create by LWC on 2023/5/24 17:45
 */
@Data
public class TokenResponse {
    private String scope;
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
    private String id_token;
}
