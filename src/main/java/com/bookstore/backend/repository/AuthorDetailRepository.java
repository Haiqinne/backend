package com.bookstore.backend.repository;

import com.bookstore.backend.entity.AuthorDetail;
import com.bookstore.backend.entity.AuthorDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorDetailRepository extends JpaRepository<AuthorDetail, AuthorDetailId> {

    List<AuthorDetail> findByProduct_ProductId(Long productId);

    List<AuthorDetail> findByAuthor_AuthorId(Long authorId);
}