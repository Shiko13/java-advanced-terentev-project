package ru.otus.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenExtractorService {

    String extractToken(HttpServletRequest request);

    String extractTokenFromSecurityContext();
}