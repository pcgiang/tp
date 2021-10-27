package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.TEAM_MEETING;
import static seedu.address.testutil.TypicalEvents.getAddressBookWithBookmarkEvent;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class EBookmarkCommandTest {
    private Model expectedModel = new ModelManager(getAddressBookWithBookmarkEvent(), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EBookmarkCommand(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        String expectedMessage = String.format(EBookmarkCommand.MESSAGE_SUCCESS, TEAM_MEETING) + "\n";
        List<Index> indexes = List.of(Index.fromOneBased(5));
        EBookmarkCommand eBookmarkCommand = new EBookmarkCommand(indexes);
        assertCommandSuccess(eBookmarkCommand, model, new CommandResult(expectedMessage), expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        List<Index> outOfBoundIndex = List.of(Index.fromOneBased(model.getFilteredEventList().size() + 1));
        EBookmarkCommand ebookmarkCommand = new EBookmarkCommand(outOfBoundIndex);

        assertCommandFailure(ebookmarkCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_contactAlreadyMarked() {
        List<Index> indexes = List.of(Index.fromOneBased(1));
        model = expectedModel;
        EBookmarkCommand ebookmarkCommand = new EBookmarkCommand(indexes);
        String expectedMessage = String.format(EBookmarkCommand.MESSAGE_ALREADY_MARKED, TEAM_MEETING) + "\n";
        assertCommandSuccess(ebookmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Index first = Index.fromOneBased(1);
        Index second = Index.fromOneBased(2);

        List<Index> firstIndexes = new ArrayList<>();
        firstIndexes.add(first);
        List<Index> secondIndexes = new ArrayList<>();
        secondIndexes.add(second);
        EBookmarkCommand bookmarkFirstCommand = new EBookmarkCommand(firstIndexes);
        EBookmarkCommand bookmarkSecondCommand = new EBookmarkCommand(secondIndexes);

        // same object -> returns true
        assertTrue(bookmarkFirstCommand.equals(bookmarkFirstCommand));

        // same values -> returns true
        EBookmarkCommand bookmarkFirstCommandCopy = new EBookmarkCommand(firstIndexes);
        ;
        assertTrue(bookmarkFirstCommand.equals(bookmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(bookmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(bookmarkFirstCommand.equals(null));

        // different Index -> returns false
        assertFalse(bookmarkFirstCommand.equals(bookmarkSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
    }

}
