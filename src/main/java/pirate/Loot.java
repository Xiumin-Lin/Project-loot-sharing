package pirate;

public class Loot {
	private static int num = 1;
	private final int id;
	private String label;

	public Loot(String label){
		this.id = num++;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return "o" + id + ":" + label;
	}
}
