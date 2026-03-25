package ssurent.ssurentbe.domain.item.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import ssurent.ssurentbe.common.base.BaseEntity;
import ssurent.ssurentbe.domain.item.enums.Condition;
import ssurent.ssurentbe.domain.item.enums.Status;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "items",
uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_items_category_item_num",
                columnNames = {"category_id","item_num"}
        )
})
public class Items extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(
                    name = "fk_items_category",
                    foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE"
            ))
    private Category categoryId;

    @Column(name="item_name", nullable = false)
    private String itemName;

    @Column(name = "item_num", nullable = false)
    private String itemNum;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "item_condition")
    private Condition condition = Condition.KEEP;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void updateStatus(Status status){
        this.status = status;
    }

    public void updateCondition(Condition condition) {
        this.condition = condition;
    }

    public void softDelete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}