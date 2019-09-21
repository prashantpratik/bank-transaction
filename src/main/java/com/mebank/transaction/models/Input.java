package com.mebank.transaction.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Input {

    private String accountId;
    private Date from;
    private Date to;

    public Input(String accountId, Date from, Date to) {
        this.accountId = accountId;
        this.from = from;
        this.to = to;
    }

    public String getAccountId() {
        return accountId;
    }

    public Date getFrom() {
        return from;
    }


    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "Input{" +
                "accountId='" + accountId + '\'' +
                ", from =" + dateFormat.format(from) +
                ", to =" + dateFormat.format(to) +
                '}';
    }

    public Date getTo() {
        return to;
    }

}
