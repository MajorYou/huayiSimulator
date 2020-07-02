package minzhen.you.simFormSubmit;

/**
 * Hello world!
 *
 */
public class App {
	private static int startPId = 44437;
	private static int startSourseId = 44429;
	private static int[] number = {8};

	public static void main(String[] args) {
		Submit submit = new Submit();
		try {
			for (int i = 0; i < number.length; i++) {
				for (int j = 0; j < number[i]; j++) {
					submit.htmlSubmit(startPId, startSourseId++,"q35f5t4ha35z40ux5htanu1p","login_log_id=2fc61908-4aba-4950-9e8a-19eec8c8d9ec");
				}
				startPId++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
