package de.kstm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;

import de.kstm.helper.IntegrationTestProperties;
import de.kstm.model.Person;

public class PersonControllerIT {

    private RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
     
    @BeforeClass
    public static void setupClass() throws FileNotFoundException, IOException {
        RestAssured.baseURI = IntegrationTestProperties.getProperty("location.baseUri");
        RestAssured.port = Integer.valueOf(IntegrationTestProperties.getProperty("service.person.port"));
        RestAssured.basePath = IntegrationTestProperties.getProperty("service.person.path");
    }

    @Before
    public void setup() {
        requestSpecBuilder.setContentType(ContentType.JSON).addHeader("Accept",
                ContentType.JSON.getAcceptHeader());
    }

    @Test
    public void add_new_user_test() {
        // arrange
        Person newPerson = new Person();
        newPerson.setName("Name");
        newPerson.setFirstname("Firstname");

        requestSpecBuilder.setBody(newPerson);
        RestAssured.given(requestSpecBuilder.build()).when().post("/").then()
                .statusCode(201)
                .header("location", Matchers.containsString("persons/"));
    }
}
