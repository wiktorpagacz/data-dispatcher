package com.pagacz.database.dto;

import com.google.api.client.util.DateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlatOfferDto extends BaseDto {

    private Long id;
    private String title;
    private String source;
    private String link;
    private String comment = "-";
    private Integer price;
    private Integer originalPrice;
    private Double space;
    private String address;
    private LocalDateTime insertDate;

    public FlatOfferDto() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getLink() {
        return link;
    }

    public String getComment() {
        return comment;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setSpace(Double space) {
        this.space = space;
    }

    public double getSpace() {
        return space;
    }

    public String getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }


    @Override
    public String getUniqueIdentifier() {
        return this.link;
    }

    @Override
    public List<Object> getColumnsForSheet() {
        return Arrays.asList(this.getLink(),
                this.getId(),
                this.getTitle(),
                this.getAddress(),
                this.getPrice(),
                this.getOriginalPrice(),
                this.getSpace(),
                this.getSource(),
                this.getComment(),
                insertDate != null ? DateTime.parseRfc3339(this.getInsertDate().toString()) : null);
    }

    @Override
    public List<Object> getColumnsForMail() {
        return Arrays.asList(this.getSource(),
                this.getTitle(),
                this.getPrice(),
                this.getSpace(),
                this.getAddress(),
                this.getLink());
    }

    @Override
    public List<String> getMailTableHeaders() {
        return new ArrayList<>(List.of("Serwis",
                "Komentarz",
                "Tytuł",
                "Cena",
                "Wielkość",
                "Adres",
                "Link"));
    }
}
