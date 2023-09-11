package com.testbe.co.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;
import javax.persistence.*;

import com.testbe.co.model.Visit;
import com.testbe.co.model.Pet;
import com.testbe.co.model.PetOwner;
import com.testbe.co.model.VisitScheduler;
import com.testbe.co.model.Veterian;
import com.testbe.co.model.Appointment;
import com.testbe.co.model.VaccineScheduler;
import com.testbe.co.model.Image;

@Entity(name = "PetOwns")
@Table(schema = "\"testbe\"", name = "\"PetOwns\"")
@Data
public class PetOwns{

 	@Id
    @Column(name = "\"Id\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "\"Pet_id\"")
	private Integer pet_id;

    
    @Column(name = "\"Pet_ownerId\"")
    private Integer pet_ownerId;
 
}