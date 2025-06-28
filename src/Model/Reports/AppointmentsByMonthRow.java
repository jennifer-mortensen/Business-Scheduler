package Model.Reports;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Contains data for a single row in a appointment types by month report.
 * @author J. Mortensen
 */
public class AppointmentsByMonthRow 
{
    /**
     * The month of the appointment row.
     */
    private SimpleStringProperty month;
    /**
     * The number of open account appointments in the given month.
     */
    private SimpleIntegerProperty openAccount;
    /**
     * The number of consultation appointments in the given month.
     */
    private SimpleIntegerProperty consultation;
    /**
     * The number of follow-up appointments in the given month.
     */
    private SimpleIntegerProperty followUp;
    /**
     * The number of other appointments in the given month.
     */
    private SimpleIntegerProperty other;
    
    /**
     * Default AppointsByMonthRow constructor.
     * @param month the month of the appointment row.
     * @param openAccount the number of open account appointments in the given month.
     * @param consultation the number of consultation appointments in the given month.
     * @param followUp the number of follow-up appointments in the given month.
     * @param other the number of other appointments in the given month.
     */
    public AppointmentsByMonthRow(String month, int openAccount, int consultation, int followUp, int other)
    {
        this.month = new SimpleStringProperty(month);
        this.openAccount = new SimpleIntegerProperty(openAccount);
        this.consultation = new SimpleIntegerProperty(consultation);
        this.followUp = new SimpleIntegerProperty(followUp);
        this.other = new SimpleIntegerProperty(other);
    }
    
    /**
     * Returns the month of the appointment row.
     * @return String
     */
    public String getMonth() { return month.get(); }
    /**
     * Returns the number of open account appointments for the given month.
     * @return int
     */
    public int getOpenAccount() { return openAccount.get(); }
    /**
     * Returns the number of consultation appointments for the given month.
     * @return int
     */
    public int getConsultation() { return consultation.get(); }
    /**
     * Returns the number of follow-up appointments for the given month.
     * @return int
     */
    public int getFollowUp() { return followUp.get(); }
    /**
     * Returns the number of other appointments for the given month.
     * @return int
     */
    public int getOther() { return other.get(); }
}
