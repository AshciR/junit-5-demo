package io.richie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
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
        assertAll("Contributor Name",

                  //
                  () -> {
                      String contributorName = contribution.getContributor();
                      assertNotNull(contributorName);

                      // Executed only if the previous assertion is valid.
                      assertAll("Contributor's name",
                                () -> assertTrue(contributorName.startsWith("R")),
                                () -> assertTrue(contributorName.endsWith("e"))
                               );
                  },

                  // 2nd Assertion Block
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

    @Test
    void exceptionAssertions(){
        assertThrows(NumberFormatException.class, () -> Integer.parseInt("FOO"));
    }

    @Test
    void timeoutAssertions(){
        String assertionValue = assertTimeout(ofMinutes(1), () -> "Returned a String within a minute");
        assertTrue(assertionValue.length() > 10);
    }

    @Test
    void timeoutExceeded() {
        // The following assertion fails with an error message similar to:
        // execution exceeded timeout of 10 ms by 91 ms
        assertTimeout(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    // IMO, this is a better method than assertTimeout because it runs the code in another thread
    // If the executed code takes longer than you specify, then it's terminated.
    @Test
    void timeoutExceededWithPreemptiveTermination() {
        // The following assertion fails with an error message similar to:
        // execution timed out after 10 ms
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

}
