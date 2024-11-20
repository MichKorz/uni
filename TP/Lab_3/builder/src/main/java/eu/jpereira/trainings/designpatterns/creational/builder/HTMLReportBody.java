package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBody;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SaleEntry;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SoldItem;

import java.util.Iterator;

public class HTMLReportBody implements ReportBody
{

	private final StringBuffer delegate = new StringBuffer();

	@Override
	public Object getAsString()
	{
		return this.delegate .toString();
	}

	@Override
	public void putContent(Object content)
	{
		this.delegate.append(content);

	}

	@Override
	public void addCustomerInfo(SaleEntry saleEntry)
	{
		putContent("<span class=\"customerName\">");
		putContent(saleEntry.getCustomer().getName());
		putContent("</span><span class=\"customerPhone\">");
		putContent(saleEntry.getCustomer().getPhone());
		putContent("</span>");
	}

	@Override
	public void addItems(SaleEntry saleEntry)
	{
		putContent("<items>");

		for (SoldItem soldEntry : saleEntry.getSoldItems())
		{
			putContent("<item><name>");
			putContent(soldEntry.getName());
			putContent("</name><quantity>");
			putContent(soldEntry.getQuantity());
			putContent("</quantity><price>");
			putContent(soldEntry.getUnitPrice());
			putContent("</price></item>");
		}
		putContent("</items>");
	}

}
