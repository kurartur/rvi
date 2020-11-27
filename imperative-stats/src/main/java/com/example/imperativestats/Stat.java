package com.example.imperativestats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stats", indexes = {
        @Index(name = "idx_company_id", columnList = "company_id"),
        @Index(name = "idx_room_type", columnList = "room_type"),
        @Index(name = "idx_unique", columnList = "company_id, room_id, date", unique = true)
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stat {

    @Id
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer amount;


}
