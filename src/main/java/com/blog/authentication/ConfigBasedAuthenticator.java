package com.blog.authentication;

import com.cksource.ckfinder.authentication.Authenticator;

import javax.inject.Named;

/**
 * WARNING: Your authenticator should never simply return true. By doing so,
 * you are allowing "anyone" to upload and list the files on your server.
 * You should implement some kind of session validation mechanism to make
 * sure that only trusted users can upload or delete your files.
 */
@Named
public class ConfigBasedAuthenticator implements Authenticator {
    @Override
    public boolean authenticate() {
        return true;
    }
}
