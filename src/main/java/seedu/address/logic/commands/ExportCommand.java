package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.util.ExportDataUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Attribute;

/**
 * Edits the details of an existing person in the address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export selected attributes "
            + "from the displayed person list.\n"
            + "Parameters: "
            + "[" + PREFIX_ATTRIBUTE + "ATTRIBUTE]...\n"
            + "ATTRIBUTE may be any of: name, phone, email, address, commission and should not have duplicates\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ATTRIBUTE + "phone";

    public static final String MESSAGE_EXPORT_SUCCESS = "Export success";
    public static final String MESSAGE_EXPORT_FAILURE = "Export failed";

    // Should we change this into a parameter of the export command?
    public static final Path DEFAULT_PATH = Path.of("export.csv");

    // This cannot be Attribute[] due to the given parser
    private final List<Attribute> attributes;

    /**
     * Constructs an {@code ExportCommand} to export the specified attributes from
     * the displayed person list.
     * If no attributes are provided, all available attributes will be exported by
     * default.
     *
     * @param attributes The list of attributes to export. If null or empty, all
     *                   attributes will be exported.
     */
    public ExportCommand(List<Attribute> attributes) {
        requireNonNull(attributes);

        // The case where the input is empty is handled at parsing
        assert (!attributes.isEmpty());

        this.attributes = attributes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getFilteredPersonList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }
        try {
            exportAsCsv(model, attributes);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_EXPORT_FAILURE, e);
        }
        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("attributes", attributes)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand otherExportCommand)) {
            return false;
        }

        return attributes.equals(otherExportCommand.attributes);
    }

    protected void exportAsCsv(Model model, List<Attribute> attributes) throws IOException {
        ExportDataUtil.exportAsCsv(model, attributes, DEFAULT_PATH);
    }
}
