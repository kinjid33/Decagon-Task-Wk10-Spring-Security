package com.decagontasks.fashionblogwithsecurity.repository;

import com.decagontasks.fashionblogwithsecurity.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
    @Query(value = """
      select t from Token t inner join UserModel u\s
      on t.user.id = u.id\s
      where u.id = :userId and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken (String token);
}
