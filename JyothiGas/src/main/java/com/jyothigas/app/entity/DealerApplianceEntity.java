/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jyothigas.app.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rprashar
 */
@Entity
@Table(name = "DealerApplianceBooking")
public class DealerApplianceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "ApplianceId")
    private int applianceId;

    @Column(name = "BookingId")
    private int bookingId;

    @Column(name = "Quantity")
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(int applianceId) {
        this.applianceId = applianceId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
