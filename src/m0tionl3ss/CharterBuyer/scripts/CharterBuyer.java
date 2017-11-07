package m0tionl3ss.CharterBuyer.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.World;

import m0tionl3ss.CharterBuyer.gui.Frame;
import m0tionl3ss.CharterBuyer.tasks.BankItems;
import m0tionl3ss.CharterBuyer.tasks.BuyFromCharterShop;
import m0tionl3ss.CharterBuyer.tasks.Quit;
import m0tionl3ss.CharterBuyer.tasks.RunToBank;
import m0tionl3ss.CharterBuyer.tasks.RunToCharterMember;
import m0tionl3ss.CharterBuyer.tasks.Task;
import m0tionl3ss.CharterBuyer.util.Options;
import m0tionl3ss.CharterBuyer.util.Tools;

import org.powerbot.script.Script;

@Script.Manifest(description = "Buy items from charter crewmembers in catherby", name = "CharterBuyer" , properties = "author=m0tionl3ss;topic=1339589;client=4;")
public class CharterBuyer extends PollingScript<ClientContext> implements PaintListener, MessageListener {
	List<Task> tasks = new ArrayList<>();
	Frame frame = new Frame(ctx);
	String status = "";
	@Override
	public void start()
	{
		
		BuyFromCharterShop buyFromCharterShopTask = new BuyFromCharterShop(ctx);
		buyFromCharterShopTask.setItemsToBuy(frame.getIds());
		Options.getInstance().setUseEscape(frame.useEscape());
		Options.getInstance().setUseScroll(frame.useMouseWheel());
		Options.getInstance().setShuffleWorlds(frame.shuffleWorlds());
		tasks.add(new RunToCharterMember(ctx));
		tasks.add(buyFromCharterShopTask);
		tasks.add(new RunToBank(ctx));
		tasks.add(new BankItems(ctx));
		tasks.add(new Quit(ctx));
		
		
		
		
		
	}
	@Override
	public void poll() {
		for (Task task : tasks) {
			if (task.activate()) {
				task.execute();
				this.status = task.status();
			}
		}
	}
	@Override
	public void repaint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(25, 25, 250, 90);
		g2.setColor(Color.GREEN);
		g2.drawRect(25, 25, 250, 90);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Bauhaus 93", Font.BOLD, 16));
		g2.drawString("M0tionl3ss CharterBuyer V1.2", 30, 50);
		g2.setFont(new Font("Tahoma",Font.PLAIN,12));
		g2.drawString("Status : " + this.status, 30, 70);
		g2.drawString("Time running :  " + Tools.getTimeRunning(getTotalRuntime()), 30, 90);
		
	}
	@Override
	public void messaged(MessageEvent message) {
		if (message.text().contains("out of stock"))
		{
			Options.getInstance().setOutOfStock(true);
		}
		
	}
	

}