Feature: Login to Swag Labs page

  Background:
    Given User is on Swag Labs page "https://www.saucedemo.com/"

  @ValidCredentials
  Scenario Outline: Login with valid credentials

    When User enters username as "<username>" and password as "<password>"
    And User click on Login button
    Then User should be able to login successfully and new page open
    Then Add products to the Cart
    And Check number of products in the Cart
    Then Remove products from Cart
    And Check products are removed from Cart
    Then Select again products to the Cart
    And Check again number of products in the Cart
    Then Go to Cart page
    And Verify redirect to Shopping Cart page
    Then Verify products in the Cart
    Then Go to Checkout page
    And Verify redirect to Shopping Checkout page
    Then Try to submit Empty form
    And Verify error messages form Empty form
    Then Clear errors from Submit page
    And Submit correct filled Submit form with "<firstname>", "<lastname>" and "<postalcode>"
    Then Verify redirect to Checkout Overview page
    And Verify correct products, prices and buttons are displayed
    Then Complete the Order
    And Verify redirect to checkout Complete page
    Then Logout
    And Verify redirect to Login page


    Examples:
      | username      | password     | firstname     | lastname     | postalcode |
      | standard_user | secret_sauce | Iliya         | Andreev      | 1505       |


