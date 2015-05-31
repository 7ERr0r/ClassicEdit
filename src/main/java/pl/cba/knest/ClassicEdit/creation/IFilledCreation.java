package pl.cba.knest.ClassicEdit.creation;

import pl.cba.knest.ClassicEdit.Filling;

public interface IFilledCreation extends ICreation {
	public Filling getFilling();
	public void setFilling(Filling f);
}
