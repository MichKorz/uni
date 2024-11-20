package eu.jpereira.trainings.designpatterns.structural.facade;

import eu.jpereira.trainings.designpatterns.structural.facade.model.*;
import eu.jpereira.trainings.designpatterns.structural.facade.service.*;

public class DefaultBookstoreFacade implements BookstoreFacade {

    private BookDBService bookService;
    private CustomerDBService customerService;
    private OrderingService orderingService;
    private WharehouseService warehouseService;
    private CustomerNotificationService customerNotificationService;

    public void setBookService(BookDBService bookService) {
        this.bookService = bookService;
    }

    public void setCustomerService(CustomerDBService customerService) {
        this.customerService = customerService;
    }

    public void setOrderingService(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    public void setWarehouseService(WharehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    public void setCustomerNotificationService(CustomerNotificationService customerNotificationService) {
        this.customerNotificationService = customerNotificationService;
    }

    @Override
    public void placeOrder(String customerId, String isbn) {

        Book book = bookService.findBookByISBN(isbn);
        Customer customer = customerService.findCustomerById(customerId);
        Order order = orderingService.createOrder(customer, book);
        DispatchReceipt receipt = warehouseService.dispatch(order);
        customerNotificationService.notifyClient(receipt);

    }
}
