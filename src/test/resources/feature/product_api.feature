Feature: Product API

  Scenario: Add a new product
    Given I have a product with name "Product 1" and price "100"
    When I send a POST request to "/addProduct"
    Then the response status should be 201
    And the response should contain the product name "Product 1"

  Scenario: Get all products
    When I send a GET request to "/products"
    Then the response status should be 200
    And the response should contain a list of products