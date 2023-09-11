package com.testbe.co.integrationtest;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testbe.co.SpringApp;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
class ControllerTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private WebApplicationContext context;
  @LocalServerPort
  private int port;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  
  
   private JsonNode getJSONFromFile(String filePath) throws IOException {
    try(InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)){
      JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
      return jsonNode;
    }
    catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  
  private String getPayload(String filePath) throws IOException {
	  String jsonString = mapper.writeValueAsString( getJSONFromFile(filePath) );
	  return jsonString;
  }

  @Test
  void testRetrieveServiceDocument() {
    final String xml = given()
        .accept(ContentType.XML)
        .when()
        .get("/testbe/")
        .then()
        .statusCode(HttpStatusCode.OK.getStatusCode())
        .contentType(ContentType.XML)
        .extract()
        .asString();

    final XmlPath path = new XmlPath(xml);
    final Collection<Node> n = ((Node) ((Node) path.get("service")).get("workspace")).get("collection");
    assertNotNull(n);
    assertFalse(n.isEmpty());
  }

  @Test
  void  testRetrieveMetadataDocument() {
    final String xml = given()
        .when()
        .get("/testbe/$metadata")
        .then()
        .statusCode(HttpStatusCode.OK.getStatusCode())
        .contentType(ContentType.XML)
        .extract()
        .asString();

    final XmlPath path = new XmlPath(xml);
    final Node n = ((Node) ((Node) path.get("edmx:Ed mx")).get("DataServices")).get("Schema");
    assertNotNull(n);
    assertEquals("testbe", n.getAttribute("Namespace"));
    assertNotNull(n.get("EntityContainer"));
  }

	

	
  @Test
  void  testCreateAppointmentInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("AppointmentInstance.json"))
        .when()
        .post("/testbe/Appointments")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    
  }
	
	
  
   
  
   @Test
  public void testSystemFilterOptionsAppointment() throws IOException {
  
  given()
        .contentType("application/json")
        .body(getPayload("AppointmentInstance.json"))
        .when()
        .post("/testbe/Appointments")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
   given()
            .when()
            .get("/testbe/Appointments?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).AppointmentId", equalTo("<<replace_with_keyFieldValue>>"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/testbe/Appointments/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
            
            
    
    } 
	
	

	
  @Test
  void  testCreatePetOwnerInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("PetOwnerInstance.json"))
        .when()
        .post("/testbe/PetOwners")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    
  }
	
	
  
   
  
   @Test
  public void testSystemFilterOptionsPetOwner() throws IOException {
  
  given()
        .contentType("application/json")
        .body(getPayload("PetOwnerInstance.json"))
        .when()
        .post("/testbe/PetOwners")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
   given()
            .when()
            .get("/testbe/PetOwners?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).Pet_ownerId", equalTo("<<replace_with_keyFieldValue>>"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/testbe/PetOwners/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
            
            
    
    } 
	
	

	
  @Test
  void  testCreateVeterianInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("VeterianInstance.json"))
        .when()
        .post("/testbe/Veterians")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    
  }
	
	
  
   
  
   @Test
  public void testSystemFilterOptionsVeterian() throws IOException {
  
  given()
        .contentType("application/json")
        .body(getPayload("VeterianInstance.json"))
        .when()
        .post("/testbe/Veterians")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
   given()
            .when()
            .get("/testbe/Veterians?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).Vet_id", equalTo("<<replace_with_keyFieldValue>>"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/testbe/Veterians/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
            
            
    
    } 
	
	

	
  @Test
  void  testCreateVisitInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("VisitInstance.json"))
        .when()
        .post("/testbe/Visits")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    
  }
	
	
  
   
  
   @Test
  public void testSystemFilterOptionsVisit() throws IOException {
  
  given()
        .contentType("application/json")
        .body(getPayload("VisitInstance.json"))
        .when()
        .post("/testbe/Visits")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
   given()
            .when()
            .get("/testbe/Visits?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).Visit_id", equalTo("<<replace_with_keyFieldValue>>"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/testbe/Visits/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
            
            
    
    } 
	
	

	
  @Test
  void  testCreateVaccineSchedulerInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("VaccineSchedulerInstance.json"))
        .when()
        .post("/testbe/VaccineSchedulers")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    
  }
	
	
  
   
  
   @Test
  public void testSystemFilterOptionsVaccineScheduler() throws IOException {
  
  given()
        .contentType("application/json")
        .body(getPayload("VaccineSchedulerInstance.json"))
        .when()
        .post("/testbe/VaccineSchedulers")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
   given()
            .when()
            .get("/testbe/VaccineSchedulers?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).Vaccine_id", equalTo("<<replace_with_keyFieldValue>>"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/testbe/VaccineSchedulers/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
            
            
    
    } 
	
	

	
  @Test
  void  testCreateImageInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("ImageInstance.json"))
        .when()
        .post("/testbe/Images")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    
  }
	
	
  
   
  
   @Test
  public void testSystemFilterOptionsImage() throws IOException {
  
  given()
        .contentType("application/json")
        .body(getPayload("ImageInstance.json"))
        .when()
        .post("/testbe/Images")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
   given()
            .when()
            .get("/testbe/Images?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).ImageId", equalTo("<<replace_with_keyFieldValue>>"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/testbe/Images/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
            
            
    
    } 
	
	

	
  @Test
  void  testCreatePetInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("PetInstance.json"))
        .when()
        .post("/testbe/Pets")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    
  }
	
	
  
   
  
   @Test
  public void testSystemFilterOptionsPet() throws IOException {
  
  given()
        .contentType("application/json")
        .body(getPayload("PetInstance.json"))
        .when()
        .post("/testbe/Pets")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
   given()
            .when()
            .get("/testbe/Pets?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).Pet_id", equalTo("<<replace_with_keyFieldValue>>"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/testbe/Pets/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
            
            
    
    } 
	
	

	
  @Test
  void  testCreateVisitSchedulerInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("VisitSchedulerInstance.json"))
        .when()
        .post("/testbe/VisitSchedulers")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    
  }
	
	
  
   
  
   @Test
  public void testSystemFilterOptionsVisitScheduler() throws IOException {
  
  given()
        .contentType("application/json")
        .body(getPayload("VisitSchedulerInstance.json"))
        .when()
        .post("/testbe/VisitSchedulers")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
   given()
            .when()
            .get("/testbe/VisitSchedulers?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).ScheduleVisit_id", equalTo("<<replace_with_keyFieldValue>>"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/testbe/VisitSchedulers/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
            
            
    
    } 
	
           
       
  
  
  
  
 
  @AfterEach
  void  teardown() {
    jdbcTemplate.execute("DELETE FROM testbe.Appointment");
    jdbcTemplate.execute("DELETE FROM testbe.PetOwner");
    jdbcTemplate.execute("DELETE FROM testbe.Veterian");
    jdbcTemplate.execute("DELETE FROM testbe.Visit");
    jdbcTemplate.execute("DELETE FROM testbe.VaccineScheduler");
    jdbcTemplate.execute("DELETE FROM testbe.Image");
    jdbcTemplate.execute("DELETE FROM testbe.Pet");
    jdbcTemplate.execute("DELETE FROM testbe.VisitScheduler");
     jdbcTemplate.execute("DELETE FROM testbe.VaccineSchedulerPetVaccine");
     jdbcTemplate.execute("DELETE FROM testbe.PetOwnerPetOwnerImage");
     jdbcTemplate.execute("DELETE FROM testbe.VisitSchedulerPetVisit");
     jdbcTemplate.execute("DELETE FROM testbe.PetPetImage");
     jdbcTemplate.execute("DELETE FROM testbe.VisitSchedulerVetPetVisitSchedular");
     jdbcTemplate.execute("DELETE FROM testbe.VaccineSchedulerVetVaccineSchedular");
     jdbcTemplate.execute("DELETE FROM testbe.PetVisits");
     jdbcTemplate.execute("DELETE FROM testbe.VisitSchedulerVisitSchedular");
     jdbcTemplate.execute("DELETE FROM testbe.AppointmentVetPetVaccineSchedular");
     jdbcTemplate.execute("DELETE FROM testbe.VeterianVetImage");
     jdbcTemplate.execute("DELETE FROM testbe.VaccineSchedulerVaccineDetails");
     jdbcTemplate.execute("DELETE FROM testbe.AppointmentPetappointment");
     jdbcTemplate.execute("DELETE FROM testbe.VaccineSchedulerVetPetVaccineSchedularAlert");
     jdbcTemplate.execute("DELETE FROM testbe.PetOwns");
     jdbcTemplate.execute("DELETE FROM testbe.PetOwnerBookAppointmentScheduleVisitVaccine");
     jdbcTemplate.execute("DELETE FROM testbe.VisitSchedulerPetVaccineSchedular");
     jdbcTemplate.execute("DELETE FROM testbe.AppointmentAppointmentDetails");
     jdbcTemplate.execute("DELETE FROM testbe.AppointmentVetpetappointment");
     jdbcTemplate.execute("DELETE FROM testbe.VaccineSchedulerPrescription");
     jdbcTemplate.execute("DELETE FROM testbe.VeterianExaminehealth");

    RestAssuredMockMvc.reset();
  }
}
