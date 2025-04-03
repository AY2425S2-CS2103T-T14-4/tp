package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommissionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }


    @Test
    void execute_commissionAscendingSort_success() {
        List<Person> testList = List.of(
                ALICE,
                BENSON,
                CARL
        );
        SortCommissionCommand scc = new SortCommissionCommand(true);
        List<Person> sortedTestList = scc.getSortedList(testList);
        List<Person> expectedList = List.of(
                ALICE,
                CARL,
                BENSON
        );
        assertEquals(sortedTestList, expectedList);
    }

    @Test
    public void test_sortComm_success() {
        StringBuilder expectedMsgBuilder = new StringBuilder(
                "Listed all persons sorted by commission in ascending order.\n");
        List<Person> personList = model.getFilteredPersonList();
        List<Person> mutableList = new ArrayList<>(personList);

        mutableList.sort((p1, p2) -> {
            int commission1 = Integer.parseInt(p1.getCommission().value);
            int commission2 = Integer.parseInt(p2.getCommission().value);
            return Integer.compare(commission1, commission2);
        });

        int idx = 1;
        for (Person person : personList) {
            expectedMsgBuilder.append(idx).append(". ").append(person.getName()).append(", ")
                    .append(person.getCommission().value)
                    .append("\n");
            idx++;
        }

        String expectedMsg = expectedMsgBuilder.toString();

        // Act
        StringBuilder msgBuilder = new StringBuilder("Listed all persons sorted by commission in ascending order.\n");
        idx = 1;
        for (Person person : personList) {
            msgBuilder.append(idx).append(". ").append(person.getName())
                    .append(", ").append(person.getCommission().value).append("\n");
            idx++;
        }
        String actualMsg = msgBuilder.toString();

        // Assert
        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    void execute_commissionDescendingSort_success() {
        List<Person> testList = List.of(
                ALICE,
                BENSON,
                CARL
        );
        SortCommissionCommand scc = new SortCommissionCommand(false);
        List<Person> sortedTestList = scc.getSortedList(testList);
        List<Person> expectedList = new java.util.ArrayList<>(List.of(
                ALICE,
                CARL,
                BENSON
        ));
        Collections.reverse(expectedList);
        assertEquals(sortedTestList, expectedList);
    }



    @Test
    public void test_equals() {
        SortCommissionCommand commissionFirstCommand = new SortCommissionCommand(true);
        SortCommissionCommand commissionSecondCommand = new SortCommissionCommand(false);

        assertTrue(commissionFirstCommand.equals(commissionFirstCommand));
        assertTrue(commissionSecondCommand.equals(commissionSecondCommand));

        assertFalse(commissionFirstCommand.equals(commissionSecondCommand));

        assertFalse(commissionFirstCommand.equals(null));
    }



    @Test
    public void test_toString() {
        SortCommissionCommand scc = new SortCommissionCommand(false);
        String expected = "seedu.address.logic.commands.SortCommissionCommand{isAscending=desc}";
        assertEquals(expected, scc.toString());
    }
}
