package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalAddressBook;


public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths
        .get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER
        .resolve("typicalContactsAndEventsAddressBook.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER
        .resolve("invalidContactAndEventAddressBook.json");
    private static final Path DUPLICATE_CONTACT_FILE = TEST_DATA_FOLDER
        .resolve("duplicateContactAddressBook.json");
    private static final Path DUPLICATE_EVENT_FILE = TEST_DATA_FOLDER
        .resolve("duplicateEventAddressBook.json");

    @Test
    public void toModelType_typicalFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
            TYPICAL_CONTACTS_FILE,
            JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalAddressBook = TypicalAddressBook.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalAddressBook);
    }

    @Test
    public void toModelType_invalidContactFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
            INVALID_CONTACT_FILE,
            JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateContacts_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
            DUPLICATE_CONTACT_FILE,
            JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_CONTACT,
            dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
            DUPLICATE_EVENT_FILE,
            JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_EVENT,
            dataFromFile::toModelType);
    }
}
