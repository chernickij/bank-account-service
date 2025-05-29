package com.chernickij.bankaccount.sevice.impl;

import com.chernickij.bankaccount.dto.UpdateUserRequest;
import com.chernickij.bankaccount.dto.UserResponse;
import com.chernickij.bankaccount.entity.User;
import com.chernickij.bankaccount.exception.NotFoundException;
import com.chernickij.bankaccount.repository.EmailRepository;
import com.chernickij.bankaccount.repository.UserRepository;
import com.chernickij.bankaccount.sevice.UserService;
import com.chernickij.bankaccount.dto.UserSearch;
import com.chernickij.bankaccount.sevice.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;

    @Override
    public UserResponse getUser(final Long userId) {
        log.info("Getting user by id: {}", userId);
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NotFoundException.ResourceType.USER, userId.toString()));
        return UserMapper.userToGetUserResponse(user);
    }

    @Override
    public UserResponse updateUser(final Long userId, final UpdateUserRequest request) {
        log.info("Updating user by id: {}", userId);
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NotFoundException.ResourceType.USER, userId.toString()));

        user.setName(request.name());
        user.setDateOfBirth(request.dateOfBirth());

        userRepository.save(user);

        return UserMapper.userToGetUserResponse(user);
    }

    @Override
    public Page<UserResponse> searchUsers(final UserSearch userSearch) {
        log.info("Searching users with criteria: {}", userSearch);

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        final Root<User> userRoot = criteriaQuery.from(User.class);

        final List<Predicate> predicates = new ArrayList<>();
        if (userSearch.getDateOfBirth() != null) {
            predicates.add(cb.greaterThan(userRoot.get("dateOfBirth"), userSearch.getDateOfBirth()));
        }
        if (userSearch.getName() != null && !userSearch.getName().isEmpty()) {
            predicates.add(cb.like(userRoot.get("name"), userSearch.getName() + "%"));
        }
        if (userSearch.getPhone() != null && !userSearch.getPhone().isEmpty()) {
            predicates.add(cb.equal(userRoot.join("phones").get("phone"), userSearch.getPhone()));
        }
        if (userSearch.getEmail() != null && !userSearch.getEmail().isEmpty()) {
            predicates.add(cb.equal(userRoot.join("emails").get("email"), userSearch.getEmail()));
        }
        criteriaQuery.select(userRoot).where(predicates.toArray(new Predicate[0]));

        final Pageable pageable = PageRequest.of(userSearch.getPage(), userSearch.getSize());
        final List<UserResponse> users = entityManager.createQuery(criteriaQuery)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(UserMapper::userToGetUserResponse)
                .collect(Collectors.toList());

        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(User.class)));
        final Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(users, pageable, count);
    }

    @Override
    public UserDetails getByEmail(String email) {
        return emailRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(NotFoundException.ResourceType.EMAIL, email)).getUser();
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }
}
