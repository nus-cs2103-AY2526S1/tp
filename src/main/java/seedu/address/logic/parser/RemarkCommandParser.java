package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemarkCommandParser implements Parser<RemarkCommand> {

    @Override
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize input and look for the r/ prefix
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            // Extract index from the preamble (the part before any prefix)
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), ive);
        }

        // Extract remark value (or empty string if not provided)
        String remarkString = argMultimap.getValue(PREFIX_REMARK).orElse("");
        Remark remark = new Remark(remarkString);

        return new RemarkCommand(index, remark);
    }
}
