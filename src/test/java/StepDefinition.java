import io.cucumber.java.en.*;

import static org.junit.Assert.*;



public class StepDefinition {
	private String clientName;
	private String address;
	private String contactPerson;
	private String email;
	private Exception exception;
	private ContainerApp containerApp = new ContainerApp();
	
	
	
	// REGISTER CLIENT
	@Given("information about a client; name {string}, address {string}, contactperson with name {string}, email of contactperson {string}")
	public void information_about_a_client_name_address_contactperson_with_name_email_of_contactperson(String clientName, String clientAddress, String contactPerson, String email) {
	    this.clientName = clientName;
	    this.address = clientAddress;
	    this.contactPerson = contactPerson;
	    this.email = email;
	}
	
	@Given("a list of clients with attributes; name: {string}, address: {string}, contactperson name {string}, contactperson email {string} and name: {string}, address: {string}, contactperson name: {string}, contactperson email: {string}")
	public void a_list_of_clients_with_attributes_name_address_contactperson_name_contactperson_email_and_name_address_contactperson_name_contactperson_email(String clientName1, String clientAddress1, String contactPerson1, String email1, String clientName2, String clientAddress2, String contactPerson2, String email2) throws Exception {
	    containerApp.registerClient(clientName1, clientAddress1, contactPerson1, email1);
	    containerApp.registerClient(clientName2, clientAddress2, contactPerson2, email2);
	}

	@When("registering the client")
	public void registering_the_client() {
	    try {
			containerApp.registerClient(clientName, address, contactPerson, email);
		} catch (Exception e) {
			exception = e;
		}
	}

	@Then("Client is registered")
	public void client_is_registered() {
	    assertTrue(containerApp.isClientRegistered(clientName));
	}

	@Then("Client registration failed")
	public void client_registration_failed() {
	    assertNotNull(exception);
	}
}