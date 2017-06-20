package scripts.TRuneCrafting.resources;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Tyskie on 18-6-2017.
 */
public abstract class Task extends ClientAccessor {

    public Task(ClientContext ctx) {
        super(ctx);
    }

    public abstract boolean activate();
    public abstract void execute();

}
