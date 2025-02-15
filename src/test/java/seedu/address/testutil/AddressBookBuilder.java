package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withContact("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook.copy();
    }

    /**
     * Adds a new {@code Contact} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withContact(Contact contact) {
        addressBook.addContact(contact);
        return this;
    }

    /**
     * Adds a new {@code Event} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withEvent(Event event) {
        addressBook.addEvent(event);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
