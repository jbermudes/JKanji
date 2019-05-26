public class KanjiEntry
{
	private String kanjiFingerprint;
	private String unicode;
	private String entry;
	
	public KanjiEntry(String fingerprint, String code, String text)
	{
		kanjiFingerprint = fingerprint;
		unicode = code;
		entry = text;
	}
	
	public String getFingerprint()
	{
		return kanjiFingerprint;
	}
	
	public String getUnicode()
	{
		return unicode;
	}
	
	public String getEntry()
	{
		return entry;
	}
}