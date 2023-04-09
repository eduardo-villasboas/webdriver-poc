import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration


/*
Getting started with drivers and web driver manager (WebDriverManager)
In the documentation bellow there are some useful information about how to configure
the web-drivers using different strategies
https://www.selenium.dev/documentation/webdriver/getting_started/install_drivers/

documentation pages

web-driver manager
https://bonigarcia.dev/webdrivermanager/

first script
https://www.selenium.dev/documentation/webdriver/getting_started/first_script/

web-driver docs
https://www.selenium.dev/documentation/webdriver/

 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Pesquisar melhor
class MainKtTest {

    private lateinit var driver: WebDriver


    companion object {
        @BeforeAll
        @JvmStatic
        fun setupAll() {
            WebDriverManager.chromedriver().setup()
        }
    }

    @BeforeEach
    fun setup() {
        var options = ChromeOptions()
        options.addArguments("--remote-allow-origins=*")
        driver = ChromeDriver(options)
    }
    /*
    It is possible to configure driver by using a docker image, but it takes almost the double of
    the time to run when compared with traditional approach. Maybe select it by an environment variable
    globally could be a great idea once running in docker could be better for run in CI/CD

    private val wdm = WebDriverManager.chromedriver().browserInDocker()

    @BeforeEach
    fun setup() {
        driver = wdm.create()
    }
    */

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun `should test something using web driver`() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html")
        val title = driver.title

        Assertions.assertThat(
            title
        ).isEqualTo(
            "Web form"
        )
    }

    @Test
    fun eightComponents() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html")

        val title = driver.title
        Assertions.assertThat(title).isEqualTo("Web form")

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500))

        val textBox = driver.findElement(By.name("my-text"))
        val submitButton = driver.findElement(By.cssSelector("button"))

        textBox.sendKeys("Selenium")
        submitButton.click()

        val message = driver.findElement(By.id("message"))
        val value = message.text
        Assertions.assertThat(value).isEqualTo("Received!")

    }

}