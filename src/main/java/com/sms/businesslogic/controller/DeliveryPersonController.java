package com.sms.businesslogic.controller;

import com.sms.businesslogic.entity.DeliveryPerson;
import com.sms.businesslogic.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/deliveryPerson")
public class DeliveryPersonController {
    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping
    public List<DeliveryPerson> getAllDeliveryPersons(){
        return DeliveryRepository.findAll();
    }

    @GetMapping ("/{id}")
    public DeliveryPerson getDeliveryPersonById(@PathVariable Long id){
        return DeliveryRepository.findById(id).orElse(null);
    }

    @PostMapping
    public DeliveryPerson createDeliveryPerson(@RequestBody DeliveryPerson deliveryPerson) {
        return DeliveryRepository.save(deliveryPerson);
    }

    @PutMapping("/{id}")
    public DeliveryPerson updateDeliveryPerson(@PathVariable Long id, @RequestBody DeliveryPerson updatedDeliveryPerson) {
        if (DeliveryRepository.existsById(id)) {
            updatedDeliveryPerson.setId(id);
            return DeliveryRepository.save(updatedDeliveryPerson);
        } else {
            return null; // Handle not found case
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDeliveryPerson(@PathVariable Long id) {
        DeliveryRepository.deleteById(id);
    }
}
