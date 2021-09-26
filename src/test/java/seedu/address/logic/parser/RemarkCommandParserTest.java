package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

class RemarkCommandParserTest {

    private static final String INVALID_REMARK_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemarkCommand.MESSAGE_USAGE);

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    void parse_validArgs_returnsRemarkCommand() {

        Remark emptyRemark = new Remark("");
        Remark validRemark = new Remark("She likes starbucks");

        // user input with valid index and no remark
        String userInputWithIndexOnly = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK;
        assertParseSuccess(parser, userInputWithIndexOnly, new RemarkCommand(INDEX_FIRST_PERSON, emptyRemark));

        // user input with valid index and remark
        String userInputWithIndexAndRemark = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_REMARK + "She likes starbucks";
        assertParseSuccess(parser, userInputWithIndexAndRemark,
                new RemarkCommand(INDEX_FIRST_PERSON, validRemark));
    }

    @Test
    void parse_missingArgs_throwsParseException() {
        // input without index
        String userInputWithoutIndex = PREFIX_REMARK + "She likes starbucks";
        assertParseFailure(parser, userInputWithoutIndex, INVALID_REMARK_MESSAGE);

        // input without index and field
        assertParseFailure(parser, "", INVALID_REMARK_MESSAGE);

        // input without prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + "Some remark", INVALID_REMARK_MESSAGE);
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        // zero index input
        String userInputWithZeroIndex = "0" + PREFIX_REMARK + "Some remark";
        assertParseFailure(parser, userInputWithZeroIndex, INVALID_REMARK_MESSAGE);

        // negative index input
        String userInputWithNegativeIndex = "-1" + PREFIX_REMARK + "Some remark";
        assertParseFailure(parser, userInputWithNegativeIndex, INVALID_REMARK_MESSAGE);

        // input with string index
        assertParseFailure(parser, "a", INVALID_REMARK_MESSAGE);
    }
}
