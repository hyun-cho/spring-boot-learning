package org.zerock.ex2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex2.entity.Memo;

// JpaRepository를 사용해서 다양한 기능 제공
// 기본적인것만은 CrudRepository, 근데 그냥 JpaRepository를 사용하는게 속 편하다.
public interface MemoRepository extends JpaRepository<Memo, Long> {

}
