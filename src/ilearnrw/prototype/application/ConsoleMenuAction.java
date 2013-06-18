package ilearnrw.prototype.application;
import ilearnrw.prototype.application.ConsoleMenu;
import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;

public class ConsoleMenuAction implements IConsoleMenuAction {
	String mText;
	public ConsoleMenuAction(String text) {
		mText = text;
	}

	@Override
	public EConsoleMenuActionResult onSelected(ConsoleMenu menu) { return EConsoleMenuActionResult.menuBreak; }

	@Override
	public String getText() {
		return mText;
	}
}