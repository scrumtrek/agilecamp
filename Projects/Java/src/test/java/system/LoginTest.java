//package system;
//
//import java.util.regex.Pattern;
//import java.util.concurrent.TimeUnit;
//import org.junit.*;
//import org.openqa.selenium.*;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.Select;
//
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
//import static org.fest.assertions.Assertions.*;
//
//public class LoginTest {
//	private WebDriver driver;
//	private String baseUrl;
//	private StringBuffer verificationErrors = new StringBuffer();
//	@Before
//	public void setUp() throws Exception {
//		driver = new FirefoxDriver();
//		baseUrl = "http://localhost:8080/Habitator01";
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//
//	@Test
//	public void shouldAuthentificateWhenCorrectCredentials() throws Exception {
//		driver.get(baseUrl);
//		driver.findElement(By.linkText("вход")).click();
//		driver.findElement(By.name("name")).clear();
//		driver.findElement(By.name("name")).sendKeys("bender");
//		driver.findElement(By.name("password")).clear();
//		driver.findElement(By.name("password")).sendKeys("password");
//		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
//
//		assertThat(driver.getTitle()).isEqualTo("Главная");
//	}
//
//	@Test
//	public void shouldNotAuthentificateWhenIncorrectCredentials() throws Exception {
//		driver.get(baseUrl);
//		driver.findElement(By.linkText("вход")).click();
//		driver.findElement(By.name("name")).clear();
//		driver.findElement(By.name("name")).sendKeys("noname");
//		driver.findElement(By.name("password")).clear();
//		driver.findElement(By.name("password")).sendKeys("nopassword");
//		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
//		assertThat(driver.getTitle()).isEqualTo("Вход");
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		driver.quit();
//		String verificationErrorString = verificationErrors.toString();
//		if (!"".equals(verificationErrorString)) {
//			fail(verificationErrorString);
//		}
//	}
//
//	private boolean isElementPresent(By by) {
//		try {
//			driver.findElement(by);
//			return true;
//		} catch (NoSuchElementException e) {
//			return false;
//		}
//	}
//}
//
