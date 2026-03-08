package ssurent.ssurentbe.domain.rental.entity;

import jakarta.persistence.*;
import lombok.*;
import ssurent.ssurentbe.common.base.BaseEntity;
import ssurent.ssurentbe.domain.rental.enums.ProblemType;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "rental_report")
public class RentalReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    private RentalHistory rentalHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "problem_type", nullable = false)
    private ProblemType problemType;

    @Column(name = "description")
    private String description;

    @Column(name = "reported_at", nullable = false)
    private LocalDateTime reportedAt;

    @Builder.Default
    @Column(name = "is_resolved", nullable = false)
    private boolean resolved = false;

    public void resolve() {
        this.resolved = true;
    }

    public static RentalReport of(RentalHistory rentalHistory, ProblemType problemType, String description) {
        return RentalReport.builder()
                .rentalHistory(rentalHistory)
                .problemType(problemType)
                .description(description)
                .reportedAt(LocalDateTime.now())
                .build();
    }
}