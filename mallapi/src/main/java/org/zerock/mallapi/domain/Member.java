package org.zerock.mallapi.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "memberRoleList")
public class Member {

    @Id
    private String email;

    private String pw;

    private String nickname;

    // Boolean 타입은 null 값을 허용하고 boolean 타입은 null을 가질 수 없다.
    // Boolean 으로 만들면 롬복에 의해 생성되는 getter, setter 메소드는 getXXX 로 메소드명이 명명되지만,
    // boolean 으롬 만들면 메소드명은 isXXX 가 된다.
    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();

    public void addRole(MemberRole memberRole) {
        memberRoleList.add(memberRole);
    }

    public void clearRole() {
        memberRoleList.clear();
    }

    public void changePw(String pw) {
        this.pw = pw;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeSocial(boolean social) {
        this.social = social;
    }
}
