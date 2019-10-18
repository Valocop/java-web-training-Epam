package by.training.module3.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Certificate {
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private long number;
    private Date issueDate;
    private Date shelfDate;
    private String registrOrganization;

    public Certificate() {
    }

    public Certificate(long number, Date issueDate, Date shelfDate, String registrOrganization) {
        this.number = number;
        this.issueDate = issueDate;
        this.shelfDate = shelfDate;
        this.registrOrganization = registrOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return number == that.number &&
                Objects.equals(issueDate, that.issueDate) &&
                Objects.equals(shelfDate, that.shelfDate) &&
                Objects.equals(registrOrganization, that.registrOrganization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, issueDate, shelfDate, registrOrganization);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "number=" + number +
                ", issueDate=" + new SimpleDateFormat(DATE_FORMAT).format(issueDate) +
                ", shelfDate=" + new SimpleDateFormat(DATE_FORMAT).format(shelfDate) +
                ", registrOrganization='" + registrOrganization + '\'' +
                '}';
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getShelfDate() {
        return shelfDate;
    }

    public void setShelfDate(Date shelfDate) {
        this.shelfDate = shelfDate;
    }

    public String getRegistrOrganization() {
        return registrOrganization;
    }

    public void setRegistrOrganization(String registrOrganization) {
        this.registrOrganization = registrOrganization;
    }
}
