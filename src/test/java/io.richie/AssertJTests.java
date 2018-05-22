package io.richie;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AssertJTests{

    private Contribution richieContribution;
    private Contribution richieSecondContribution;
    private Contribution jamesContribution;

    @BeforeEach
    void setupTests(){
        richieContribution = new Contribution(1000.00, "Richie");
        jamesContribution = new Contribution(2000.00, "James");
        richieSecondContribution = new Contribution(3000.00, "Richie");
    }

    @Test
    void objectAssertions(){
        assertThat(richieContribution).isEqualToComparingFieldByFieldRecursively(richieSecondContribution)
                                      .isNotEqualTo(richieSecondContribution)
                                      .isNotEqualTo(jamesContribution);
    }

    @Test
    void stringAssertions(){
        String contributorName = richieContribution.getContributor();
        String amountAsString = String.valueOf(jamesContribution.getAmount())
                                      .replace(".","");

        assertThat(contributorName).isNotBlank()
                                   .containsIgnoringCase("chi")
                                   .doesNotContain("JAMES")
                                   .isEqualToIgnoringWhitespace("R i c h i e")
                                   .isEqualToIgnoringCase("RICHIE");

        assertThat(amountAsString).containsOnlyDigits();
    }

    @Test
    void doubleAssertions(){
        double richieMoney = richieContribution.getAmount();
        double richieSecondMoney = richieSecondContribution.getAmount();
        double jamesMoney = jamesContribution.getAmount();

        assertThat(jamesMoney).isGreaterThanOrEqualTo(richieMoney)
                              .isBetween(richieMoney, richieSecondMoney)
                              .isCloseTo(richieSecondMoney, Offset.<Double>offset(1000.00))
                              .isCloseTo(richieSecondMoney, Offset.<Double>strictOffset(1000.01));
    }

}
