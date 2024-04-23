package com.pagacz.database.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table
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

    public FlatOffer() {
    }

    public FlatOffer(String link, String title, String source, Integer price, Integer originalPrice, Double space, String address) {
        this.link = link;
        this.title = title;
        this.source = source;
        this.price = price;
        this.originalPrice = originalPrice;
        this.space = space;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSpace() {
        return space;
    }

    public void setSpace(Double space) {
        this.space = space;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Character getWriteToDocs() {
        return writeToDocs;
    }

    public void setWriteToDocs(Character writeToDocs) {
        this.writeToDocs = writeToDocs;
    }

    public Character getSendByEmail() {
        return sendByEmail;
    }

    public void setSendByEmail(Character sendByEmail) {
        this.sendByEmail = sendByEmail;
    }

    public LocalDateTime getWriteToDocsTime() {
        return writeToDocsTime;
    }

    public void setWriteToDocsTime(LocalDateTime writeToDocsTime) {
        this.writeToDocsTime = writeToDocsTime;
    }

    public LocalDateTime getSendByEmailTime() {
        return sendByEmailTime;
    }

    public void setSendByEmailTime(LocalDateTime sendByEmailTime) {
        this.sendByEmailTime = sendByEmailTime;
    }
}
