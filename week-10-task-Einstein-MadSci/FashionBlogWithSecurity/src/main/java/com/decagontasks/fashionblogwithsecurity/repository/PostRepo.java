package com.decagontasks.fashionblogwithsecurity.repository;

import com.decagontasks.fashionblogwithsecurity.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<PostModel, Long> {
    boolean existsByTitle(String title);

}

