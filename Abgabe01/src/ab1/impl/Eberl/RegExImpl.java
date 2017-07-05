package ab1.impl.Eberl;

import ab1.RegEx;

public class RegExImpl implements RegEx {

	@Override
	public String getRegexGeradeLaenge() {

		return "(..)*";
	}

	@Override
	public String getRegexGanzeZahlen() {

		return "((\\+|-)?[1-9]\\d*)|0";
	}

	@Override
	public String getRegexTelnummer() {

		return "(00|\\+)[1-9]\\d{0,2}"  // country code
		     + " ?"                     // optional space
		     + "[1-9]\\d{2}"            // area code
		     + " ?"                     // optional space
		     + "\\d{6,8}";              // phone number
	}

	@Override
	public String getRegexDatum() {

		return "\\d{1,2}"               // day
		     + "."                      // day-month separator
		     + "\\d{1,2}"               // month
		     + "."                      // month-year separator
		     + "[1-9]\\d{3}";           // year
	}

	@Override
	public String getRegexDomainName() {

		return "([a-z]+\\.)+"           // any domain part
		     + "[a-z]{2,3}";            // top level domain
	}

	@Override
	public String getRegexEmail() {

        return "[a-zA-Z](\\w|\\.|-)*"   // recipient part
             + "@"
             + getRegexDomainName();
	}

    @Override
    public String getRegexURL() {

        return "(ftp|https?)"           // protocol
                + "://"
                + getRegexDomainName()
                + "(:\\d{1,5})?"        // port part
                + "(/(\\w|\\.|-)*)*";   // path part
	}

	@Override
	public String getRegexVereinfachen1() {

        return "[a-d]{10}";
	}

	@Override
	public String getRegexVereinfachen2() {

        return "[ab]{2,4}";
    }
}
