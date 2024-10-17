package com.example.categoryservice.repository;


import com.example.categoryservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT c.id FROM Category c WHERE c.name = :categoryName")
    String existsByName(@Param("categoryName") String categoryName);

}
