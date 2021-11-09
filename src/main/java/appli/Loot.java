package appli;

public class Loot {
	private static int id = 1;
	private int num;
	private String label;

	public Loot(String label){
		this.num = id++;
		this.label = label;
	}

	public int getNum() {
		return num;
	}

	public String getLabel() {
		return label;
	}
}
