package com.example.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

//JpaRepository extends해서 @Repository 생략
//JpaRepository는 CRUD 함수를 포함하고있다.
public interface PostRepository extends JpaRepository<Post, Long> {
     @Transactional(readOnly=true)
     @Query(
             value = "SELECT * FROM post where layer=:layer and (title like %:title% or content like %:content%)", nativeQuery = true
     )
     Page<Post> selectOrigin(@Param("layer") Long layer, @Param("title") String title, @Param("content") String content, Pageable pageable);

     @Transactional(readOnly=true)
     @Query(value = "select * from post where originNo=:originNo and layer=:layer", nativeQuery = true)
     Page<Post> selectReply(@Param("originNo") Long originNo, @Param("layer") Long layer, Pageable pageable);

//     @Transactional(readOnly=true)
//     @Modifying
//     @Query("ALTER TABLE post AUTO_INCREMENT = 1 " +
//             "SET @COUNT = 0 " +
//             "UPDATE post SET post.id = @COUNT:=@COUNT+1 ")
//     void auto_increment();
}
