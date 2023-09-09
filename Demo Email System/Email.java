// Jason Zheng 114907558 R30 Section 8
import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * This class represents an email inside an email system.
 * It has variables that represent what would be inside an email,
 * and what it would have if we were to send such email.
 */
public class Email implements Serializable {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private GregorianCalendar timeStamp;

    /**
     * This is a constructor that constructs an email object with
     * specified properties of people to send to, subject, and body
     * of the email.
     *
     * @param to the parameter that represents who is email is for
     * @param cc the carbon copy recipient of this email
     * @param bcc the blind carbon copy recipient of this email
     * @param subject the subject of this email
     * @param body this is the body of the email
     */
    public Email
    (String to, String cc, String bcc, String subject, String body) {
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
        this.timeStamp = new GregorianCalendar();
    }

    /**
     * This method returns the recipient of the email
     *
     * @return the recipient of the email
     */
    public String getTo() {
        return to;
    }

    /**
     * This sets a new recipient for this email
     *
     * @param to the new recipient of the email
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * This method returns the carbon copy recipient of this email
     *
     * @return the carbon copy recipient of this email
     */
    public String getCc() {
        return cc;
    }

    /**
     * This sets a new carbon copy recipient of this email
     *
     * @param cc the new carbon copy recipient of this email
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * This method returns the blind carbon copy recipient of this email
     *
     * @return the blind carbon copy recipient of this email
     */
    public String getBcc() {
        return bcc;
    }

    /**
     * This method sets a new blind carbon copy recipient of this email
     * 
     * @param bbc the new blind carbon copy recipient of this email
     */

    public void setBcc(String bbc) {
        this.bcc = bbc;
    }

    /**
     * This method returns the subject of the email
     *
     * @return the subject of the email
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method sets a new subject to the email
     *
     * @param subject the new subject of the email
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * This method returns the body of the email
     *
     * @return the body of the email
     */
    public String getBody() {
        return body;
    }

    /**
     * This method sets a new body to the email
     *
     * @param body the new body of the email
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * This method returns the time of the email
     *
     * @return the timestamp of this email
     */
    public GregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * This method sets a new timestamp to this email
     *
     * @param timeStamp the new timestamp
     */
    public void setTimeStamp(GregorianCalendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * This method returns a string formatted form of the
     * GregorianCalendar object in order to properly print it
     *
     * @return the string formatted object
     */
    public String gregorianConverter() {
        int hour = timeStamp.get(GregorianCalendar.HOUR);
        int minute = timeStamp.get(GregorianCalendar.MINUTE);
        int amPm = timeStamp.get(GregorianCalendar.AM_PM);
        int month = timeStamp.get(GregorianCalendar.MONTH) + 1;
        int day = timeStamp.get(GregorianCalendar.DAY_OF_MONTH);
        int year = timeStamp.get(GregorianCalendar.YEAR);
        String AMorPM;
        if (amPm == 0) {
            AMorPM = "AM";
        } else {
            AMorPM = "PM";
        }
        String timeFormat = String.format
                ("%02d:%02d%s", hour, minute, AMorPM);
        String dateFormat = String.format
                ("%d/%d/%d", month, day, year);
        return (timeFormat + " " + dateFormat);
    }
}
