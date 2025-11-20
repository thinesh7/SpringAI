package com.ai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "user_id")
    private Long id;
	
    @Column(name = "username")
    private String username;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "ssn")
    private String ssn;
    
}
