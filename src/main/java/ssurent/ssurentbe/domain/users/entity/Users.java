package ssurent.ssurentbe.domain.users.entity;

import lombok.*;
import jakarta.persistence.*;
import ssurent.ssurentbe.common.base.BaseEntity;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "Users")
public class Users extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_Num", unique = true)
    private String studentNum;

    @Column(name = "name")
    private String name;

    @Column(name = "ph_num")
    private String phoneNum;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "role")
    private Role role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    //회원 삭제처리
    public void withdraw(){
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.name = null;
        this.phoneNum = null;
        this.studentNum = null;
    }

    //회원 복구
    public void restore(){
        this.deleted = false;
        this.deletedAt = null;
    }

    public void updatePhoneNumber(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void updateStatus(Status status){
        this.status = status;
    }

    public void updateInfo(String name, String phoneNum){
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
