package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

public class ELinkCommand extends Command {
    public static final String COMMAND_WORD = "elink";
    public static final String PARAMETERS = "EVENT_INDEX "
            + PREFIX_CONTACT + "CONTACT_INDEX [" + PREFIX_CONTACT + "CONTACT_INDEX]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links an event to a contact. "
        + "Parameters: "
        + PARAMETERS
        + "Examples:\n"
        + "elink 1 c/1\n"
        + "elink 3 c/1 c/2 c/3 c/4 c/5";
    public static final String MESSAGE_SUCCESS = "Successfully linked the event %s to the contact %s.\n";
    public static final String MESSAGE_ALREADY_LINKED = "Event %s is already linked to the contact %s.\n";

    private final Index eventIndex;
    private final Set<Index> contactIndexes;

    /**
     * Creates an ELinkCommand to link the specified {@code Event} to a {@code Contact}
     */
    public ELinkCommand(Index eventIndex, Set<Index> contactIndexes) {
        assert !contactIndexes.isEmpty() : "Set of contact indices cannot be empty.";
        this.eventIndex = eventIndex;
        this.contactIndexes = contactIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // check validity of command
        List<Event> lastShownEventList = model.getFilteredEventList();
        List<Contact> lastShownContactList = model.getFilteredContactList();
        checkCommandValidity(lastShownEventList, lastShownContactList);

        // execution of command
        CommandResult commandResult = linkEventAndContacts(model, lastShownEventList, lastShownContactList);

        // rerender UI to show the links between event and each of the contacts
        model.rerenderAllCards();
        model.commitAddressBook();

        return commandResult;
    }

    private void checkCommandValidity(List<Event> eventList, List<Contact> contactList) throws CommandException {
        if (eventIndex.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        for (Index contactIndex : contactIndexes) {
            if (contactIndex.getZeroBased() >= contactList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
        }
    }

    private CommandResult linkEventAndContacts(Model model, List<Event> lastShownEventList,
            List<Contact> lastShownContactList) {
        String commandResult = "";
        int count = 0;
        for (Index contactIndex : contactIndexes) {
            // have to get the event from the list again because a new event replaces the index
            // whenever a link occurs, hence cannot use the old reference of event.
            Event eventToLink = lastShownEventList.get(eventIndex.getZeroBased());
            Contact contactToLink = lastShownContactList.get(contactIndex.getZeroBased());
            if (contactToLink.hasLinkTo(eventToLink)) {
                assert eventToLink.hasLinkTo(contactToLink); // bidirectional relationship
                commandResult += String.format(MESSAGE_ALREADY_LINKED, eventToLink.getName(), contactToLink.getName());
                count++;
                continue;
            }
            model.linkEventAndContact(eventToLink, contactToLink);
            commandResult += String.format(MESSAGE_SUCCESS, eventToLink.getName(), contactToLink.getName());
            count++;
        }
        return new CommandResult(commandResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ELinkCommand // instanceof handles nulls
            && eventIndex.equals(((ELinkCommand) other).eventIndex)
            && contactIndexes.equals(((ELinkCommand) other).contactIndexes)); // state check
    }
}
