package minzhen.you.simFormSubmit;

/**
 * Hello world!
 *
 */
public class App {
	private static int startPId = 38917;
	private static int startSourseId = 38857;
	private static int[] number = { 7, 13, 1, 11, 6, 5, 3, 4, 5, 5 };

	public static void main(String[] args) {
		Submit submit = new Submit();
		try {
			for (int i = 0; i < number.length; i++) {
				for (int j = 0; j < number[i]; j++) {
					submit.htmlSubmit(startPId, startSourseId++);
				}
				startPId++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
