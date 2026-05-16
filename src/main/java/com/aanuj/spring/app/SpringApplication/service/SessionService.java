package com.aanuj.spring.app.SpringApplication.service;

import com.aanuj.spring.app.SpringApplication.entities.Session;
import com.aanuj.spring.app.SpringApplication.entities.User;
import com.aanuj.spring.app.SpringApplication.repositories.SessionRepository;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final int SESSION_LIMIT = 2;
    private final SessionRepository sessionRepository;

    public void generateNewSession(User user, String refreshToken) {
        List<Session> sessions = sessionRepository.findByUser(user);
        if(CollectionUtils.isEmpty(sessions) && sessions.size() == SESSION_LIMIT) {
            sessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session leastRecentlyUsedSession = sessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }
        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("Session not found for refresh token: " + refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
