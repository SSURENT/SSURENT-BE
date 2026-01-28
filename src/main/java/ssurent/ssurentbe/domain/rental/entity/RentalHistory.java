package ssurent.ssurentbe.domain.rental.entity;

import jakarta.persistence.*;
import lombok.*;
import ssurent.ssurentbe.common.base.BaseEntity;
import ssurent.ssurentbe.domain.assists.entity.Assists;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.rental.enums.Status;
import ssurent.ssurentbe.domain.users.entity.Users;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "rental_history")
public class RentalHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assist_id")
    private Assists assistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Items itemId;

    @Column(name = "rental_date")
    private LocalDateTime rentalDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "status")
    private Status status;
}
