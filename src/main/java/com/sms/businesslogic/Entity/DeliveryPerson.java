package com.sms.businesslogic.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import javax.xml.transform.sax.SAXResult;

@Entity
public class DeliveryPerson {
    @Id
    private long id;
    private String name;
    private String phoneNumber;
    private String vehicleNumber;
}
