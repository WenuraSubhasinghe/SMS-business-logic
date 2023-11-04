package com.sms.businesslogic.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
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

   /* @OneToOne(mappedBy = "reserve")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;*/


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

/*    @OneToMany(mappedBy="reserve",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ReserveBook> reserveBooks=new HashSet<>();*/
}
