package com.pagacz.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "FLAT_OFFER")
@SequenceGenerator(name = "FLATOFFERIDSEQUENCE", sequenceName = "FLATOFFERIDSEQUENCE", allocationSize = 1)
public class FlatOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FLATOFFERIDSEQUENCE")
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "SOURCE")
    private String source;
    @Column(name = "LINK")
    private String link;
    @Column(name = "COMMENT")
    private String comment = "";
    @Column(name = "PRICE")
    private Integer price;
    @Column(name = "ORIGINAL_PRICE")
    private Integer originalPrice;
    @Column(name = "SPACE")
    private Double space;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "INSERT_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime insertDate;
    @Column(name = "LAST_MODIFIED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime lastModifiedTime;
    @Column(name = "WRITE_TO_DOCS")
    private Character writeToDocs = 'N';
    @Column(name = "SEND_BY_EMAIL")
    private Character sendByEmail = 'N';
    @Column(name = "WRITE_TO_DOCS_TIME")
    private LocalDateTime writeToDocsTime;
    @Column(name = "SEND_BY_EMAIL_TIME")
    private LocalDateTime sendByEmailTime;

    public FlatOffer(String link, String title, String source, Integer price, Integer originalPrice, Double space, String address) {
        this.link = link;
        this.title = title;
        this.source = source;
        this.price = price;
        this.originalPrice = originalPrice;
        this.space = space;
        this.address = address;
    }
}
