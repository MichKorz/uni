package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.json.JSONReportBody;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBody;
import eu.jpereira.trainings.designpatterns.creational.builder.xml.XMLReportBody;

public class ReportBodyFactory {

    /**
     * Factory method to create ReportBody instances
     *
     * @param type The type of report (e.g., JSON, XML, HTML)
     * @return ReportBody instance
     */
    public static ReportBody createReportBody(String type)
    {
        switch (type)
        {
            case "JSON":
                return new JSONReportBody();
            case "XML":
                return new XMLReportBody();
            case "HTML":
                return new HTMLReportBody();
            default:
                throw new IllegalArgumentException("Unknown report type: " + type);
        }
    }
}

