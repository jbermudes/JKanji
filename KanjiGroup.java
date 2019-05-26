import java.util.*;

public class KanjiGroup
{
	private ArrayList kanjiProfiles;
	
	public KanjiGroup()
	{
		kanjiProfiles = new ArrayList();
	}
	
	public void addKanji(Kanji k)
	{
		kanjiProfiles.add(k.getProfile());
	}
	
	public KanjiProfile getKanji(int id)
	{
		return (KanjiProfile)(kanjiProfiles.get(id));
	}
	
	public int numProfiles()
	{
		return kanjiProfiles.size();
	}
}