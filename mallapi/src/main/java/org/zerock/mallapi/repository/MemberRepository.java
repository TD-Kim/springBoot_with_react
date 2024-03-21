package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mallapi.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"memberRoleList"})    // 이걸 사용하지 않으면 role 은 가져오지 않는다.
    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Member getWithRoles(@Param("email") String email);
}
