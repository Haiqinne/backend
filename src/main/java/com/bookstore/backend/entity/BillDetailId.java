package com.bookstore.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailId implements Serializable {

    private Long bill;
    private Long product;
}