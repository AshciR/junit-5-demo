package io.richie;

public class Contribution{

    private double amount;
    private String contributor;

    Contribution(double amount, String contributor){
        this.amount = amount;
        this.contributor = contributor;
    }

    double getAmount(){
        return amount;
    }

    String getContributor(){
        return contributor;
    }

    @Override
    public String toString(){
        return "Contribution{" + "amount=" + amount +
                ", contributor='" + contributor + '\'' +
                '}';
    }
}
