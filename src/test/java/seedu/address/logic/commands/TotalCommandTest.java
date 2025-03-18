package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.JOSH;
import static seedu.address.testutil.TypicalPersons.KEN;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.CommissionLimitExceededException;

public class TotalCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_totalIsNotFiltered_showsSameValue() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void calculateTotalCommission_correctlySumsValues() {
        List<Person> lastShownList = List.of(
                ALICE,
                BENSON
        );
        TotalCommand totalCommand = new TotalCommand();
        Commission totalCommission = totalCommand.getTotal(lastShownList);

        Integer total = Integer.parseInt(ALICE.getCommission().value) + Integer.parseInt(BENSON.getCommission().value);
        assertEquals(new Commission(total.toString()), totalCommission);
    }

    @Test
    void getTotalThrowsRuntimeException() {
        List<Person> lastShownList = List.of(
                JOSH,
                KEN
        );
        TotalCommand totalCommand = new TotalCommand();
        assertThrows(CommissionLimitExceededException.class, () -> totalCommand.getTotal(lastShownList));
    }

    @Test
    void execute_nullModel_throwsNullPointerException() {
        Model model = null;
        TotalCommand totalCommand = new TotalCommand();
        assertThrows(NullPointerException.class, () -> totalCommand.execute(model));
    }

    @Test
    void execute_successMessage() {
        List<Person> lastShownList = List.of(
                ALICE,
                BENSON
        );
        TotalCommand totalCommand = new TotalCommand();
        Commission total = totalCommand.getTotal(getTypicalPersons());
        String expectedMessage = String.format(totalCommand.MESSAGE_TOTAL_SUCCESS + total);
        assertCommandSuccess(totalCommand, model, expectedMessage, expectedModel);
    }
}
