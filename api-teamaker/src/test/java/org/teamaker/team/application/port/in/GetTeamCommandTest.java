package org.teamaker.team.application.port.in;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolationException;
import static org.junit.jupiter.api.Assertions.*;

class GetTeamCommandTest {
    private static String validTeamId;

    @BeforeAll
    public static void setUp() {
        validTeamId = "validTeamId";
    }

    @Test
    public void testConstructorValidData() {
        GetTeamCommand command = new GetTeamCommand(validTeamId);
        assertEquals(validTeamId, command.getId());
    }

    @Test
    public void testConstructorEmptyTechnologyId() {
        assertThrows(ConstraintViolationException.class,
                () -> new GetTeamCommand(null));
    }
}