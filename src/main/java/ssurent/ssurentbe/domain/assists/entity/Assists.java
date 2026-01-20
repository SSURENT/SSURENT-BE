package ssurent.ssurentbe.domain.assists.entity;

import jakarta.persistence.*;
import lombok.*;
import ssurent.ssurentbe.common.base.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "Assists")
public class Assists extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    //도우미 삭제
    public void withdraw(){
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
        this.name = null;
    }
}