package head.first.ooad.ch04.start;

public class BarkRecognizer {

	private DogDoor door;

	public BarkRecognizer(DogDoor door) {
		this.door = door;
	}

	public void recognize(String bark) {
		System.out.println("   BarkRecognizer: Heard a '" + bark + "'");
		door.open();
	}
}
