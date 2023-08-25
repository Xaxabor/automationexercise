package modules;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    static Date date = new Date();
    static Timestamp ts=new Timestamp(date.getTime());
    static SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
    static String timeStamp = String.valueOf(timestamp.format(ts)).replace(" ", "_").replace("-", "_");
    private static ExtentReports extent;
    private static final String reportFileName = "Test_Automaton_Report_"+timeStamp+".html";
    public static final String fileSeperator = System.getProperty("file.separator");
    public static final String reportFilepath = System.getProperty("user.dir") +fileSeperator+ "Reports"+fileSeperator+"Report_"+timeStamp;
    private static final String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;

    //Get an extent report instance. Will be sending this to ExtentTestManager Class
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    //Create an extent report instance
    public static ExtentReports createInstance() {
        String fileName = getReportPath();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }

    //Create the report path
    private static String getReportPath() {
        File testDirectory = new File(ExtentManager.reportFilepath);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + ExtentManager.reportFilepath + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + ExtentManager.reportFilepath);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + ExtentManager.reportFilepath);
        }
        return reportFileLocation;
    }
}