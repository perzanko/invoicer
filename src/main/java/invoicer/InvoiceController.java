package invoicer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Integer> addNewInvoice (@RequestBody InvoiceDBO invoiceForm) {
        Invoice invoice = new Invoice();
        invoice.setDate(invoiceForm.getDate());
        invoice.setComment(invoiceForm.getComment());
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return new ResponseEntity<>(savedInvoice.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}")
    public ResponseEntity<Invoice> updateInvoice (@RequestBody InvoiceDBO invoiceForm, @PathVariable Integer id) {
        Optional<Invoice> optInvoice = invoiceRepository.findById(id);
        return optInvoice
                .map(invoice -> {
                    if (invoiceForm.getDate() != null)
                        invoice.setDate(invoiceForm.getDate());
                    if (invoiceForm.getComment() != null)
                        invoice.setComment(invoiceForm.getComment());
                    Invoice savedInvoice = invoiceRepository.save(invoice);
                    return new ResponseEntity<>(savedInvoice, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Invoice> findAllInvoices() {
        return invoiceRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Integer id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return invoice
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}