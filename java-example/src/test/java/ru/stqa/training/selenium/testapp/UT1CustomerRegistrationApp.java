package ru.stqa.training.selenium.testapp;

import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.model.Customer;
import ru.stqa.training.selenium.pages.PageParams;
import ru.stqa.training.selenium.pages.RegistrationPage;
import ru.stqa.training.selenium.pages.AdminPanelLoginPage;
import ru.stqa.training.selenium.pages.CustomerListPage;

import java.util.*;


public class UT1CustomerRegistrationApp {

    private PageParams pageParams;
    private RegistrationPage registrationPage;
    private AdminPanelLoginPage adminPanelLoginPage;
    private CustomerListPage customerListPage;


    public void initPages(DriverBase drvBase) {

        pageParams = new PageParams(drvBase);

        registrationPage    = new RegistrationPage(pageParams);
        adminPanelLoginPage = new AdminPanelLoginPage(pageParams);
        customerListPage    = new CustomerListPage(pageParams);
    }

    public void registerNewCustomer(Customer customer) {
        registrationPage.open();
        registrationPage.tax_idInput.sendKeys(customer.getTax_id());
        registrationPage.companyInput.sendKeys(customer.getCompany());
        registrationPage.firstnameInput.sendKeys(customer.getFirstname());
        registrationPage.lastnameInput.sendKeys(customer.getLastname());
        registrationPage.address1Input.sendKeys(customer.getAddress1());
        registrationPage.postcodeInput.sendKeys(customer.getPostcode());
        registrationPage.cityInput.sendKeys(customer.getCity());
        registrationPage.selectCountry(customer.getCountry());
        //registrationPage.selectZone(customer.getZone());
        registrationPage.emailInput.sendKeys(customer.getEmail());
        registrationPage.phoneInput.sendKeys(customer.getPhone());
        registrationPage.passwordInput.sendKeys(customer.getPassword());
        registrationPage.confirmedPasswordInput.sendKeys(customer.getPassword());

        registrationPage.DriverSleep();

        registrationPage.takeScreenshot("ScreenOne");

        registrationPage.createAccountButton.click();

        registrationPage.takeScreenshot( "ScreenTwo");
    }

    public Set<String> getCustomerIds() {
        if (adminPanelLoginPage.open().isOnThisPage()) {
            adminPanelLoginPage.enterUsername("admin").enterPassword("admin").submitLogin();
        }

        return customerListPage.open().getCustomerIds();
    }

}
