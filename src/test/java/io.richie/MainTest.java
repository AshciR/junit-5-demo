package io.richie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest{

    private Contribution contribution;

    @BeforeEach
    void setupTests(){
        contribution = new Contribution(100.00, "Richie");
    }

    @Test
    void standardAssertions(){
        assertEquals(10, 5 + 5);
        assertEquals(5, 2 + 3, "Optional Message if assertion fails");
        assertTrue(true,() -> "Uses a lambda for message; allows for lazy evaluation");
    }

    @Test
    void groupedAssertions(){
        // In a grouped assertion all assertions are executed, and any
        // failures will be reported together.
        assertAll("Grouped Assertions",
                  () -> assertEquals(100.00, contribution.getAmount()),
                  () -> assertEquals("Richie", contribution.getContributor())
                 );
    }

    @Test
    void dependentAssertions(){
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("Dependent Assertions",
                  () -> {
                      String firstName = contribution.getContributor();
                      assertNotNull(firstName);

                      // Executed only if the previous assertion is valid.
                      assertAll("first name",
                                () -> assertTrue(firstName.startsWith("R")),
                                () -> assertTrue(firstName.endsWith("e"))
                               );
                  },
                  () -> {
                      // Grouped assertion, so processed independently
                      // of results of first name assertions.
                      double money = contribution.getAmount();

                      // Executed only if the previous assertion is valid.
                      assertAll("money",
                                () -> assertEquals(100.00, money),
                                () -> assertTrue(money > 50)
                               );
                  }
                 );
    }

}
