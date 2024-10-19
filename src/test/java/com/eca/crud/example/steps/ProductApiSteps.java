package com.eca.crud.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductApiSteps {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<String> response;
    private String productName;
    private String productPrice;

    @Given("I have a product with name {string} and price {string}")
    public void i_have_a_product_with_name_and_price(String name, String price) {
        this.productName = name;
        this.productPrice = price;
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer YOUR_JWT_TOKEN"); // Replace with a valid token
        String productJson = String.format("{\"name\":\"%s\", \"price\":%s}", productName, productPrice);
        HttpEntity<String> entity = new HttpEntity<>(productJson, headers);
        response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer YOUR_JWT_TOKEN"); // Replace with a valid token
        HttpEntity<String> entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

    @Then("the response should contain the product name {string}")
    public void the_response_should_contain_the_product_name(String expectedProductName) {
        assertTrue(Objects.requireNonNull(response.getBody()).contains(expectedProductName));
    }

    @Then("the response should contain a list of products")
    public void the_response_should_contain_a_list_of_products() {
        // Here you might want to check if the response is a valid JSON array
        assertTrue(response.getBody().startsWith("[") && response.getBody().endsWith("]"),
                "Response body should be a JSON array");
    }
}
