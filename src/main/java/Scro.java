import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Scro
{

    static WebDriver driver;
    static Properties configProp;
    static FileInputStream fin;
    public static Logger log= Logger.getLogger(Scro.class.getName());

    public static void HighLightElement(WebElement  element) throws InterruptedException
    {
        JavascriptExecutor js=(JavascriptExecutor)driver;

        js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;');",element);
        Thread.sleep(1000);
        js.executeScript("arguments[0].setAttribute('style','background:cyan;border:2px solid red;');",element);


    }

    public static void scrolling(WebElement elm)
    {
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView();",elm);
        elm.click();
    }
    public static void main(String[] args) throws IOException, InterruptedException
    {
        PropertyConfigurator.configure(".\\src\\test\\resources\\Logs\\log4j.properties");
        fin=new FileInputStream(".\\src\\test\\resources\\TestData\\Config.properties");
        configProp=new Properties();
        configProp.load(fin);
        log.info("Config property file loaded succesfully");

        if(configProp.getProperty("Browser").equals("ie"))
        {
            WebDriverManager.edgedriver().setup();
            driver=new EdgeDriver();
            log.info("Edge browser opened");
           // driver.navigate().to("javascript:document.getElementById('overridelink').click()");
        }
        else if(configProp.getProperty("Browser").equals("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
        }

        driver.manage().window().maximize();
        log.info("Browser maximized");
        driver.manage().deleteAllCookies();
        log.info("Deleted all cookies");
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(configProp.getProperty("ImplicitWait")), TimeUnit.SECONDS);

        driver.get(configProp.getProperty("URL"));

        /*if(configProp.getProperty("Browser").equals("ie"))
        {
            WebElement btnLog=driver.findElement(By.xpath("//*[@class='_1_3w1N']"));
            HighLightElement(btnLog);
            btnLog.click();
        }*/
        WebElement txtUserName=driver.findElement(By.xpath("//div[@class='IiD88i _351hSN']//input[@type='text']"));
        HighLightElement(txtUserName);
        txtUserName.sendKeys("9211355420");

        WebElement txtPwd=driver.findElement(By.xpath("//div[@class='IiD88i _351hSN']//input[@type='password']"));
        HighLightElement(txtPwd);
        txtPwd.sendKeys("India@12345");

        WebElement btnLogin=driver.findElement(By.xpath("//button[@class='_2KpZ6l _2HKlqd _3AWRsL']"));
        HighLightElement(btnLogin);
        btnLogin.click();

        Thread.sleep(2000);
        WebElement locate=driver.findElement(By.xpath("//*[text()='Contact Us']"));

        scrolling(locate);



        Runtime.getRuntime().exec("TaskKill /F /IM chromedriver (32 bit).exe").destroy();

    }
}
