package integration.pageobject;

import org.openqa.selenium.How;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MemberForm {

	@FindBy(how = How.ID, using = "name")
	protected WebElement name;

	@FindBy(how = How.ID, using = "email")
	protected WebElement email;

	@FindBy(how = How.ID, using = "password")
	protected WebElement password;

	@FindBy(how = How.ID, using = "name.errors")
	protected WebElement nameError;

	@FindBy(how = How.ID, using = "email.errors")
	protected WebElement emailError;

	@FindBy(how = How.ID, using = "password.errors")
	protected WebElement passwordError;

	@FindBy(how = How.XPATH, using = "//input[@type='submit']")
	protected WebElement submit;

	public WebElement getName() {
		return name;
	}

	public void setName(WebElement name) {
		this.name = name;
	}

	public WebElement getEmail() {
		return email;
	}

	public void setEmail(WebElement email) {
		this.email = email;
	}

	public WebElement getPassword() {
		return password;
	}

	public void setPassword(WebElement password) {
		this.password = password;
	}

	public WebElement getNameError() {
		return nameError;
	}

	public void setNameError(WebElement nameError) {
		this.nameError = nameError;
	}

	public WebElement getEmailError() {
		return emailError;
	}

	public void setEmailError(WebElement emailError) {
		this.emailError = emailError;
	}

	public WebElement getPasswordError() {
		return passwordError;
	}

	public void setPasswordError(WebElement passwordError) {
		this.passwordError = passwordError;
	}

	public WebElement getSubmit() {
		return submit;
	}

	public void setSubmit(WebElement submit) {
		this.submit = submit;
	}

}
