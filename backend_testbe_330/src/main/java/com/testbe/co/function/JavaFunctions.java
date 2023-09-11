package com.testbe.co.function;

import com.testbe.co.model.Visit;
import com.testbe.co.model.Pet;
import com.testbe.co.model.PetOwner;
import com.testbe.co.model.VisitScheduler;
import com.testbe.co.model.Veterian;
import com.testbe.co.model.Appointment;
import com.testbe.co.model.VaccineScheduler;
import com.testbe.co.model.Image;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmFunction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmParameter;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.ODataFunction;
import com.testbe.co.repository.AppointmentRepository;
import com.testbe.co.repository.PetOwnerRepository;
import com.testbe.co.repository.VeterianRepository;
import com.testbe.co.repository.VisitRepository;
import com.testbe.co.repository.VaccineSchedulerRepository;
import com.testbe.co.repository.ImageRepository;
import com.testbe.co.repository.PetRepository;
import com.testbe.co.repository.VisitSchedulerRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
public class JavaFunctions implements ODataFunction {


    
    
}
   
