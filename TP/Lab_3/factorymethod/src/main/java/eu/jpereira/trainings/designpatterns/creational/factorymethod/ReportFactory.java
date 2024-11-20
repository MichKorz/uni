package eu.jpereira.trainings.designpatterns.creational.factorymethod;

public class ReportFactory {

    Report getReport(String type)
    {
        Report report = null;
        if (type.equals("JSON")) {
            report = new JSONReport();
        } else if (type.equals("XML")) {
            report = new XMLReport();
        } else if (type.equals("HTML")) {
            report = new HTMLReport();
        } else if (type.equals("PDF")) {
            report = new PDFReport();
        }

        return report;
    }
}
