<<<<<<< HEAD:src/main/java/com/sms/businesslogic/Entity/Order.java
package com.sms.businesslogic.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
=======
package com.sms.businesslogic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
>>>>>>> main:src/main/java/com/sms/businesslogic/entity/Order.java
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
<<<<<<< HEAD:src/main/java/com/sms/businesslogic/Entity/Order.java

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

=======
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "_order")
public class Order {
>>>>>>> main:src/main/java/com/sms/businesslogic/entity/Order.java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private Date orderDate;
    private String orderStatus;
    private String deliveryStatus;

<<<<<<< HEAD:src/main/java/com/sms/businesslogic/Entity/Order.java
   /* @OneToOne(mappedBy = "reserve")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;*/


=======
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
>>>>>>> main:src/main/java/com/sms/businesslogic/entity/Order.java
//    @ManyToMany(fetch =FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @JoinTable(
//            name = "reserve_book",
//            joinColumns = {@JoinColumn(name = "reserveId")
//            },
//            inverseJoinColumns = {@JoinColumn(name = "bookId")}
//    )
//    private Set<Book> books=new HashSet<>();
<<<<<<< HEAD:src/main/java/com/sms/businesslogic/Entity/Order.java

=======
>>>>>>> main:src/main/java/com/sms/businesslogic/entity/Order.java
//    public void addBook(Book book){
//        this.books.add(book);
//        book.getReserves().add(this);
//    }
<<<<<<< HEAD:src/main/java/com/sms/businesslogic/Entity/Order.java

/*    @OneToMany(mappedBy="reserve",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ReserveBook> reserveBooks=new HashSet<>();*/
=======
//    @OneToMany(mappedBy="reserve",cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private Set<ReserveBook> reserveBooks=new HashSet<>();
>>>>>>> main:src/main/java/com/sms/businesslogic/entity/Order.java
}
