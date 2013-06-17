package ilearnrw.user;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User user = new User(1);
		System.out.println(user.getProfile().getProblemsList().getList().toString());
	}

}
