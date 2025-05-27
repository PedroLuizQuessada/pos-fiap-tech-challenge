package com.example.tech_challenge.repo.user;

import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.persistence.jpa.UserJpa;
import com.example.tech_challenge.mapper.persistence.UserJpaMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJpaRepositoryImpl implements UserRepository {

    @Autowired
    private UserJpaMapper userJpaMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Query query = entityManager.createQuery("SELECT u FROM UserJpa u WHERE u.login = :login AND u.password = :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        List<?> result = query.getResultList();
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(((UserJpa) result.getFirst()).toEntity());
    }

    @Override
    public Long countByEmail(String email) {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM UserJpa u WHERE u.email = :email");
        query.setParameter("email", email);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long countByLogin(String login) {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM UserJpa u WHERE u.login = :login");
        query.setParameter("login", login);
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public User save(User user) {
        UserJpa userJpa = userJpaMapper.map(user);
        return entityManager.merge(userJpa).toEntity();
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserJpa> optionalUserJpa = Optional.ofNullable(entityManager.find(UserJpa.class, id));
        return optionalUserJpa.map(UserJpa::toEntity);
    }

    @Override
    @Transactional
    public void delete(User student) {
        UserJpa studentMysql = userJpaMapper.map(student);
        studentMysql = entityManager.merge(studentMysql);
        entityManager.remove(studentMysql);
    }
}
