package com.laattre.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
