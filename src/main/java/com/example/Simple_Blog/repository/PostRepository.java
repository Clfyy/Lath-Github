package com.example.Simple_Blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Simple_Blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Kita bisa menambahkan method custom kalau perlu, tapi untuk sekarang sudah cukup dengan JpaRepository
}
