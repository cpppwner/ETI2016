package ab6;

import java.util.List;

/**
 * 
 * @author Raphael Wigoutschnigg
 * 
 */
public interface SearchTools {

	/**
	 * Liefert alle Startindizes (beginnend bei 0), an denen das Muster
	 * (pattern) im Text gefunden wurde. Das Muster besteht nur aus Zeichen
	 * (keine Kleene-Stern etc). Die Suche ist mit Hilfe von endlichen Automaten
	 * zu bewerkstelligen. String-Methoden (sowie weitere passende Methoden) zum
	 * Suchen von Texten dürfen nicht verwendet werden.
	 * 
	 * @param text
	 *            der zu durchsuchende Text
	 * @param pattern
	 *            das zu suchende Muster
	 * @return aufsteigende Liste der Indizes des gefundenen Musters (jeweils
	 *         erste Stelle des Musters im Text)
	 */
	public List<Integer> findPattern(String text, String pattern);

	/**
	 * Entscheidet, ob der Text dem Muster entspricht. Es sind die Sonderzeichen
	 * "." und "*" erlaubt. Das Sonderzeichen "." steht für ein beliebiges
	 * Zeichen. Das Sonderzeichen "*" erlaubt die beliebige Wiederholung des
	 * letzten Zeichens (auch ".*" ist möglich). Die Überprüfung ist mit Hilfe
	 * von endlichen Automaten zu bewerkstelligen. String-Methoden (sowie weitere
	 * passende Methoden) zum Suchen von Texten dürfen nicht verwendet werden.
	 * 
	 * @param text
	 *            der zu durchsuchende Text
	 * @param pattern
	 *            das zu überprüfende Muster
	 * @return ob der text dem Muster enstpricht
	 */
	public Boolean checkPattern(String text, String pattern);
}
