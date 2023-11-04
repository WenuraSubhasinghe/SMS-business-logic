package com.sms.businesslogic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private Date orderDate;
    private String orderStatus;
    private String deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private Payment payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private Delivery delivery;

//    @OneToOne(mappedBy = "reserve")
//    private Courier courier;
//    @ManyToOne
//    @JoinColumn(name = "userId")
//    @JsonBackReference
//    private User user;
//    @ManyToMany(fetch =FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @JoinTable(
//            name = "reserve_book",
//            joinColumns = {@JoinColumn(name = "reserveId")
//            },
//            inverseJoinColumns = {@JoinColumn(name = "bookId")}
//    )
//    private Set<Book> books=new HashSet<>();
//    public void addBook(Book book){
//        this.books.add(book);
//        book.getReserves().add(this);
//    }
//    @OneToMany(mappedBy="reserve",cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private Set<ReserveBook> reserveBooks=new HashSet<>();
}